package com.example.demo.admin;

import com.example.demo.student.student;

import java.sql.*;

public class jdbc {
    Connection connection;
    Statement statement;
    jdbc() {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/lib",
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
            String query = "SELECT * FROM userdata WHERE username = " + username_
                    +" AND password = " +password_ + ";";
            System.out.println(query);
            resultSet = statement.executeQuery(query);
            if (resultSet != null)
            {
                System.out.println("hehe");
            } else {
                System.out.println("connect fail");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }
    public boolean addStudentData(student newUser) {
        String id = newUser.getId();                     // Gán id
        String username = newUser.getUsername();         // Gán username
        String password = newUser.getPassword();         // Gán password
        String name = newUser.getName();                 // Gán name
        String role = newUser.getRole();                 // Gán role
        String phone = newUser.getPhone();               // Gán phone
        String classname = newUser.getClassname();       // Gán classname
        ResultSet resultSet = null;
        try {
            String query  = "INSERT INTO userdata column(id, username,password,name,role,phone,classname) " +
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
    public ResultSet getStudentData(String op, String info) {
        ResultSet resultSet = null;
        try {
            info = "'%" + info + "%'";
            String query = "SELECT * FROM userdata WHERE " + op + " LIKE " + info + " and role = 'Student';";
            System.out.println(query);
            resultSet = statement.executeQuery(query);
            if (resultSet != null)
            {
                System.out.println("OK");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}
