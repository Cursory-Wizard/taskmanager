package com.example.taskmanager;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Project.class, Tasks.class}, version = 1, exportSchema = false)
public abstract class TaskManagerDatabase extends RoomDatabase {

    public abstract ProjectDao project();
    public abstract TasksDao tasks();
    private static TaskManagerDatabase INSTANCE;

    public static TaskManagerDatabase getDatabase(final Context context){
        if (INSTANCE == null) {
            synchronized (TaskManagerDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TaskManagerDatabase.class, "task_manager_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sTaskManagerDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static TaskManagerDatabase.Callback sTaskManagerDatabaseCallback =
            new TaskManagerDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final ProjectDao mProjectDao;
        private final TasksDao mTasksDao;

//        String[] project = {"Your first Project"};
//        String[] tasks = {"Your first Task"};

        PopulateDbAsync(TaskManagerDatabase db) {
            mProjectDao = db.project();
            mTasksDao = db.tasks();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            return null;
        }
    }
}
