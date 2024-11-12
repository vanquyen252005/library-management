package com.example.demo.student;

import com.example.demo.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class student extends User {
    private String classname;
    private jdbc Request = new jdbc();
    public student() {
        super();

    }
    public student(String id, String username, String password, String name, String role, String phone, String classname) {
        super(id, username, password, name, role, phone);
        this.classname = classname;
    }
    public student(String id, String username, String name) {
        super(id, username, name);
    }
    public void setClassname(String classname) {
        this.classname = classname;
    }
    public String getClassname() {
        return classname;
    }
    @Override
    public boolean login(String username, String password) {
        try{
            ResultSet resultSet = Request.getData(username, password);
            while (resultSet.next() != false) {
                if (resultSet.getString("role").equals("Student")) {
                    super.setID(resultSet.getString("id"));
                    super.setUsername(resultSet.getString("username"));
                    super.setPassword(resultSet.getString("password"));
                    super.setName(resultSet.getString("name"));
                    super.setRole(resultSet.getString("role"));
                    super.setPhone(resultSet.getString("phone"));
                    this.setClassname("classname");
                    return true;
                }

            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public student getStudent() {
        return this;
    }
    public void deleteStudent() {
        Request.deleteStudent(super.getId());
    }

}