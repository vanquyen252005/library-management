package com.example.demo.student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class jdbc {
    Connection connection;
    Statement statement;
    jdbc() {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/userdata",
                    "root",
                    "123456789"
            );

            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ResultSet getData(String username_, String password_){

        ResultSet resultSet = null;
        try {
            username_ = "'" + username_ + "'";
            password_ = "'" + password_ + "'";
            String query = "SELECT * FROM information WHERE username = " + username_
                    +" AND password = " +password_ + ";";
            System.out.println(query);
            resultSet = statement.executeQuery(query);
            if (resultSet != null)
            {
                System.out.println("hehe");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }
    public boolean addStudentData(Student newUser) {
        String id = newUser.getId();                     // Gán id
        String username = newUser.getUsername();         // Gán username
        String password = newUser.getPassword();         // Gán password
        String name = newUser.getName();                 // Gán name
        String role = newUser.getRole();                 // Gán role
        String phone = newUser.getPhone();               // Gán phone
        String classname = newUser.getClassname();       // Gán classname
        ResultSet resultSet = null;
        try {
            String query  = "INSERT INTO information column(id, username,password,name,role,phone,classname) " +
                    "values(id, username,password,name,role,phone,classname)";
            System.out.println(query);
            resultSet = statement.executeQuery(query);
            if (resultSet != null)
            {
                //System.out.println();
                return true;

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public ResultSet getStudentDataByUsername(String username_) {
        ResultSet resultSet = null;
        try {
            username_ = "'" + username_ + "'";
            String query = "SELECT * FROM information WHERE username = " + username_ + ";";
            System.out.println(query);
            resultSet = statement.executeQuery(query);
            if (resultSet != null)
            {
                System.out.println("hehe");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }
    public ResultSet getStudentDataById(String id) {
        ResultSet resultSet = null;
        try {
            id = "'" + id + "'";
            String query = "SELECT * FROM information WHERE id = " + id + ";";
            System.out.println(query);
            resultSet = statement.executeQuery(query);
            if (resultSet != null)
            {
                System.out.println("hehe");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}
