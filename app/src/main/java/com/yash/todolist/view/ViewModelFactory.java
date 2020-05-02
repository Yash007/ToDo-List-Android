package com.yash.todolist.view;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private Application application;
    private int taskId;

    public ViewModelFactory(@NonNull Application application, int taskId) {
        this.application = application;
        this.taskId = taskId;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new TaskListViewModel(application, taskId);
    }
}
