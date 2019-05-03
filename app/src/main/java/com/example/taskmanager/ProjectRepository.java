package com.example.taskmanager;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class ProjectRepository {
    private ProjectDao mProjectDao;
    private LiveData<List<Project>> mAllProjects;

    ProjectRepository(Application application){
        TaskManagerDatabase db = TaskManagerDatabase.getDatabase(application);
        mProjectDao = db.project();
        mAllProjects = mProjectDao.getAllProjects();
    }

    LiveData<List<Project>> getAllProjects(){
        return mAllProjects;
    }

//    int getProjectID(String projectName) {
//        return mProjectDao.getProjectID(projectName);
//    }

    public void insert(Project project) {
        new insertAsyncTask(mProjectDao).execute(project);
    }


    public void deleteAll()  {
        new deleteAllProjectsAsyncTask(mProjectDao).execute();
    }

    public void deleteAllTasks(Project project){
        new deleteAllProjectTasksAsyncTask(mProjectDao).execute(project);
    }

    public void deleteProject(Project project)  {
        new deleteProjectAsyncTask(mProjectDao).execute(project);
    }

    private static class insertAsyncTask extends AsyncTask<Project, Void, Void> {

        private ProjectDao mAsyncProjectTaskDao;

        insertAsyncTask(ProjectDao dao) {
            mAsyncProjectTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Project... params) {
            mAsyncProjectTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAllProjectTasksAsyncTask extends AsyncTask<Project, Void, Void> {

        private ProjectDao mAsyncTaskDao;

        deleteAllProjectTasksAsyncTask(ProjectDao dao) {mAsyncTaskDao = dao; }

        @Override
        protected Void doInBackground(final Project... params) {
            int projectID = params[0].getID();
            mAsyncTaskDao.deleteAllTasks(projectID);
            return null;
        }
    }

    private static class deleteAllProjectsAsyncTask extends AsyncTask<Void, Void, Void> {
        private ProjectDao mAsyncTaskDao;

        deleteAllProjectsAsyncTask(ProjectDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteTasksTable();
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    private static class deleteProjectAsyncTask extends AsyncTask<Project, Void, Void> {
        private ProjectDao mAsyncTaskDao;

        deleteProjectAsyncTask(ProjectDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Project... params) {
            mAsyncTaskDao.deleteProject(params[0]);
            return null;
        }
    }
}
