package com.example.taskmanager;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ProjectDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Project project);

    @Query("DELETE FROM task_table")
    void deleteTasksTable();

    @Query("DELETE FROM project_table")
    void deleteAll();

    @Query("DELETE FROM task_table WHERE reference_project = (:project_id)")
    void deleteAllTasks(int project_id);

    @Query("SELECT * from project_table ORDER BY project_id ASC")
    LiveData<List<Project>> getAllProjects();

    @Query("SELECT project_id from project_table WHERE project_name = (:projectName)")
    int getProjectID(String projectName);

    @Query("SELECT * from project_table LIMIT 1")
    Project[] getAnyProject();

    @Delete
    void deleteProject(Project project);
}
