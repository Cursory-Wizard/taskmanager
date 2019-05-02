package com.example.taskmanager;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TasksDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Tasks task);

    //@Query("DELETE FROM task_table")
    //void deleteAll();
    // replace with:
    @Query("DELETE FROM task_table WHERE reference_project = (:project_id)")
    void deleteAll(int project_id);

    @Query("SELECT * from task_table WHERE reference_project = (:project_id) ORDER BY task_id ASC")
    LiveData<List<Tasks>> getAllTasks(int project_id);

    @Query("SELECT * from task_table LIMIT 1")
    Tasks[] getAnyTasks();

    @Delete
    void deleteTasks(Tasks task);
}
