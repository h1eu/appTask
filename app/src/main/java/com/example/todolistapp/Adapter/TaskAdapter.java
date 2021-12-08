package com.example.todolistapp.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistapp.AddNewTask;
import com.example.todolistapp.Db.DbHanlder;
import com.example.todolistapp.MainActivity;
import com.example.todolistapp.Model.Task;
import com.example.todolistapp.R;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private ArrayList<Task> taskArrayList;
    private MainActivity mainActivity;
    private DbHanlder db;
    public TaskAdapter(DbHanlder db,MainActivity mainActivity){
        this.db= db;
        this.mainActivity = mainActivity;
    }
    public ViewHolder onCreateViewHolder(ViewGroup parent, int view){
        View itemsview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_task,parent,false);
        return new ViewHolder(itemsview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        db.openDB();
        Task item = taskArrayList.get(position);
        holder.task.setText(item.getContentTask());
        holder.task.setChecked(check(item.getStatusTask()));
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    db.updateStatus(item.getIdTast(),1);
                }
                else {
                    db.updateStatus(item.getIdTast(),0);
                }
            }
        });
    }

    private boolean check(int n){
        return n != 0 ;
    }

    public void setTask(ArrayList<Task> task){
        taskArrayList = task;
        notifyDataSetChanged();
    }
    public Context getContext(){
        return mainActivity;
    }

    public void edititem(int pos){
        Task item = taskArrayList.get(pos);
        Bundle bundle = new Bundle();
        bundle.putInt("id",item.getIdTast());
        bundle.putString("task",item.getContentTask());
        AddNewTask addNewTask = new AddNewTask();
        addNewTask.setArguments(bundle);
        addNewTask.show(mainActivity.getSupportFragmentManager(),AddNewTask.TAG);
    }
    @Override
    public int getItemCount() {
        return taskArrayList.size();
    }
    public void  deleteItem(int pos){
        Task item = taskArrayList.get(pos);
        db.deleteTask(item.getIdTast());
        taskArrayList.remove(pos);
        notifyDataSetChanged();

    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox task;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            task = itemView.findViewById(R.id.cb_task);
        }
    }
}

