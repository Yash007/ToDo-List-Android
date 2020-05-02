package com.yash.todolist.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yash.todolist.R;
import com.yash.todolist.TaskListActivity;
import com.yash.todolist.model.TaskList;

import java.util.ArrayList;
import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskListHolder> {

    private List<TaskList> taskLists = new ArrayList<>();
    private Activity activity;
    private int taskId;

    public TaskListAdapter(TaskListActivity taskListActivity, int taskId) {
        activity = taskListActivity;
        this.taskId = taskId;
    }

    @NonNull
    @Override
    public TaskListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_task_lists_item, parent, false);
        return new TaskListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TaskListHolder holder, int position) {
        TaskList taskList = taskLists.get(position);
        holder.taskListId.setText(String.valueOf(taskList.getId()));
        holder.taskListTitle.setText(taskList.getTaskName());
        holder.taskCounter.setText(String.valueOf(position + 1));
    }

    @Override
    public int getItemCount() {
        return taskLists.size();
    }
    public void setTaskLists(List<TaskList> taskLists)  {
        this.taskLists = taskLists;
        notifyDataSetChanged();
    }


    public TaskList getTaskListAt(int position)    {
        return taskLists.get(position);
    }

    public final class TaskListHolder extends RecyclerView.ViewHolder    {
        private TextView taskListId;
        private TextView taskListTitle;
        private TextView taskCounter;
        public RelativeLayout viewBackground, viewForeground;

        public TaskListHolder(View itemView) {
            super(itemView);
            taskListId = itemView.findViewById(R.id.taskListIdRecyclerView);
            taskListTitle = itemView.findViewById(R.id.taskListTitleRecyclerview);
            taskCounter = itemView.findViewById(R.id.taskListNumberRecyclerView);
            viewBackground = itemView.findViewById(R.id.viewListBackground);
            viewForeground = itemView.findViewById(R.id.viewListForeground);

        }
    }
}
