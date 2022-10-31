package com.example.doan;

public class User {
    private int id;
    private String nameLogin;
    private String fullname;
    private String ngaySinh;
    private int phone;
    private String password;

    public User(int id, String nameLogin, String fullname, String ngaySinh, int phone, String password) {
        this.id = id;
        this.nameLogin = nameLogin;
        this.fullname = fullname;
        this.ngaySinh = ngaySinh;
        this.phone = phone;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameLogin() {
        return nameLogin;
    }

    public void setNameLogin(String nameLogin) {
        this.nameLogin = nameLogin;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
