package com.example.todolistapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.DialogCompat;
import androidx.core.content.ContextCompat;

import com.example.todolistapp.Db.DbHanlder;
import com.example.todolistapp.Model.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewTask extends BottomSheetDialogFragment {
    public static final String TAG = "ACTIONBOTTOMDIALOG";
    private EditText editText;
    private Button button;
    private DbHanlder dbHanlder;
    public static AddNewTask newInstance(){
        return new AddNewTask();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL,R.style.DialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_task,container,false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = getView().findViewById(R.id.edt_newTask);
        button = getView().findViewById(R.id.btn_addTash);

        dbHanlder = new DbHanlder(getActivity());
        dbHanlder.openDB();

        boolean isUpdate = false;
        final Bundle bundle = getArguments();
        if(bundle != null){
            isUpdate = true;
            String task = bundle.getString("task");
            editText.setText(task);
            if(task.length() > 0){
                button.setTextColor(ContextCompat.getColor(getContext(),R.color.home_screen));
            }
        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    button.setEnabled(false);
                    button.setTextColor(Color.GRAY);
                }
                else {
                    button.setEnabled(true);
                    button.setTextColor(ContextCompat.getColor(getContext(),R.color.home_screen));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        boolean finalIsUpdate = isUpdate;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                Log.e(TAG,text);
                if(finalIsUpdate){
                    dbHanlder.updateContent(bundle.getInt("id"),text);
                }
                else {
                    Task task= new Task();
                    task.setContentTask(text);
                    task.setStatusTask(0);
                    dbHanlder.insertTask(task);
                }
                dismiss();
            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        Activity activity = getActivity();
        if(activity instanceof DialogCloseListener){
            ((DialogCloseListener)activity).handleDialogClose(dialog);
        }
        super.onDismiss(dialog);
    }

}
