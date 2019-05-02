package com.example.taskmanager;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.ProjectViewHolder> {

    private final LayoutInflater mInflater;
    private List<Project> mProjects;
    private String projectID;

    ProjectListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ProjectViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProjectViewHolder holder, int position) {
        if (mProjects != null) {
            final Project current = mProjects.get(position);
            holder.projectItemView.setText(current.getProject());
            holder.projectItemView.setOnTouchListener(new View.OnTouchListener() {
                private static final int MAX_CLICK_DURATION = 200;
                private long startClickTime;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: {
                            startClickTime = Calendar.getInstance().getTimeInMillis();
                            break;
                        }
                        case MotionEvent.ACTION_UP: {
                            long clickDuration = Calendar.getInstance().getTimeInMillis() - startClickTime;
                            if(clickDuration < MAX_CLICK_DURATION) {
                               projectID = Integer.toString(current.getID());
                               Log.e("click", projectID);
                            }
                        }
                    }
                    return true;
                }
            });
        } else {
            holder.projectItemView.setText("No Project");
        }
    }

    public Project getProjectAtPosition (int position) {
        return mProjects.get(position);
    }

    void setProjects(List<Project> projects){
        mProjects = projects;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mProjects != null)
            return mProjects.size();
        else return 0;
    }

    class ProjectViewHolder extends RecyclerView.ViewHolder {
        private final TextView projectItemView;

        private ProjectViewHolder(View itemView) {
            super(itemView);
            projectItemView = itemView.findViewById(R.id.textView);

        }
    }
}