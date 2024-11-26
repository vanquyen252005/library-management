package com.example.demo.admin;

import com.example.demo.student.Student;

import java.sql.*;
import java.util.ArrayList;

public class jdbc {
    Connection connection;
    Statement statement;
    jdbc() {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/oop",
                    "root",
                    "123456"
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

    public ArrayList<RequestBook> getInQueue() {
        ArrayList<RequestBook> requestList = new ArrayList<>();

        try {
            String query = "SELECT * FROM request_borrow_book WHERE status = 'pending' ORDER BY request_date ASC";

            // Dùng PreparedStatement để tạo truy vấn
            PreparedStatement pstmt = connection.prepareStatement(query);

            // Thực thi truy vấn
            ResultSet rs = pstmt.executeQuery();

            // Duyệt qua kết quả và thêm vào danh sách
            while (rs.next()) {
                int id = rs.getInt("id");
                String bookId = rs.getString("book_id");
                int userId = rs.getInt("user_id");
                String requestDate = rs.getTimestamp("request_date").toString();
                String status = rs.getString("status");

                // Tạo đối tượng RequestBook và thêm vào danh sách
                RequestBook request = new RequestBook(id, bookId, userId, requestDate, status);
                requestList.add(request);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return requestList;
    }
}
