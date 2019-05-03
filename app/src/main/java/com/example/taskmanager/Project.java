package com.example.taskmanager;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "project_table")
public class Project {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "project_id")
    private int mID;


    @ColumnInfo(name = "project_name")
    private String mProject;

    public Project(int mID, String mProject){
        this.mID = mID;
        this.mProject = mProject;}

    public int getID() {return this.mID;}
    public String getProject(){return this.mProject;}

}
