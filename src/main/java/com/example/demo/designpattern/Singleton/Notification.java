package com.example.demo.designpattern.Singleton;

public class Notification {
    private String content;
    private int user_id;
    private String notify_date;
    private int id;
    private int admin_id;

    public Notification() {
    }

    public Notification(String content, int user_id, String notify_date, int id, int admin_id) {
        this.content = content;
        this.user_id = user_id;
        this.notify_date = notify_date;
        this.id = id;
        this.admin_id = admin_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getNotify_date() {
        return notify_date;
    }

    public void setNotify_date(String notify_date) {
        this.notify_date = notify_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }
}