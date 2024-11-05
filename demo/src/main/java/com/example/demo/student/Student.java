package com.example.demo.student;

import com.example.demo.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Student extends User {
    private String classname;
    private jdbc DataUser = new jdbc();
    public Student() {
        super();

    }
    public Student(String id, String username, String password, String name, String role, String phone, String classname) {
        super(id, username, password, name, role, phone);
        this.classname = classname;
    }
    public Student(String id, String username, String name) {
        super(id, username, name);
    }
    public void setClassname(String classname) {
        this.classname = classname;
    }
    public String getClassname() {
        return classname;
    }
    @Override
    public boolean login(String username, String password) throws SQLException {
        ResultSet resultSet = DataUser.getData(username, password);
        if (resultSet.next() != false) {
            if (resultSet.getString("role").equals("admin")) {
                super.setID(resultSet.getString("id"));
                super.setUsername(resultSet.getString("username"));
                super.setPassword(resultSet.getString("password"));
                super.setName(resultSet.getString("name"));
                super.setRole(resultSet.getString("role"));
                super.setPhone(resultSet.getString("phone")); // Chuyển đổi kiểu nếu cần
                return true;
            }

        }
        return false;
    }
}