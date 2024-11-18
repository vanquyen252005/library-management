package com.example.demo.admin;

import com.example.demo.student.Student;

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
}
