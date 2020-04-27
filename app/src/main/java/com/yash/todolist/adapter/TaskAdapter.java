package com.yash.todolist.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yash.todolist.R;
import com.yash.todolist.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {

    private List<Task> tasks = new ArrayList<>();

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_tasks, parent, false);
        return new TaskHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
        Task task = tasks.get(position);
        holder.taskId.setText(String.valueOf(task.getId()));
        holder.taskTitle.setText(task.getTitle());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setTasks(List<Task> tasks)  {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    public Task getTaskAt(int position)    {
        return tasks.get(position);
    }

    public final class TaskHolder extends RecyclerView.ViewHolder    {
        private TextView taskId;
        private TextView taskTitle;
        public RelativeLayout viewBackground, viewForeground;

        public TaskHolder(View itemView) {
            super(itemView);
            taskId = itemView.findViewById(R.id.taskIdRecyclerView);
            taskTitle = itemView.findViewById(R.id.taskTitleRecyclerview);
            viewBackground = itemView.findViewById(R.id.viewBackground);
            viewForeground = itemView.findViewById(R.id.viewForeground);

        }
    }
}
