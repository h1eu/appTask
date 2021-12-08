package com.example.todolistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.todolistapp.Adapter.TaskAdapter;
import com.example.todolistapp.Db.DbHanlder;
import com.example.todolistapp.Model.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements DialogCloseListener  {
    private RecyclerView taskRyc;
    private TaskAdapter taskAdapter;
    private ArrayList<Task> taskArrayList;
    private DbHanlder db;
    private FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        db = new DbHanlder(this);
        db.openDB();

        taskArrayList= new ArrayList<Task>();

        taskRyc = findViewById(R.id.rycv_task);
        taskRyc.setLayoutManager(new LinearLayoutManager(this));

        taskAdapter = new TaskAdapter(db,this);
        taskRyc.setAdapter(taskAdapter);

        fab = findViewById(R.id.add_task);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemHelper(taskAdapter));
        itemTouchHelper.attachToRecyclerView(taskRyc);
        taskArrayList = db.getAllTask();
        Collections.reverse(taskArrayList);
        taskAdapter.setTask(taskArrayList);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager(),AddNewTask.TAG);
            }
        });

    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        taskArrayList = db.getAllTask();
        Collections.reverse(taskArrayList);
        taskAdapter.setTask(taskArrayList);
        taskAdapter.notifyDataSetChanged();

    }

}