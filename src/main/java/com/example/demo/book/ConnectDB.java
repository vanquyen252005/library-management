package com.example.demo.book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ConnectDB {
    private static ConnectDB instance;
    private Connection connection;

    private static final String URL = "jdbc:mysql://localhost:3306/bookdb";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    private ConnectDB() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the database successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized ConnectDB getInstance() {
        if (instance == null) {
            instance = new ConnectDB();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public String generateISBN() {
        StringBuilder ISBN = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            ISBN.append(rand.nextInt(10));
        }
        return ISBN.toString();
    }

    public void addDocument(String title, String author, String publishYear, String publisher) {
        String sql = "INSERT INTO books (ISBN, Title, Author, PublishYear, Publisher) VALUES (?, ?, ?, ?, ?)";
        String ISBN = generateISBN();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
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
        String sql;

        if (keyword == null || keyword.trim().isEmpty()) {
            sql = "SELECT * FROM books ORDER BY rating DESC LIMIT 80";
        } else {
            sql = "SELECT * FROM books WHERE Title LIKE ? OR Author LIKE ? ORDER BY rating DESC LIMIT 80";
        }

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            if (keyword != null && !keyword.trim().isEmpty()) {
                pstmt.setString(1, "%" + keyword + "%");
                pstmt.setString(2, "%" + keyword + "%");
            }

            System.out.println("Executing query: " + pstmt);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                books.add(new Book(
                        rs.getString("ISBN"),
                        rs.getString("Title"),
                        rs.getString("Author"),
                        rs.getString("Publisher"),
                        rs.getString("PublishYear"),
                        rs.getString("Image-URL-M")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public void editDocument(String ISBN, String newTitle, String newAuthor, String newPublisher, String newPublishYear) {
        String selectSql = "SELECT title, author, publisher, publishYear FROM books WHERE ISBN = ?";
        String updateSql = "UPDATE books SET title = ?, author = ?, publisher = ?, publishYear = ? WHERE ISBN = ?";
        try (PreparedStatement selectStmt = connection.prepareStatement(selectSql);
             PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {

            selectStmt.setString(1, ISBN);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                newTitle = (newTitle == null || newTitle.isEmpty()) ? rs.getString("title") : newTitle;
                newAuthor = (newAuthor == null || newAuthor.isEmpty()) ? rs.getString("author") : newAuthor;
                newPublisher = (newPublisher == null || newPublisher.isEmpty()) ? rs.getString("publisher") : newPublisher;
                newPublishYear = (newPublishYear == null || newPublishYear.isEmpty()) ? rs.getString("publishYear") : newPublishYear;

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
            e.printStackTrace();
        }
    }

    public void deleteDocument(String ISBN) {
        String sql = "DELETE FROM books WHERE ISBN = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, ISBN);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
