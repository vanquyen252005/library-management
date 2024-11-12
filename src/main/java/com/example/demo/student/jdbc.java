package com.example.demo.student;

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
        String query = "SELECT * FROM userdata WHERE username = ? AND password = ?";

        try {
            // Sử dụng PreparedStatement để tránh SQL Injection
            PreparedStatement pstmt = connection.prepareStatement(query);

            // Gán giá trị cho các tham số ? trong câu truy vấn
            pstmt.setString(1, username_);
            pstmt.setString(2, password_);

            // Thực thi truy vấn
            resultSet = pstmt.executeQuery();

            // Kiểm tra nếu có kết quả
            if (resultSet != null) {
                System.out.println("Login successful or data found");
            }
        } catch (SQLException e) {
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
            String query = "SELECT * FROM userdata WHERE username = " + username_ + ";";
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
            String query = "SELECT * FROM userdata WHERE id = " + id + ";";
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
    public void deleteStudent(String id) {
        String query = "DELETE FROM userdata WHERE id = ?";

        try {
            // Sử dụng PreparedStatement để xóa sinh viên theo ID
            PreparedStatement pstmt = connection.prepareStatement(query);

            // Gán giá trị ID cho tham số ?
            pstmt.setString(1, id);

            // Thực thi câu lệnh DELETE
            int rowsAffected = pstmt.executeUpdate();

            // Kiểm tra xem có bản ghi nào bị xóa hay không
            if (rowsAffected > 0) {
                System.out.println("Student with ID " + id + " deleted successfully.");
            } else {
                System.out.println("No student found with ID " + id + ".");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
