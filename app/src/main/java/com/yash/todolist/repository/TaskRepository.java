package com.yash.todolist.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.yash.todolist.dao.TaskDao;
import com.yash.todolist.dao.ToDoDatabase;
import com.yash.todolist.model.Task;

import java.util.List;

public class TaskRepository {
    private TaskDao taskDao;
    private LiveData<List<Task>> allTasks;

    public TaskRepository(Application application) {
        ToDoDatabase toDoDatabase = ToDoDatabase.getToDoDatabase(application);
        taskDao = toDoDatabase.getTaskDao();
        allTasks = taskDao.findAllTask();
    }

    public void insert(Task task)   {
        new InsertTaskAsync(taskDao).execute(task);
    }

    public void update(Task task)   {
        new UpdateTaskAsync(taskDao).execute(task);
    }

    public void delete(Task task)   {
        new DeleteTaskAsync(taskDao).execute(task);
    }

    public void deleteAll() {
        new DeleteAllTaskAsync(taskDao).execute();
    }

    public LiveData<List<Task>> getAllTasks()   {
        return allTasks;
    }

    private static class InsertTaskAsync extends AsyncTask<Task, Void, Void>    {
        private TaskDao taskDao;

        private InsertTaskAsync(TaskDao taskDao)    {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.insert(tasks[0]);
            return null;
        }
    }

    private static class UpdateTaskAsync extends AsyncTask<Task, Void, Void>    {
        private TaskDao taskDao;

        private UpdateTaskAsync(TaskDao taskDao)    {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.update(tasks[0]);
            return null;
        }
    }

    private static class DeleteTaskAsync extends AsyncTask<Task, Void, Void>    {
        private TaskDao taskDao;

        private DeleteTaskAsync(TaskDao taskDao)    {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.deleteById(tasks[0]);
            return null;
        }
    }

    private static class DeleteAllTaskAsync extends AsyncTask<Void, Void, Void>    {
        private TaskDao taskDao;

        private DeleteAllTaskAsync(TaskDao taskDao)    {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            taskDao.deleteAll();
            return null;
        }
    }

}
