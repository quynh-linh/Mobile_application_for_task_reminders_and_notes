package com.example.doan.Model;

public class DinaryModel {
    public String id;
    public String content;
    public String img;
    public String find_id;
    public String title;
    public String date;
    public String user_id;

    public DinaryModel(String id,String content, String img, String find_id, String title, String date, String user_id) {
        this.id = id;
        this.content = content;
        this.img = img;
        this.find_id = find_id;
        this.title = title;
        this.date = date;
        this.user_id = user_id;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getFind_id() {
        return find_id;
    }

    public void setFind_id(String find_id) {
        this.find_id = find_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
