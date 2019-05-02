package com.example.taskmanager;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class ProjectViewModel extends AndroidViewModel {

    private ProjectRepository mProjectRepository;
    private LiveData<List<Project>> mAllProjects;

    public ProjectViewModel(Application application){
        super(application);
        mProjectRepository = new ProjectRepository(application);
        mAllProjects = mProjectRepository.getAllProjects();
    }

    LiveData<List<Project>> getAllProjects(){return mAllProjects;}

    public void insert(Project project) {mProjectRepository.insert(project);}

    public void deleteAll() {mProjectRepository.deleteAll();}

    public void deleteAllTasks(Project project) {mProjectRepository.deleteAllTasks(project);}

    public void deleteProject(Project project) {mProjectRepository.deleteProject(project);}
}
