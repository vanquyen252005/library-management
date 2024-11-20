package com.example.demo.user;

import java.io.Serializable;
import java.sql.SQLException;

public abstract class User implements Serializable {
    private String id;
    private String username;
    private String password;
    private String name;
    private String role;
    private String phone;
    public User(){}
    public User(String id, String username, String password, String name, String role, String phone) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.role = role;
        this.phone = phone;
    }
    public User(String id, String username, String name) {
        this.id = id;
        this.username = username;
        this.name = name;

    }

    public User( String username, String password, String name, String role, String phone) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.role = role;
        this.phone = phone;
    }
    // Phương thức getter cho từng thuộc tính
    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getPhone() {
        return phone;
    }



    // Các phương thức setter
    public void setID(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public abstract boolean login(String username, String password) throws SQLException;
}
