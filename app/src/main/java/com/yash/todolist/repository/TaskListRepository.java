package com.yash.todolist.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.yash.todolist.dao.TaskListDao;
import com.yash.todolist.dao.ToDoDatabase;
import com.yash.todolist.model.TaskList;

import java.util.List;

public class TaskListRepository {
    private TaskListDao taskListDao;
    private LiveData<List<TaskList>> allTaskList;

    public TaskListRepository(Application application, int taskId)  {
        ToDoDatabase toDoDatabase = ToDoDatabase.getToDoDatabase(application);
        taskListDao = toDoDatabase.getTaskListDao();
        allTaskList = taskListDao.findAllTasks(taskId);
    }

    public LiveData<List<TaskList>> findAllTasks()    {
        return  allTaskList;
    }

    public void insert(TaskList taskList)   {
        new insertTaskListItem(taskListDao).execute(taskList);
    }

    public void update(TaskList taskList)   {
        new updateTaskListItem(taskListDao).execute(taskList);
    }

    public void delete(TaskList taskList)   {
        new deleteTaskListItem(taskListDao).execute(taskList);
    }

    public void deleteAll(int taskId) {
        new deleteAllTaskListItem(taskListDao).execute(taskId);
    }

    private static class insertTaskListItem extends AsyncTask<TaskList, Void, Void> {
        private TaskListDao taskListDao;
        public insertTaskListItem(TaskListDao taskListDao)  {
            this.taskListDao = taskListDao;
        }
        @Override
        protected Void doInBackground(TaskList... taskLists) {
            taskListDao.insert(taskLists[0]);
            return null;
        }
    }

    private static class updateTaskListItem extends AsyncTask<TaskList, Void, Void> {
        private TaskListDao taskListDao;

        public updateTaskListItem(TaskListDao taskListDao)  {
            this.taskListDao = taskListDao;
        }

        @Override
        protected Void doInBackground(TaskList... taskLists) {
            taskListDao.update(taskLists[0]);
            return null;
        }
    }

    private static class deleteTaskListItem extends AsyncTask<TaskList, Void, Void> {
        private TaskListDao taskListDao;
        public deleteTaskListItem(TaskListDao taskListDao)  {
            this.taskListDao = taskListDao;
        }

        @Override
        protected Void doInBackground(TaskList... taskLists) {
            taskListDao.delete(taskLists[0]);
            return null;
        }
    }

    private static class deleteAllTaskListItem extends AsyncTask<Integer, Void, Void>  {
        private TaskListDao taskListDao;

        public deleteAllTaskListItem(TaskListDao taskListDao) {
            this.taskListDao = taskListDao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            taskListDao.deleteByTaskId(integers[0]);
            return null;
        }
    }
}
