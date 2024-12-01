package com.example.demo.admin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class jdbc {
    Connection connection;
    Statement statement;
    jdbc() {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bookdbb",
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

    public ArrayList<Request> getInQueue() {
        ArrayList<Request> requestList = new ArrayList<>();

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
                Request request = new Request(id, bookId, userId, requestDate, status);
                requestList.add(request);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return requestList;
    }
    public List<Request> getPendingRequests() {
        List<Request> requests = new ArrayList<>();
        String sql = "SELECT * FROM request_borrow_book WHERE status = 'pending'";
try{
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Request request = new Request(
                        rs.getInt("id"),
                        rs.getString("book_id"),
                        rs.getInt("user_id"),
                        rs.getTimestamp("request_date").toString(),
                        rs.getString("status")
                );
                requests.add(request);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return requests;
    }

    public void updateRequestStatus(int id, String status) {
        String updateQuery = "UPDATE request_borrow_book SET status = ? WHERE id = ?";
        try{
        PreparedStatement stmt = connection.prepareStatement(updateQuery);

            // Đặt giá trị cho các tham số trong câu lệnh SQL
            stmt.setString(1, status); // Gán trạng thái mới
            stmt.setInt(2, id); // Gán ID của yêu cầu cần cập nhật

            // Thực thi câu lệnh cập nhật
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Request status updated successfully.");
            } else {
                System.out.println("No request found with the given ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error updating request status: " + e.getMessage());
        }
    }
}
