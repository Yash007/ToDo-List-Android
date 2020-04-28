package com.yash.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yash.todolist.adapter.TaskAdapter;
import com.yash.todolist.helper.RecyclerItemTouchHelper;
import com.yash.todolist.model.Task;
import com.yash.todolist.repository.TaskRepository;
import com.yash.todolist.view.TaskViewModel;

import java.util.List;

/**
 * Purpose: MainActivity Class works on Launching Activity.
 */
public class MainActivity extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    private Dialog dialog;

    private TaskViewModel taskViewModel;

    private FloatingActionButton addNewTaskButton;
    private TaskAdapter taskAdapter;

    private Vibrator vibrator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.taskListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        taskAdapter = new TaskAdapter();
        recyclerView.setAdapter(taskAdapter);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        taskViewModel.getAllTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                taskAdapter.setTasks(tasks);
            }
        });

        addNewTaskButton = findViewById(R.id.addNewListButton);
        addNewTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayDialog();
            }
        });

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this, vibrator);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

    }

    private void displayDialog()    {
        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_new_task);
        dialog.getWindow().getAttributes().width = LinearLayout.LayoutParams.MATCH_PARENT;
        dialog.show();

        final EditText taskTitle = dialog.findViewById(R.id.dialogTaskTitle);
        Button addButton = dialog.findViewById(R.id.dialogAddButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskTitleString = taskTitle.getText().toString();
                if(taskTitleString.isEmpty() == true || taskTitleString.length() == 0)    {
                    taskTitle.setError("Add task title");
                }
                else    {
                    TaskRepository taskRepository = new TaskRepository(getApplication());
                    taskRepository.insert(new Task(taskTitleString));

                    Toast.makeText(getApplicationContext(), "Task Saved", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_task,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())   {
            case R.id.deleteAllTasks:
               taskViewModel.deleteAll();
               Toast.makeText(this, "All tasks deleted", Toast.LENGTH_LONG).show();
               return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof TaskAdapter.TaskHolder) {
            taskViewModel.delete(taskAdapter.getTaskAt(viewHolder.getAdapterPosition()));
            Toast.makeText(MainActivity.this, "Task deleted", Toast.LENGTH_LONG).show();
        }
    }
}
