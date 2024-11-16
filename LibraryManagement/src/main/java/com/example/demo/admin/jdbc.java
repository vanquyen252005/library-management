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
//            username_ = "'" + username_ + "'";
//            password_ = "'" + password_ + "'";
//            String query = "SELECT * FROM userdata WHERE username = " + username_
//                    +" AND password = " +password_ + ";";
            String query = "SELECT * FROM userdata WHERE username = ? AND password = ?";

            // Dùng PreparedStatement để tránh SQL Injection
            PreparedStatement pstmt = connection.prepareStatement(query);

            // Gán giá trị cho tham số
            pstmt.setString(1, username_);
            pstmt.setString(2, (password_));

            // Thực thi câu lệnh
            resultSet = pstmt.executeQuery();
            System.out.println(query);
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
    public void addStudentData(student newUser) {
        String id = newUser.getId();                     // Gán id
        String username_ = newUser.getUsername();         // Gán username
        String password_ = newUser.getPassword();         // Gán password
        String name = newUser.getName();                 // Gán name
        String role = newUser.getRole();                 // Gán role
        String phone = newUser.getPhone();               // Gán phone
        String classname = newUser.getClassname();       // Gán classname
        ResultSet resultSet = null;
        try {
            String query = "INSERT INTO userdata (id, username, password, name, role, phone, classname) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

// Sử dụng PreparedStatement
            PreparedStatement pstmt = connection.prepareStatement(query);

// Gán giá trị cho các tham số
            pstmt.setString(1, id);            // Gán giá trị cho id
            pstmt.setString(2, username_);   // Gán giá trị cho username
            pstmt.setString(3, password_);   // Gán giá trị cho password
            pstmt.setString(4, name);       // Gán giá trị cho name
            pstmt.setString(5, role);       // Gán giá trị cho role
            pstmt.setString(6, phone);      // Gán giá trị cho phone
            pstmt.setString(7, classname);  // Gán giá trị cho classname
            // Thực thi câu lệnh
           pstmt.executeUpdate();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
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
