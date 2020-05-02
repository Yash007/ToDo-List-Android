package com.yash.todolist.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.yash.todolist.model.TaskList;

import java.util.List;

@Dao
public interface TaskListDao {

    @Query("SELECT * FROM tasklist where task_id= :taskId ORDER BY tid ASC")
    LiveData<List<TaskList>> findAllTasks(int taskId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(TaskList taskList);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(TaskList taskList);

    @Delete
    void delete(TaskList taskList);

    @Query("DELETE FROM tasklist WHERE task_id = :taskId")
    void deleteByTaskId(int taskId);

}
