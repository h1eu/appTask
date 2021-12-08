package com.example.todolistapp.Model;

public class Task {
    private int idTast, statusTask;
    private String contentTask;

    public int getIdTast() {
        return idTast;
    }

    public void setIdTast(int idTast) {
        this.idTast = idTast;
    }

    public int getStatusTask() {
        return statusTask;
    }

    public void setStatusTask(int statusTask) {
        this.statusTask = statusTask;
    }

    public String getContentTask() {
        return contentTask;
    }

    public void setContentTask(String contentTask) {
        this.contentTask = contentTask;
    }
}
