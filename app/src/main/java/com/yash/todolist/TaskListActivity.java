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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yash.todolist.adapter.TaskAdapter;
import com.yash.todolist.adapter.TaskListAdapter;
import com.yash.todolist.helper.RecyclerItemListTouchHelper;
import com.yash.todolist.helper.RecyclerItemTouchHelper;
import com.yash.todolist.model.TaskList;
import com.yash.todolist.repository.TaskListRepository;
import com.yash.todolist.view.TaskListViewModel;
import com.yash.todolist.view.ViewModelFactory;

import java.util.List;

public class TaskListActivity extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener{
    private int taskId;
    private String taskTitle;
    private TaskListAdapter taskListAdapter;

    private TaskListViewModel taskListViewModel;
    private Vibrator vibrator;
    private ImageView notListFound;
    private FloatingActionButton addListButton;
    private Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        taskId = getIntent().getExtras().getInt("TASK_ID", 1);
        taskTitle = getIntent().getExtras().getString("TASK_TITLE", "ToDo List");

        getSupportActionBar().setTitle(taskTitle);
        final RecyclerView recyclerView = findViewById(R.id.taskListItemsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        taskListAdapter = new TaskListAdapter(TaskListActivity.this, taskId);
        recyclerView.setAdapter(taskListAdapter);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        notListFound = findViewById(R.id.noTaskListItemsFoundImage);

        taskListViewModel = new ViewModelProvider(this, new ViewModelFactory(this.getApplication(), taskId)).get(TaskListViewModel.class);
        taskListViewModel.getTaskLists().observe(this, new Observer<List<TaskList>>() {
            @Override
            public void onChanged(List<TaskList> taskLists) {
                taskListAdapter.setTaskLists(taskLists);
                if(taskListAdapter.getItemCount() == 0) {
                    notListFound.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
                else    {
                    notListFound.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
        });

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemListTouchHelper(0, ItemTouchHelper.LEFT, this, vibrator);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        addListButton = findViewById(R.id.addNewTaskListItemButton);
        addListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTaskList();
            }
        });
    }

    public void addTaskList()   {
        dialog = new Dialog(TaskListActivity.this);
        dialog.setContentView(R.layout.dialog_items);
        dialog.getWindow().getAttributes().width = LinearLayout.LayoutParams.MATCH_PARENT;
        dialog.show();

        final EditText taskTitle = dialog.findViewById(R.id.dialogItemTitle);
        Button addButton = dialog.findViewById(R.id.dialogAddItemButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskTitleString = taskTitle.getText().toString();
                if(taskTitleString.isEmpty() == true || taskTitleString.length() == 0)    {
                    taskTitle.setError("Add task title");
                }
                else    {
                    TaskListRepository taskListRepository = new TaskListRepository(getApplication(), taskId);
                    taskListRepository.insert(new TaskList(taskId, taskTitleString));

                    Toast.makeText(getApplicationContext(), "Item saved", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            }
        });
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof TaskListAdapter.TaskListHolder) {
            taskListViewModel.delete(taskListAdapter.getTaskListAt(viewHolder.getAdapterPosition()));
            Toast.makeText(TaskListActivity.this, "item deleted", Toast.LENGTH_LONG).show();
        }
    }
}
