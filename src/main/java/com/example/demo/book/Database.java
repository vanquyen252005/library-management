package com.example.demo.book;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private static final String URL = "jdbc:mysql://localhost:3306/bookdb";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";
    private ApiService API;

    public static Connection getConnection() {
        try {
            System.out.println("get connected success to database");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isExistImage(String Image_Link, ImageView imageView) {
        try {
            java.net.URL url = new URL(Image_Link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream input = connection.getInputStream();
                Image image = new Image(input);
                imageView.setImage(image);

                if(imageView.getImage() == null || ( imageView.getFitWidth() < 50 && imageView.getFitHeight() < 50)) {
                    System.out.println("reject getting image");
                    System.out.println(imageView.getFitWidth());
                    return false;
                }
                return true;
            } else {
                System.err.println("Error loading image: Server returned HTTP response code: " + responseCode);
            }
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
        }

        return true;
    }

    public List<Book> searchDocuments(String keyword) {
        List<Book> books = new ArrayList<>();
        String sql;

        // If keyword is empty, fetch top-rated books
        if (keyword == null || keyword.trim().isEmpty()) {
            sql = "SELECT * FROM books ORDER BY rating DESC LIMIT 50";
        } else {
            // If keyword is provided, search by Title or Author, then order by rating
            sql = "SELECT * FROM books WHERE Title LIKE ? ORDER BY rating DESC LIMIT 50";
        }

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set parameters only if keyword is not empty
            if (keyword != null && !keyword.trim().isEmpty()) {
                pstmt.setString(1, "%" + keyword + "%");
            }

            System.out.println("Executing query: " + pstmt.toString());

            ResultSet rs = pstmt.executeQuery();
            if (!rs.isBeforeFirst()) {
                System.out.println("No data returned from database so trying to testing api source.");
                List<Book> sample = API.searchBooks(keyword);
                for(Book b : sample) books.add(b);
                if(books.size() == 0) {
                    System.out.println("No data returned from API");
                }
            }

            while (rs.next()) {
                ImageView imageView = new ImageView();
                //if(isExistImage(rs.getString("Image-URL-M"),  imageView)) {
                books.add(new Book(
                        rs.getString("ISBN"),
                        rs.getString("Title"),
                        rs.getString("Author"),
                        rs.getString("Publisher"),
                        rs.getString("PublishYear"),
                        rs.getString("Image-URL-M")
                ));
                // }
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

    public Book getBookByISBN(String isbn) {
        Book book = null;
        String sql = "SELECT * FROM books WHERE ISBN = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, isbn);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                book = new Book(
                        rs.getString("ISBN"),
                        rs.getString("Title"),
                        rs.getString("Author"),
                        rs.getString("Publisher"),
                        rs.getString("PublishYear"),
                        rs.getString("Image-URL-M")
                );
            } else {
                System.out.println("No book found with ISBN: " + isbn);
            }
        } catch (SQLException e) {
            System.out.println("SQL exception: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Unexpected exception: " + e.getMessage());
            e.printStackTrace();
        }

        return book;
    }

    public boolean sendBorrowRequest(String userID, String ISBN) {
        String query = "INSERT INTO request_borrow_book (book_id, user_id, status) VALUES (?, ?, 'pending')";

        // Tạo kết nối cơ sở dữ liệu
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, ISBN);  // Gán giá trị cho book_id
            preparedStatement.setInt(2, Integer.parseInt(userID));  // Gán giá trị cho user_id

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Borrow request has been sent successfully.");
                return true;
            } else {
                System.out.println("Failed to send borrow request.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String RequestStatus(int userID, String ISBN) {
        String status = "";
        String query = "SELECT status FROM request_borrow_book WHERE user_id = ? AND book_id = ? ORDER BY request_date DESC LIMIT 1";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userID); // Gán user_id
            preparedStatement.setString(2, ISBN); // Gán book_id (ISBN)

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                status = resultSet.getString("status"); // Lấy giá trị status
            } else {
                System.out.println("No borrow request found for this user and book:" + userID + ", " + ISBN);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

//    public List<BookDetailController.Comment> getCommentsByISBN(String isbn) {
//        List<BookDetailController.Comment> comments = new ArrayList<>();
//        String query = "SELECT author, text FROM comments WHERE isbn = ?";
//
//        try (Connection connection = getConnection();
//             PreparedStatement statement = connection.prepareStatement(query)) {
//
//            statement.setString(1, isbn);
//            ResultSet resultSet = statement.executeQuery();
//
//            while (resultSet.next()) {
//                String author = resultSet.getString("author");
//                String text = resultSet.getString("text");
//                comments.add(new BookDetailController.Comment(text, author));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return comments;
//    }

    public List<Comment> GetCommentList(String ISBN) {
        List<Comment> commentList = new ArrayList<>();

        // Truy vấn để lấy danh sách các bình luận
        String query = "SELECT id, book_id, user_id, content, created_at FROM book_comments WHERE book_id = ? AND parent_comment_id is null ";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            // Thiết lập tham số bookId
            stmt.setString(1,ISBN);

            // Thực thi truy vấn
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String bookID = rs.getString("book_id");
                    int userId = rs.getInt("user_id");
                    String content = rs.getString("content");
                    String created_at = rs.getString("created_at");

                    // Tạo đối tượng Comment (parent_comment_id sẽ luôn là null ở đây)
                    Comment comment = new Comment(id, bookID, userId, content, null,created_at);
                    commentList.add(comment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return commentList;
    }

//    public void PostCommentForBook(String Content, String ISBN, int user_id) {
//        String query = "INSERT INTO book_comments (book_id, user_id, content, parent_comment_id) VALUES (?, ?, ?, NULL)";
//
//        try (Connection connection = getConnection();
//             PreparedStatement stmt = connection.prepareStatement(query)) {
//
//            // Gán giá trị cho các tham số trong câu truy vấn
//            stmt.setString(1, ISBN); // Gán giá trị ISBN để tìm book_id
//            stmt.setInt(2, user_id); // Gán user_id
//            stmt.setString(3, Content); // Gán nội dung của bình luận (Content)
//
//            // Thực thi truy vấn
//            int rowsInserted = stmt.executeUpdate();
//            if (rowsInserted > 0) {
//                System.out.println("Comment successfully added for ISBN: " + ISBN);
//            } else {
//                System.out.println("Failed to add comment for ISBN: " + ISBN);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    public void PostCommentForBook(String Content, String ISBN, int user_id) {
        // Step 1: Get the book_id corresponding to the ISBN
        String getBookIdQuery = "SELECT ISBN FROM books WHERE ISBN = ?";

        try (Connection connection = getConnection();
             PreparedStatement getBookIdStmt = connection.prepareStatement(getBookIdQuery)) {

            getBookIdStmt.setString(1, ISBN); // Set the ISBN parameter

            ResultSet rs = getBookIdStmt.executeQuery();

            // If the ISBN exists, fetch the book_id
            if (rs.next()) {
                String bookId = rs.getString("ISBN");

                // Step 2: Insert the comment with the book_id
                String insertCommentQuery = "INSERT INTO book_comments (book_id, user_id, content, parent_comment_id) VALUES (?, ?, ?, NULL)";

                try (PreparedStatement stmt = connection.prepareStatement(insertCommentQuery)) {
                    stmt.setString(1, ISBN);  // Set the book_id obtained from the first query
                    stmt.setInt(2, user_id);    // Set the user_id
                    stmt.setString(3, Content); // Set the comment content

                    // Execute the insert query
                    int rowsInserted = stmt.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("Comment successfully added for ISBN: " + ISBN);
                    } else {
                        System.out.println("Failed to add comment for ISBN: " + ISBN);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
                System.out.println("No book found with ISBN: " + ISBN);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public List<Comment> GetChildrenCommentList(int parent_id) {
        List<Comment> commentList = new ArrayList<>();

        // Truy vấn để lấy danh sách các bình luận
        String query = "SELECT id, book_id, user_id, content, created_at FROM book_comments WHERE parent_comment_id = ? ";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            // Thiết lập tham số bookId
            stmt.setInt(1,parent_id);

            // Thực thi truy vấn
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String bookID = rs.getString("book_id");
                    int userId = rs.getInt("user_id");
                    String content = rs.getString("content");
                    String created_at = rs.getString("created_at");

                    // Tạo đối tượng Comment (parent_comment_id sẽ luôn là null ở đây)
                    Comment comment = new Comment(id, bookID, userId, content, null,created_at);
                    commentList.add(comment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return commentList;
    }

    public boolean isBookBorrowed(int user_id, String ISBN) {

        String query = "SELECT COUNT(*) FROM book_borrowed WHERE user_id = ? AND book_id = ? ";

        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {

            // Thiết lập tham số bookId
            stmt.setInt(1, user_id);
            stmt.setString(2, ISBN);

            // Thực thi truy vấn
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void UndoRequest(int user_id, String ISBN) {
        // Define the SQL query to delete the request
        String query = "DELETE FROM request_borrow_book WHERE user_id = ? AND book_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            // Set the parameters for the query
            stmt.setInt(1, user_id);
            stmt.setString(2, ISBN);

            // Execute the update
            int rowsAffected = stmt.executeUpdate();

            // Check if a request was successfully undone
            if (rowsAffected > 0) {
                System.out.println("Request undone successfully for user_id: " + user_id + " and ISBN: " + ISBN);
            } else {
                System.out.println("No matching request found to undo for user_id: " + user_id + " and ISBN: " + ISBN);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("An error occurred while undoing the request.");
        }
    }


}


