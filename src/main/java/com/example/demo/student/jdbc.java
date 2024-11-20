package com.example.demo.student;

import com.example.demo.book.Book;
import com.example.demo.book.Book_borrowed;

import java.sql.*;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

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

    public ResultSet getData(String username_, String password_) {

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
            if (resultSet != null) {
                System.out.println("hehe");
            } else {
                System.out.println("connect fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public void addStudentData(Student newUser) {
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

            PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setString(1, id);            // Gán giá trị cho id
            pstmt.setString(2, username_);   // Gán giá trị cho username
            pstmt.setString(3, password_);   // Gán giá trị cho password
            pstmt.setString(4, name);       // Gán giá trị cho name
            pstmt.setString(5, role);       // Gán giá trị cho role
            pstmt.setString(6, phone);      // Gán giá trị cho phone
            pstmt.setString(7, classname);  // Gán giá trị cho classname

            pstmt.executeUpdate();

        } catch (Exception e) {
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
            if (resultSet != null) {
                System.out.println("OK");
            }
        } catch (Exception e) {
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


            int rowsAffected = pstmt.executeUpdate();


            if (rowsAffected > 0) {
                System.out.println("Student with ID " + id + " deleted successfully.");
            } else {
                System.out.println("No student found with ID " + id + ".");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Book_borrowed> loadBorrowedBook(int user_id) {
        ArrayList<Book_borrowed> listBook = new ArrayList<>();
        String query = "SELECT book_id,borrow_date, return_date FROM book_borrowed WHERE user_id = ?";

        try {

            PreparedStatement pstmt = connection.prepareStatement(query);


            pstmt.setInt(1, user_id);


            ResultSet resultSet = pstmt.executeQuery();


            while (resultSet.next()) {
                Book_borrowed book = new Book_borrowed();

                book.setISBN(resultSet.getString("book_id"));
                book.setBorrow_date(resultSet.getString("borrow_date"));
                book.setReturn_date(resultSet.getString("return_date"));

                listBook.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listBook;
    }

    public void updateUserProfile(String userId, String username, String name, String phone, String userClass) {
        // Câu lệnh SQL để cập nhật dữ liệu
        String query = "UPDATE userdata SET username = ?, name = ?, phone = ?, classname = ? WHERE id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);

            // Gán giá trị vào câu lệnh SQL
            stmt.setString(1, username);
            stmt.setString(2, name);
            stmt.setString(3, phone);
            stmt.setString(4, userClass);
            stmt.setInt(5, parseInt(userId));

            // Thực hiện câu lệnh
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Cập nhật dữ liệu thành công!");
            } else {
                System.out.println("Không tìm thấy người dùng với ID: " + userId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
