package com.example.taskmanager;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class TaskViewModel extends AndroidViewModel{

    private TaskRepository mTaskRepository;
    private LiveData<List<Tasks>> mAllTasks;

    public TaskViewModel(Application application, int project) {
        super(application);
        mTaskRepository =  new TaskRepository(application, project);
        mAllTasks = mTaskRepository.getAllTasks();
    }

    LiveData<List<Tasks>> getAllTasks() {return mAllTasks;}

    public void insert(Tasks task) {mTaskRepository.insert(task);}

    public void deleteTask(Tasks task) {mTaskRepository.deleteTask(task);}

    public void deleteAll(Tasks task) {mTaskRepository.deleteAll();}
}
