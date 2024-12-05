package com.example.demo.admin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class Jdbc {
    Connection connection;
    Statement statement;
    Jdbc() {
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

    // get userdata
    public ResultSet getData(String username_, String password_){

        ResultSet resultSet = null;
        try {
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
                System.out.println("ok");
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

            PreparedStatement preStatement = connection.prepareStatement(query);

            ResultSet resultSet = preStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String bookId = resultSet.getString("book_id");
                int userId = resultSet.getInt("user_id");
                String requestDate = resultSet.getTimestamp("request_date").toString();
                String status = resultSet.getString("status");

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

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String bookId = resultSet.getString("book_id");
                int userId = resultSet.getInt("user_id");
                String requestDate = resultSet.getTimestamp("request_date").toString();
                String status = resultSet.getString("status");

                Request request = new Request(id, bookId, userId,"return", requestDate, status);
                requestList.add(request);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return requestList;
    }

    // get pending request from database
    public List<Request> getPendingRequests() {
        List<Request> requests = new ArrayList<>();
        String sqlBorrow = "SELECT * FROM request_borrow_book ";
        String sqlReturn = "SELECT * FROM request_return_book ";
try{
             PreparedStatement preparedStatement = connection.prepareStatement(sqlBorrow);
             ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Request request = new Request(
                        resultSet.getInt("id"),
                        resultSet.getString("book_id"),
                        resultSet.getInt("user_id"),
                        "borrow",
                        resultSet.getTimestamp("request_date").toString(),
                        resultSet.getString("status")
                );
                requests.add(request);
            }

     preparedStatement = connection.prepareStatement(sqlReturn);
     resultSet = preparedStatement.executeQuery();

    while (resultSet.next()) {
        Request request = new Request(
                resultSet.getInt("id"),
                resultSet.getString("book_id"),
                resultSet.getInt("user_id"),
                "return",
                resultSet.getTimestamp("request_date").toString(),
                resultSet.getString("status")
        );
        requests.add(request);
    }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return requests;
    }

    // update request status
    public void updateRequestStatus(Request request) {
        String table = " request_" + request.getType() + "_book ";
        String updateRequest = "UPDATE " + table + " SET status = ? WHERE id = ?";
        String updateBook = "UPDATE books \n" +
                "SET quantity = CASE \n" +
                "    WHEN ? = 1 THEN quantity - 1 \n" +
                "    ELSE quantity + 1 \n" +
                "END \n" +
                "WHERE ISBN = ?";
        String updateBorrowedBook = "insert into book_borrowed (`book_id`,\n" +
                "                `user_id`,\n" +
                "                `borrow_date`,\n" +
                "                `return_date`\n" +
                "                ) values\n" +
                "                (?,?,\n" +
                "                CURRENT_TIMESTAMP, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL ? DAY))";
        String deleteBorrowBook = "DELETE FROM book_borrowed \n" +
                "where user_id = ? and book_id = ? ";
        String notification = "INSERT INTO notification\n" +
                "(\n" +
                "`admin_id`,\n" +
                "`user_id`,\n" +
                "`content`,\n" +
                "`notify_date`)\n" +
                "VALUES\n" +
                "(?,?,'Request ' ? ' book has been ' ?,CURRENT_TIMESTAMP);";
        try{
        PreparedStatement preparedStatement = connection.prepareStatement(updateRequest);

            preparedStatement.setString(1, request.getStatus()); // Gán trạng thái mới
            preparedStatement.setInt(2, request.getId()); // Gán ID của yêu cầu cần cập nhật
            System.out.println(preparedStatement);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Request status updated successfully.");
            } else {
                System.out.println("No request found with the given ID.");
            }

            if (!request.getStatus().equals("rejected")) {
               //accept
                preparedStatement = connection.prepareStatement(updateBook);
                preparedStatement.setString(1, (request.getType().equals("return"))?"0":"1"); // Gán trạng thái mới
                preparedStatement.setString(2, request.getBookId()); // Gán ID của yêu cầu cần cập nhật
                System.out.println(preparedStatement);
                rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Request status updated successfully.");
                } else {
                    System.out.println("No request found with the given ID.");
                }
              if (!request.getType().equals("return")) {
                  //borrow accept
                  preparedStatement = connection.prepareStatement(updateBorrowedBook);
                  preparedStatement.setString(1,request.getBookId());
                  preparedStatement.setInt(2,request.getUserId());
                  preparedStatement.setInt(3,parseInt(request.getReturnDate()));
                  rowsAffected = preparedStatement.executeUpdate();
                  if (rowsAffected > 0) {
                      System.out.println("update book borrowed");
                  } else {
                      System.out.println("No update book borrowed.");
                  }

                  preparedStatement = connection.prepareStatement(notification);
                  preparedStatement.setString(1, AdminController.user.getId());
                  preparedStatement.setInt(2,request.getUserId());
                  preparedStatement.setString(3," borrow");
                  preparedStatement.setString(4," accepted");
                  rowsAffected = preparedStatement.executeUpdate();
                  if (rowsAffected > 0) {
                      System.out.println("update book borrowed");
                  } else {
                      System.out.println("No update book borrowed.");
                  }
              } else {
                  //return accept
                  preparedStatement = connection.prepareStatement(deleteBorrowBook);
                  preparedStatement.setInt(1,request.getUserId());
                  preparedStatement.setString(2,request.getBookId());
                  System.out.println(preparedStatement);
                  rowsAffected = preparedStatement.executeUpdate();
                  if (rowsAffected > 0) {
                      System.out.println("update book borrowed");
                  } else {
                      System.out.println("No update book borrowed.");
                  }

                  preparedStatement = connection.prepareStatement(notification);
                  preparedStatement.setString(1, AdminController.user.getId());
                  preparedStatement.setInt(2,request.getUserId());
                  preparedStatement.setString(3," return");
                  preparedStatement.setString(4," accepted");
                  rowsAffected = preparedStatement.executeUpdate();
                  if (rowsAffected > 0) {
                      System.out.println("update book borrowed");
                  } else {
                      System.out.println("No update book borrowed.");
                  }
              }}
            else {
                //rejected
                if (!request.getType().equals("return")) {
                    //borrow rejected

                    preparedStatement = connection.prepareStatement(notification);
                    preparedStatement.setString(1, AdminController.user.getId());
                    preparedStatement.setInt(2,request.getUserId());
                    preparedStatement.setString(3," borrow");
                    preparedStatement.setString(4," rejected");
                    rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("update book borrowed");
                    } else {
                        System.out.println("No update book borrowed.");
                    }
                } else {
                    //return rcted

                    preparedStatement = connection.prepareStatement(notification);
                    preparedStatement.setString(1, AdminController.user.getId());
                    preparedStatement.setInt(2,request.getUserId());
                    preparedStatement.setString(3," return");
                    preparedStatement.setString(4," rejected");
                    rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("update book borrowed");
                    } else {
                        System.out.println("No update book borrowed.");
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error updating request status: " + e.getMessage());
        }
    }
}
