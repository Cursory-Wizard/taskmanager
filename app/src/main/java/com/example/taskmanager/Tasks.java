package com.example.taskmanager;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "task_table",
        foreignKeys = @ForeignKey(entity = Project.class,
                                    parentColumns = "project_id",
                                    childColumns = "reference_project"))
public class Tasks {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "task_id")
    private int mID;


    @ColumnInfo(name = "reference_project")
    private int mReference;


    @ColumnInfo(name = "task_name")
    private String mTask;

    public Tasks(int mID, int mReference, @NonNull String task) {
        this.mID = mID;
        this.mReference =  mReference;
        this.mTask = task;
    }

    public String getTask() {return this.mTask;}
    public int getID() {return this.mID;}
    public int getReference() {return this.mReference;}

}
