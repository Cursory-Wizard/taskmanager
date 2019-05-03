package com.example.taskmanager;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class TaskList extends AppCompatActivity {

    private TaskViewModel mTaskViewModel;
    private int projectID;
    public static final int NEW_TASK_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle b = getIntent().getExtras();
        projectID = b.getInt("id");
        Log.e("click",Integer.toString(projectID));


        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final TaskListAdapter adapter = new TaskListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //mTaskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        mTaskViewModel = new TaskViewModel(this.getApplication(), projectID);

        mTaskViewModel.getAllTasks().observe(this, new Observer<List<Tasks>>() {
            @Override
            public void onChanged(@Nullable final List<Tasks> tasking) {

                adapter.setTasks(tasking);
            }
        });

        FloatingActionButton tasking = findViewById(R.id.addTask);
        tasking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskList.this, NewTaskActivity.class);
                startActivityForResult(intent, NEW_TASK_ACTIVITY_REQUEST_CODE);
            }
        });

        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                         int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Tasks myTask = adapter.getTaskAtPosition(position);
                        Toast.makeText(TaskList.this, "Deleting " +
                                myTask.getTask(), Toast.LENGTH_LONG).show();


                        mTaskViewModel.deleteTask(myTask);


                    }
                });
        helper.attachToRecyclerView(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.clear_data) {
            // Add a toast just for confirmation
            Toast.makeText(this, "Clearing the data...",
                    Toast.LENGTH_SHORT).show();

            // Delete the existing data
            // CHECK FUNCTIONALITY LATER. Perhaps better just to return to Main and call delete?
            //mTaskViewModel.deleteAll();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_TASK_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Tasks task = new Tasks(0, projectID, data.getStringExtra(NewProjectActivity.EXTRA_REPLY));
            mTaskViewModel.insert(task);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.task_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
}
