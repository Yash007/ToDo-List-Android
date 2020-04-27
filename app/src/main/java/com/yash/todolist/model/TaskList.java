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
    @ColumnInfo(name = "id")
    public Integer id;

    @ColumnInfo(name = "task_id")
    public Integer taskId;

    @ColumnInfo(name = "task_name")
    @NonNull
    public String taskName;

    public TaskList( Integer taskId, String taskName) {
        this.taskId = taskId;
        this.taskName = taskName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
