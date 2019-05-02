package com.example.taskmanager;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class TaskRepository {
    private TasksDao mTasksDao;
    private LiveData<List<Tasks>> mAllTasks;

    TaskRepository(Application application, int project){
        TaskManagerDatabase db = TaskManagerDatabase.getDatabase(application);
        mTasksDao = db.tasks();
        mAllTasks = mTasksDao.getAllTasks(project);
    }

    LiveData<List<Tasks>> getAllTasks(){
        return mAllTasks;
    }

    public void insert(Tasks task) {
        new insertAsyncTask(mTasksDao).execute(task);
    }

//    public void deleteAll()  {
//        new deleteAllTasksAsyncTask(mTasksDao).execute();
//    }

    public void deleteTask(Tasks task)  {
        new deleteTasksAsyncTask(mTasksDao).execute(task);
    }
    private static class insertAsyncTask extends AsyncTask<Tasks, Void, Void> {

        private TasksDao mAsyncTasksDao;

        insertAsyncTask(TasksDao dao) {
            mAsyncTasksDao = dao;
        }

        @Override
        protected Void doInBackground(final Tasks... params) {
            mAsyncTasksDao.insert(params[0]);
            return null;
        }
    }

   /* private static class deleteAllTasksAsyncTask extends AsyncTask<Void, Void, Void> {
        private TasksDao mAsyncTaskDao;

        deleteAllTasksAsyncTask(TasksDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }*/

    private static class deleteTasksAsyncTask extends AsyncTask<Tasks, Void, Void> {
        private TasksDao mAsyncTaskDao;

        deleteTasksAsyncTask(TasksDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Tasks... params) {
            mAsyncTaskDao.deleteTasks(params[0]);
            return null;
        }
    }
}

