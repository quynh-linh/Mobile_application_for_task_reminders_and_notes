package com.example.doan;

import java.util.Date;

public class Task {
    private String name;
    private String description;
    private String startTask;
    private String endTask;
    private String timeStart;
    private String timeEnd;
    public Task(){

    }
    public Task(String name, String description, String startTask, String endTask, String timeStart, String timeEnd) {
        this.name = name;
        this.description = description;
        this.startTask = startTask;
        this.endTask = endTask;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public Task(String name, String description, String timeStart) {
        this.name = name;
        this.description = description;
        this.timeStart = timeStart;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartTask() {
        return startTask;
    }

    public void setStartTask(String startTask) {
        this.startTask = startTask;
    }

    public String getEndTask() {
        return endTask;
    }

    public void setEndTask(String endTask) {
        this.endTask = endTask;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }
}
