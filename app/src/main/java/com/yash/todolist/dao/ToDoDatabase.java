package com.yash.todolist.dao;

import android.app.Dialog;
import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.yash.todolist.model.Task;
import com.yash.todolist.model.TaskList;

/**
 * @author Yash Sompura
 */

@Database(entities = {Task.class, TaskList.class}, version = 3)
public abstract class ToDoDatabase extends RoomDatabase {
    private static ToDoDatabase toDoDatabase;
    private static final String DB_NAME = "todolist.db";

    public static ToDoDatabase getToDoDatabase(final Context context)   {
        if(toDoDatabase == null)    {
            synchronized (ToDoDatabase.class)   {
                if(toDoDatabase == null)    {
                    toDoDatabase = Room.databaseBuilder(context.getApplicationContext(), ToDoDatabase.class, DB_NAME)
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return toDoDatabase;
    }

    public abstract TaskDao getTaskDao();

    public abstract TaskListDao getTaskListDao();

}
