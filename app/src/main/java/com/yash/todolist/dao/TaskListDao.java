package com.yash.todolist.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.yash.todolist.model.TaskList;

import java.util.List;

@Dao
public interface TaskListDao {

    @Query("SELECT * FROM tasklist WHERE id = :taskListId ORDER BY id ASC")
    LiveData<List<TaskList>> findAllTasks(int taskListId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(TaskList taskList);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(TaskList taskList);

    @Query("DELETE FROM tasklist WHERE id = :taskListId")
    void deleteById(int taskListId);

    @Query("DELETE FROM tasklist WHERE task_id = :taskId")
    void deleteByTaskId(int taskId);

    @Query("DELETE FROM tasklist")
    void deleteAll();
}
