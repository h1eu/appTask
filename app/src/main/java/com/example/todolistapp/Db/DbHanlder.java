package com.example.todolistapp.Db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.todolistapp.Model.Task;

import java.util.ArrayList;

public class DbHanlder extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String NAME = "TaskDb";
    private static final String TASK_TB = "task_tb";
    private static final String ID = "id";
    private static final String CONTENT = "task";
    private static final String STATUS = "status";
    private static final String  CREATE_TASK_TABLE = "CREATE TABLE " + TASK_TB + "("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + STATUS + " INTEGER, "
            + CONTENT + " TEXT " + ")";
    private SQLiteDatabase db;
    public DbHanlder(Context context){
        super(context, NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TASK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TASK_TB);
        onCreate(db);
    }
    public void openDB(){
        db = this.getWritableDatabase();
    }
    public void insertTask(Task task){
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CONTENT,task.getContentTask());
        cv.put(STATUS,0);
        db.insert(TASK_TB,null,cv);
    }
    public ArrayList<Task> getAllTask(){
        ArrayList<Task> arrayList = new ArrayList<>();
        Cursor cursor= null;
        db.beginTransaction();
        try {
            cursor = db.query(TASK_TB,null,null,null,null,null,null,null);
            if(cursor != null){
                if(cursor.moveToFirst()){
                    do {
                        Task task = new Task();
                        task.setIdTast(cursor.getInt(cursor.getColumnIndex(ID)));
                        task.setStatusTask(cursor.getInt(cursor.getColumnIndex(STATUS)));
                        task.setContentTask(cursor.getString(cursor.getColumnIndex(CONTENT)));
                        arrayList.add(task);
                    }while (cursor.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            cursor.close();
        }
        return arrayList;
    }
    public void updateStatus(int id,int status){
        ContentValues cv = new ContentValues();
        cv.put(STATUS,status);
        db.update(TASK_TB,cv,ID + "=?" , new String[]{String.valueOf(id)});
    }
    public void updateContent(int id,String content){
        ContentValues cv = new ContentValues();
        cv.put(CONTENT,content);
        db.update(TASK_TB,cv,ID + "=?" , new String[]{String.valueOf(id)});
    }
    public void deleteTask(int id){
        db.delete(TASK_TB,ID + "=?" , new String[]{String.valueOf(id)});
    }
}
