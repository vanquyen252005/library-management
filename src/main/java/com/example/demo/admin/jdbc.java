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

    public ArrayList<Request> getBorrowRequest() {
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
                Request request = new Request(id, bookId, userId,"borrow", requestDate, status);
                requestList.add(request);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return requestList;
    }
    public ArrayList<Request> getReturnRequest() {
        ArrayList<Request> requestList = new ArrayList<>();

        try {
            String query = "SELECT * FROM request_return_book WHERE status = 'pending' ORDER BY request_date ASC";

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
                Request request = new Request(id, bookId, userId,"return", requestDate, status);
                requestList.add(request);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return requestList;
    }
    public List<Request> getPendingRequests() {
        List<Request> requests = new ArrayList<>();
        String sqlBorrow = "SELECT * FROM request_borrow_book WHERE status = 'pending'";
        String sqlReturn = "SELECT * FROM request_return_book WHERE status = 'pending'";
try{
             PreparedStatement pstmt = connection.prepareStatement(sqlBorrow);
             ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Request request = new Request(
                        rs.getInt("id"),
                        rs.getString("book_id"),
                        rs.getInt("user_id"),
                        "borrow",
                        rs.getTimestamp("request_date").toString(),
                        rs.getString("status")
                );
                requests.add(request);
            }

     pstmt = connection.prepareStatement(sqlReturn);
     rs = pstmt.executeQuery();

    while (rs.next()) {
        Request request = new Request(
                rs.getInt("id"),
                rs.getString("book_id"),
                rs.getInt("user_id"),
                "return",
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

    public void updateRequestStatus(Request request) {
        String table = " request_" + request.getType() + "_book ";
        String updateRequest = "UPDATE " + table + " SET status = ? WHERE id = ?";
        String updateBook = "UPDATE bookdb.books \n" +
                "SET quantity = CASE \n" +
                "    WHEN ? = 1 THEN quantity - 1 \n" +
                "    ELSE quantity + 1 \n" +
                "END \n" +
                "WHERE ISBN = ?";
        try{
        PreparedStatement stmt = connection.prepareStatement(updateRequest);

            // Đặt giá trị cho các tham số trong câu lệnh SQL
            stmt.setString(1, request.getStatus()); // Gán trạng thái mới
            stmt.setInt(2, request.getId()); // Gán ID của yêu cầu cần cập nhật

            // Thực thi câu lệnh cập nhật
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Request status updated successfully.");
            } else {
                System.out.println("No request found with the given ID.");
            }

            if (!request.getStatus().equals("rejected")) {
                stmt = connection.prepareStatement(updateBook);
                stmt.setString(1, (request.getType().equals("return"))?"0":"1"); // Gán trạng thái mới
                stmt.setString(2, request.getBookId()); // Gán ID của yêu cầu cần cập nhật

                // Thực thi câu lệnh cập nhật
                rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Request status updated successfully.");
                } else {
                    System.out.println("No request found with the given ID.");
                }
            }
            // Đặt giá trị cho các tham số trong câu lệnh SQL


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error updating request status: " + e.getMessage());
        }
    }
}
