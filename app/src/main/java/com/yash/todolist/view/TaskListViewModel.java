package com.yash.todolist.view;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.yash.todolist.model.TaskList;
import com.yash.todolist.repository.TaskListRepository;

import java.util.List;

public class TaskListViewModel extends AndroidViewModel {
    private TaskListRepository taskListRepository;
    private LiveData<List<TaskList>> taskLists;

    public TaskListViewModel(@NonNull Application application, int taskId) {
        super(application);
        taskListRepository = new TaskListRepository(application, taskId);
        taskLists = taskListRepository.findAllTasks();
    }

    public void insert(TaskList taskList)   {
        taskListRepository.insert(taskList);
    }

    public void udpate(TaskList taskList)   {
        taskListRepository.update(taskList);
    }

    public void delete(TaskList taskList)   {
        taskListRepository.delete(taskList);
    }

    public void deleteAll(int taskId)   {
        taskListRepository.deleteAll(taskId);
    }

    public LiveData<List<TaskList>> getTaskLists(){
        return taskLists;
    }
}
