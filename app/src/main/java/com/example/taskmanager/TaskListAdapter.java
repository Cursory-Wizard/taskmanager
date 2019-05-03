package com.example.taskmanager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>{
    private final LayoutInflater mInflater;
    private List<Tasks> mTasks;

    TaskListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        if (mTasks != null) {
            Tasks current = mTasks.get(position);
            holder.TaskItemView.setText(current.getTask());
        } else {
            holder.TaskItemView.setText("No Tasks");
        }
    }

    public Tasks getTaskAtPosition (int position) {
        return mTasks.get(position);
    }

    void setTasks(List<Tasks> tasks){
        mTasks = tasks;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mTasks != null)
            return mTasks.size();
        else return 0;
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        private final TextView TaskItemView;

        private TaskViewHolder(View itemView) {
            super(itemView);
            TaskItemView = itemView.findViewById(R.id.textView);
        }
    }
}

