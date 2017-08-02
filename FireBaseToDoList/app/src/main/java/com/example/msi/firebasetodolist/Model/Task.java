package com.example.msi.firebasetodolist.Model;

/**
 * Created by M.S.I on 8/2/2017.
 */

public class Task {
    public String key;
    public String todayTask;

    public Task() {
    }

    public Task(String key, String todayTask) {
        this.key = key;
        this.todayTask = todayTask;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTodayTask() {
        return todayTask;
    }

    public void setTodayTask(String todayTask) {
        this.todayTask = todayTask;
    }
}
