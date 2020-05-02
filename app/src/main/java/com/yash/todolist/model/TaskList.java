package com.yash.todolist.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.sql.Date;


/**
 * @author Yash Sompura
 */
@Entity(tableName = "tasklist",
        foreignKeys = @ForeignKey(entity = Task.class,
        parentColumns = "id",
        childColumns = "task_id",
        onDelete = ForeignKey.CASCADE),
        indices = {@Index("task_id")})
public class TaskList {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tid")
    public int id;

    @ColumnInfo(name = "task_id")
    public int taskId;

    @ColumnInfo(name = "task_name")
    @NonNull
    public String taskName;

    public TaskList(int taskId, String taskName) {
        this.taskId = taskId;
        this.taskName = taskName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
