package com.yash.todolist.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.yash.todolist.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task ORDER BY id DESC")
    LiveData<List<Task>> findAllTask();

    @Query("SELECT * FROM task WHERE id = :taskId LIMIT 1")
    Task findTaskById(int taskId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Task task);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(Task task);

    @Delete
    void deleteById(Task task);

    @Query("DELETE FROM task")
    void deleteAll();
}
