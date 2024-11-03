package org.example.LibraryManagement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ConnectDB {
    private static final String URL = "jdbc:mysql://localhost:3306/bookdb";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public static Connection getConnection() {
        try {
            System.out.println("get connected success to database");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String GenerateISBN() {
        String ISBN = "";
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            ISBN += String.valueOf(rand.nextInt(10));
        }
        return ISBN;
    }

    public void addDocument(String title, String author, String publishYear, String publisher) {
        String sql = "INSERT INTO books (ISBN, Title, Author, PublishYear, Publisher) VALUES (?, ?, ?, ?, ?)";
        String ISBN = GenerateISBN();
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, ISBN);
            pstmt.setString(2, title);
            pstmt.setString(3, author);
            pstmt.setString(4, publishYear);
            pstmt.setString(5, publisher);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Book> searchDocuments(String keyword) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE Title LIKE ? OR Author LIKE ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + keyword + "%");
            pstmt.setString(2, "%" + keyword + "%");

            System.out.println("Executing query: " + pstmt.toString());

            ResultSet rs = pstmt.executeQuery();
            if (!rs.isBeforeFirst()) {
                System.out.println("No data returned from query.");
            }
            while (rs.next()) {
                books.add(new Book(
                        rs.getString("ISBN"),
                        rs.getString("Title"),
                        rs.getString("Author"),
                        rs.getString("Publisher"), // Thêm Publisher vào đối tượng Book
                        rs.getString("PublishYear") // Thêm PublishYear vào đối tượng Book
                ));
            }
            System.out.println("Finishing getting book list");

        } catch (SQLException e) {
            System.out.println("SQL exception: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Unexpected exception: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Returning book list with size: " + books.size());
        return books;
    }


    public void editDocument(String ISBN, String newTitle, String newAuthor, String newPublisher, String newPublishYear) {
        String selectSql = "SELECT title, author, publisher, publishYear FROM books WHERE ISBN = ?";
        String updateSql = "UPDATE books SET title = ?, author = ?, publisher = ?, publishYear = ? WHERE ISBN = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectSql);
             PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {

            // Truy vấn dữ liệu hiện tại
            selectStmt.setString(1, ISBN);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                // Kiểm tra và giữ giá trị cũ nếu trường mới rỗng
                String currentTitle = rs.getString("title");
                String currentAuthor = rs.getString("author");
                String currentPublisher = rs.getString("publisher");
                String currentPublishYear = rs.getString("publishYear");

                if (newTitle == null || newTitle.isEmpty()) {
                    newTitle = currentTitle;
                }
                if (newAuthor == null || newAuthor.isEmpty()) {
                    newAuthor = currentAuthor;
                }
                if (newPublisher == null || newPublisher.isEmpty()) {
                    newPublisher = currentPublisher;
                }
                if (newPublishYear == null || newPublishYear.isEmpty()) {
                    newPublishYear = currentPublishYear;
                }

                // Thực hiện cập nhật
                updateStmt.setString(1, newTitle);
                updateStmt.setString(2, newAuthor);
                updateStmt.setString(3, newPublisher);
                updateStmt.setString(4, newPublishYear);
                updateStmt.setString(5, ISBN);
                updateStmt.executeUpdate();
            } else {
                System.out.println("No record found with the provided ISBN.");
            }

        } catch (SQLException e) {
            System.out.println("SQL exception: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void deleteDocument(String ISBN) {
        String sql = "DELETE FROM books WHERE ISBN = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, ISBN);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}

