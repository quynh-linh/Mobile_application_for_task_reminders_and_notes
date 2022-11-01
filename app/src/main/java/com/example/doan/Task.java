package com.example.doan;

import java.util.Date;

public class Task {
    private String name;
    private String description;
    private String date;
    private String check_status;
    private String time;
    private String user_id ;
    public Task(){

    }

    public Task(String name, String description, String date, String check_status, String time, String user_id) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.check_status = check_status;
        this.time = time;
        this.user_id = user_id;
    }

    public Task(String name, String description, String date, String check_status, String time) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.check_status = check_status;
        this.time = time;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCheck_status() {
        return check_status;
    }

    public void setCheck_status(String check_status) {
        this.check_status = check_status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
