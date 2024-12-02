//package com.example.demo.book;
//
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//import java.util.concurrent.*;
//
//import java.util.concurrent.Future;
//import java.util.List;
//import java.util.ArrayList;
//import java.sql.*;
//
//
//public class Database {
//    private static Database instance;
//    private Connection connection;
//    private static final String URL = "jdbc:mysql://localhost:3306/bookdb";
//    private static final String USER = "root";
//    private static final String PASSWORD = "123456789";
//
//    private static final ExecutorService executorService = Executors.newCachedThreadPool();
//
//    private Database() {
//        try {
//            connection = DriverManager.getConnection(URL, USER, PASSWORD);
//            System.out.println("Connected to the database successfully!");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static synchronized Database getInstance() {
//        if (instance == null) {
//            instance = new Database();
//        }
//        return instance;
//    }
//
//    public Connection getConnection() {
//        return connection;
//    }
//
//    public String generateISBN() {
//        StringBuilder ISBN = new StringBuilder();
//        Random rand = new Random();
//        for (int i = 0; i < 10; i++) {
//            ISBN.append(rand.nextInt(10));
//        }
//        return ISBN.toString();
//    }
//
//
//
//
//    public boolean addDocument(String title, String author, String publishYear, String publisher, String imgUrl) {
//        String sql = "INSERT INTO books (ISBN, Title, Author, PublishYear, Publisher,`Image-URL-M`) VALUES (?, ?, ?, ?, ?, ?)";
//        String ISBN = generateISBN();
//        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
//            pstmt.setString(1, ISBN);
//            pstmt.setString(2, title);
//            pstmt.setString(3, author);
//            pstmt.setString(4, publishYear);
//            pstmt.setString(5, publisher);
//            pstmt.setString(6, imgUrl);
//            pstmt.executeUpdate();
//            return true;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    public void addDocument(String title, String author, String publishYear, String publisher) {
//        String sql = "INSERT INTO books (ISBN, Title, Author, PublishYear, Publisher) VALUES (?, ?, ?, ?, ?)";
//        String ISBN = generateISBN();
//        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
//            pstmt.setString(1, ISBN);
//            pstmt.setString(2, title);
//            pstmt.setString(3, author);
//            pstmt.setString(4, publishYear);
//            pstmt.setString(5, publisher);
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public List<Book> searchDocumentsDBSingleColumn(String column, String keyword) {
//        List<Book> books = new ArrayList<>();
//        String sql = "SELECT * FROM books WHERE " + column + " LIKE ? ORDER BY rating DESC LIMIT 80";
//
//        try (
//                PreparedStatement pstmt = connection.prepareStatement(sql)) {
//
//            pstmt.setString(1, "%" + keyword + "%");
//
//            System.out.println("Executing query: " + pstmt.toString());
//
//            ResultSet rs = pstmt.executeQuery();
//            while (rs.next()) {
//                books.add(new Book(
//                        rs.getString("ISBN"),
//                        rs.getString("Title"),
//                        rs.getString("Author"),
//                        rs.getString("Publisher"),
//                        rs.getString("PublishYear"),
//                        rs.getString("Image-URL-M")
//                ));
//            }
//        } catch (SQLException e) {
//            System.out.println("SQL exception: " + e.getMessage());
//            e.printStackTrace();
//        }
//
//        return books;
//    }
//
//    private void loadImage(String imageUrl, ImageView imageView) {
//        try {
//            // Tạo URL và kết nối
//            java.net.URL url = new URL(imageUrl);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setDoInput(true);
//            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36");
//            connection.connect();
//
//            int responseCode = connection.getResponseCode();
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//                // Lấy hình ảnh và gán cho ImageView
//                InputStream input = connection.getInputStream();
//                Image image = new Image(input);
//                imageView.setImage(image);
//            } else {
//                System.err.println("Error loading image: Server returned HTTP response code: " + responseCode + " for URL: " + imageUrl);
//            }
//        } catch (Exception e) {
//            System.err.println("Error loading image: " + e.getMessage());
//        }
//    }
//
//
//
//    public List<Book> searchDocuments(String keyword) {
//        List<Book> books = new ArrayList<>();
//        String sql;
//
//        if (keyword == null || keyword.trim().isEmpty()) {
//            sql = "SELECT * FROM books ORDER BY rating DESC LIMIT 80";
//        } else {
//            sql = "SELECT * FROM books WHERE Title LIKE ? OR Author LIKE ? ORDER BY rating DESC LIMIT 80";
//        }
//
//        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
//            if (keyword != null && !keyword.trim().isEmpty()) {
//                pstmt.setString(1, "%" + keyword + "%");
//                pstmt.setString(2, "%" + keyword + "%");
//            }
//
//            System.out.println("Executing query: " + pstmt);
//            ResultSet rs = pstmt.executeQuery();
//            while (rs.next()) {
//                books.add(new Book(
//                        rs.getString("ISBN"),
//                        rs.getString("Title"),
//                        rs.getString("Author"),
//                        rs.getString("Publisher"),
//                        rs.getString("PublishYear"),
//                        rs.getString("Image-URL-M")
//                ));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return books;
//    }
//
//
//    public List<Book> searchDocumentsDB(String keyword) {
//        List<Book> books = new ArrayList<>();
//        String sql;
//
//        // If keyword is empty, fetch top-rated books
//        if (keyword == null || keyword.trim().isEmpty()) {
//            sql = "SELECT * FROM books ORDER BY rating DESC LIMIT 1000";
//        } else {
//            // If keyword is provided, search by Title or Author, then order by rating
//            sql = "SELECT * FROM books WHERE Title LIKE ? OR Author LIKE ? ORDER BY rating DESC LIMIT 1000";
//        }
//
//        try (
//                PreparedStatement pstmt = connection.prepareStatement(sql)) {
//
//            // Set parameters only if keyword is not empty
//            if (keyword != null && !keyword.trim().isEmpty()) {
//                pstmt.setString(1, "%" + keyword + "%");
//                pstmt.setString(2, "%" + keyword + "%");
//            }
//
//            System.out.println("Executing query: " + pstmt.toString());
//
//            ResultSet rs = pstmt.executeQuery();
//            while (rs.next()) {
//                books.add(new Book(
//                        rs.getString("ISBN"),
//                        rs.getString("Title"),
//                        rs.getString("Author"),
//                        rs.getString("Publisher"),
//                        rs.getString("PublishYear"),
//                        rs.getString("Image-URL-M")
//                ));
//
//            }
//            System.out.println("Finishing getting book list");
//
//        } catch (SQLException e) {
//            System.out.println("SQL exception: " + e.getMessage());
//            e.printStackTrace();
//        } catch (Exception e) {
//            System.out.println("Unexpected exception: " + e.getMessage());
//            e.printStackTrace();
//        }
//
//        System.out.println("Returning book list with size: " + books.size());
//        return books;
//    }
//    public List<Book> searchDocumentsAPI(String keyword) {
//        ArrayList<Book> books = new ArrayList<>();
//        System.out.println("No data returned from database so trying to testing api source.");
//        List<Book> sample = ApiService.searchBooks(keyword);
//        for(Book b : sample) books.add(b);
//        if(books.size() == 0) {
//            System.out.println("No data returned from API");
//        }
//        return books;
//    }
//    public boolean editDocument(String ISBN, String newTitle, String newAuthor, String newPublisher, String newPublishYear, String imgUrl) {
//        String selectSql = "SELECT title, author, publisher, publishYear, `Image-URL-M` FROM books WHERE ISBN = ?";
//        String updateSql = "UPDATE books SET title = ?, author = ?, publisher = ?, publishYear = ?, `Image-URL-M` = ?  WHERE ISBN = ?";
//        try (
//                PreparedStatement selectStmt = connection.prepareStatement(selectSql);
//                PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
//
//            // Truy vấn dữ liệu hiện tại
//            selectStmt.setString(1, ISBN);
//            ResultSet rs = selectStmt.executeQuery();
//
//            if (rs.next()) {
//                // Kiểm tra và giữ giá trị cũ nếu trường mới rỗng
//                String currentTitle = rs.getString("title");
//                String currentAuthor = rs.getString("author");
//                String currentPublisher = rs.getString("publisher");
//                String currentPublishYear = rs.getString("publishYear");
//
//                if (newTitle == null || newTitle.isEmpty()) {
//                    newTitle = currentTitle;
//                }
//                if (newAuthor == null || newAuthor.isEmpty()) {
//                    newAuthor = currentAuthor;
//                }
//                if (newPublisher == null || newPublisher.isEmpty()) {
//                    newPublisher = currentPublisher;
//                }
//                if (newPublishYear == null || newPublishYear.isEmpty()) {
//                    newPublishYear = currentPublishYear;
//                }
//
//                // Thực hiện cập nhật
//                updateStmt.setString(1, newTitle);
//                updateStmt.setString(2, newAuthor);
//                updateStmt.setString(3, newPublisher);
//                updateStmt.setString(4, newPublishYear);
//                updateStmt.setString(5, imgUrl);
//                updateStmt.setString(6, ISBN);
//
//                updateStmt.executeUpdate();
//                return true;
//            } else {
//                System.out.println("No record found with the provided ISBN.");
//            }
//
//        } catch (SQLException e) {
//            System.out.println("SQL exception: " + e.getMessage());
//            e.printStackTrace();
//        }
//        return false;
//    }
//    public void editDocument(String ISBN, String newTitle, String newAuthor, String newPublisher, String newPublishYear) {
//        String selectSql = "SELECT title, author, publisher, publishYear FROM books WHERE ISBN = ?";
//        String updateSql = "UPDATE books SET title = ?, author = ?, publisher = ?, publishYear = ? WHERE ISBN = ?";
//        try (PreparedStatement selectStmt = connection.prepareStatement(selectSql);
//             PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
//
//            selectStmt.setString(1, ISBN);
//            ResultSet rs = selectStmt.executeQuery();
//
//            if (rs.next()) {
//                newTitle = (newTitle == null || newTitle.isEmpty()) ? rs.getString("title") : newTitle;
//                newAuthor = (newAuthor == null || newAuthor.isEmpty()) ? rs.getString("author") : newAuthor;
//                newPublisher = (newPublisher == null || newPublisher.isEmpty()) ? rs.getString("publisher") : newPublisher;
//                newPublishYear = (newPublishYear == null || newPublishYear.isEmpty()) ? rs.getString("publishYear") : newPublishYear;
//
//                updateStmt.setString(1, newTitle);
//                updateStmt.setString(2, newAuthor);
//                updateStmt.setString(3, newPublisher);
//                updateStmt.setString(4, newPublishYear);
//                updateStmt.setString(5, ISBN);
//                updateStmt.executeUpdate();
//            } else {
//                System.out.println("No record found with the provided ISBN.");
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//    public void deleteDocument(String ISBN) {
//        String sql = "DELETE FROM books WHERE ISBN = ?";
//        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
//            pstmt.setString(1, ISBN);
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public Book getBookByISBN(String isbn) {
//        Book book = null;
//        String sql = "SELECT * FROM books WHERE ISBN = ?";
//
//        try ( PreparedStatement pstmt = connection.prepareStatement(sql)) {
//
//            pstmt.setString(1, isbn);
//
//            ResultSet rs = pstmt.executeQuery();
//
//            if (rs.next()) {
//                book = new Book(
//                        rs.getString("ISBN"),
//                        rs.getString("Title"),
//                        rs.getString("Author"),
//                        rs.getString("Publisher"),
//                        rs.getString("PublishYear"),
//                        rs.getString("Image-URL-M")
//                );
//            } else {
//                System.out.println("No book found with ISBN: " + isbn);
//            }
//        } catch (SQLException e) {
//            System.out.println("SQL exception: " + e.getMessage());
//            e.printStackTrace();
//        } catch (Exception e) {
//            System.out.println("Unexpected exception: " + e.getMessage());
//            e.printStackTrace();
//        }
//
//        return book;
//    }
//
//    public boolean sendBorrowRequest(String userID, String ISBN) {
//        String query = "INSERT INTO request_borrow_book (book_id, user_id, status) VALUES (?, ?, 'pending')";
//
//        // Tạo kết nối cơ sở dữ liệu
//        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//
//            preparedStatement.setString(1, ISBN);  // Gán giá trị cho book_id
//            preparedStatement.setInt(2, Integer.parseInt(userID));  // Gán giá trị cho user_id
//
//            int rowsAffected = preparedStatement.executeUpdate();
//
//            if (rowsAffected > 0) {
//                System.out.println("Borrow request has been sent successfully.");
//                return true;
//            } else {
//                System.out.println("Failed to send borrow request.");
//                return false;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    public String RequestStatus(int userID, String ISBN) {
//        String status = "";
//        String query = "SELECT status FROM request_borrow_book WHERE user_id = ? AND book_id = ? ORDER BY request_date DESC LIMIT 1";
//
//        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//
//            preparedStatement.setInt(1, userID); // Gán user_id
//            preparedStatement.setString(2, ISBN); // Gán book_id (ISBN)
//
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            if (resultSet.next()) {
//                status = resultSet.getString("status"); // Lấy giá trị status
//            } else {
//                System.out.println("No borrow request found for this user and book:" + userID + ", " + ISBN);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return status;
//    }
//
//
//    public List<Comment> GetCommentList(String ISBN) {
//        List<Comment> commentList = new ArrayList<>();
//
//        // Truy vấn để lấy danh sách các bình luận
//        String query = "SELECT id, book_id, user_id, content, created_at FROM book_comments WHERE book_id = ? AND parent_comment_id is null ";
//
//        try (PreparedStatement stmt = connection.prepareStatement(query)) {
//
//            // Thiết lập tham số bookId
//            stmt.setString(1,ISBN);
//
//            // Thực thi truy vấn
//            try (ResultSet rs = stmt.executeQuery()) {
//                while (rs.next()) {
//                    int id = rs.getInt("id");
//                    String bookID = rs.getString("book_id");
//                    int userId = rs.getInt("user_id");
//                    String content = rs.getString("content");
//                    String created_at = rs.getString("created_at");
//
//                    // Tạo đối tượng Comment (parent_comment_id sẽ luôn là null ở đây)
//                    Comment comment = new Comment(id, bookID, userId, content, null,created_at);
//                    commentList.add(comment);
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return commentList;
//    }
//
//    public void PostCommentForBook(String Content, String ISBN, int user_id) {
//        String query = "INSERT INTO book_comments (book_id, user_id, content, parent_comment_id) VALUES (?, ?, ?, NULL)";
//
//        try (PreparedStatement stmt = connection.prepareStatement(query)) {
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
//
//    public List<Comment> GetChildrenCommentList(int parent_id) {
//        List<Comment> commentList = new ArrayList<>();
//
//        // Truy vấn để lấy danh sách các bình luận
//        String query = "SELECT id, book_id, user_id, content, created_at FROM book_comments WHERE parent_comment_id = ? ";
//
//        try (PreparedStatement stmt = connection.prepareStatement(query)) {
//
//            // Thiết lập tham số bookId
//            stmt.setInt(1,parent_id);
//
//            // Thực thi truy vấn
//            try (ResultSet rs = stmt.executeQuery()) {
//                while (rs.next()) {
//                    int id = rs.getInt("id");
//                    String bookID = rs.getString("book_id");
//                    int userId = rs.getInt("user_id");
//                    String content = rs.getString("content");
//                    String created_at = rs.getString("created_at");
//
//                    // Tạo đối tượng Comment (parent_comment_id sẽ luôn là null ở đây)
//                    Comment comment = new Comment(id, bookID, userId, content, null,created_at);
//                    commentList.add(comment);
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return commentList;
//    }
//
//    public boolean isBookBorrowed(int user_id, String ISBN) {
//
//        String query = "SELECT COUNT(*) FROM book_borrowed WHERE user_id = ? AND book_id = ? ";
//
//        try (PreparedStatement stmt = connection.prepareStatement(query)) {
//
//            // Thiết lập tham số bookId
//            stmt.setInt(1, user_id);
//            stmt.setString(2, ISBN);
//
//            // Thực thi truy vấn
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                return rs.getInt(1) > 0;
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return false;
//    }
//
//    public void UndoRequest(int user_id, String ISBN) {
//        // Define the SQL query to delete the request
//        String query = "DELETE FROM request_borrow_book WHERE user_id = ? AND book_id = ?";
//
//        try (PreparedStatement stmt = connection.prepareStatement(query)) {
//
//            // Set the parameters for the query
//            stmt.setInt(1, user_id);
//            stmt.setString(2, ISBN);
//
//            // Execute the update
//            int rowsAffected = stmt.executeUpdate();
//
//            // Check if a request was successfully undone
//            if (rowsAffected > 0) {
//                System.out.println("Request undone successfully for user_id: " + user_id + " and ISBN: " + ISBN);
//            } else {
//                System.out.println("No matching request found to undo for user_id: " + user_id + " and ISBN: " + ISBN);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.err.println("An error occurred while undoing the request.");
//        }
//    }
//
//
//
//
//}
//


package com.example.demo.book;

import com.example.demo.DesignPattern.Singleton.Notification;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

import java.util.concurrent.Future;
import java.util.List;
import java.util.ArrayList;
import java.sql.*;


public class Database {
    private static Database instance;
    private Connection connection;
    private static final String URL = "jdbc:mysql://localhost:3306/bookdbb";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    private Database() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
//            System.out.println("Connected to the database successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database();
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

    public  List<Book> queryBooks(int pageSize, int offset, String text) {
        List<Book> books = new ArrayList<>(); // Danh sách để lưu trữ kết quả

        // Kết nối đến cơ sở dữ liệu và thực hiện truy vấn
        try (// Kết nối DB
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM books where ISBN like '%" + text + "%' LIMIT ? OFFSET ?"
             )) {
            // Đặt tham số cho truy vấn
            statement.setInt(1, pageSize); // Giới hạn số lượng bản ghi
            statement.setInt(2, offset);  // Bỏ qua số lượng bản ghi

            ResultSet rs = statement.executeQuery();

            // Duyệt qua kết quả truy vấn và tạo đối tượng Book
            while (rs.next()) {
                Book book = new Book(
                        rs.getString("ISBN"),
                        rs.getString("Title"),
                        rs.getString("Author"),
                        rs.getString("Publisher"),
                        rs.getString("PublishYear"),
                        rs.getString("Image-URL-M")
                );
                books.add(book); // Thêm sách vào danh sách
            }
        } catch (Exception e) {
            e.printStackTrace(); // Xử lý lỗi kết nối hoặc truy vấn
        }
        try (// Kết nối DB
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM books where Title like '%" + text + "%' LIMIT ? OFFSET ?"
             )) {
            // Đặt tham số cho truy vấn
            statement.setInt(1, pageSize); // Giới hạn số lượng bản ghi
            statement.setInt(2, offset);  // Bỏ qua số lượng bản ghi

            ResultSet rs = statement.executeQuery();

            // Duyệt qua kết quả truy vấn và tạo đối tượng Book
            while (rs.next()) {
                Book book = new Book(
                        rs.getString("ISBN"),
                        rs.getString("Title"),
                        rs.getString("Author"),
                        rs.getString("Publisher"),
                        rs.getString("PublishYear"),
                        rs.getString("Image-URL-M")
                );
                books.add(book); // Thêm sách vào danh sách
            }
        } catch (Exception e) {
            e.printStackTrace(); // Xử lý lỗi kết nối hoặc truy vấn
        }
        try (// Kết nối DB
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM books where Author like '%" + text + "%' LIMIT ? OFFSET ?"
             )) {
            // Đặt tham số cho truy vấn
            statement.setInt(1, pageSize); // Giới hạn số lượng bản ghi
            statement.setInt(2, offset);  // Bỏ qua số lượng bản ghi

            ResultSet rs = statement.executeQuery();

            // Duyệt qua kết quả truy vấn và tạo đối tượng Book
            while (rs.next()) {
                Book book = new Book(
                        rs.getString("ISBN"),
                        rs.getString("Title"),
                        rs.getString("Author"),
                        rs.getString("Publisher"),
                        rs.getString("PublishYear"),
                        rs.getString("Image-URL-M")
                );
                books.add(book); // Thêm sách vào danh sách
            }
        } catch (Exception e) {
            e.printStackTrace(); // Xử lý lỗi kết nối hoặc truy vấn
        }
        try (// Kết nối DB
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM books where Publisher like '%" + text + "%' LIMIT ? OFFSET ?"
             )) {
            // Đặt tham số cho truy vấn
            statement.setInt(1, pageSize); // Giới hạn số lượng bản ghi
            statement.setInt(2, offset);  // Bỏ qua số lượng bản ghi

            ResultSet rs = statement.executeQuery();

            // Duyệt qua kết quả truy vấn và tạo đối tượng Book
            while (rs.next()) {
                Book book = new Book(
                        rs.getString("ISBN"),
                        rs.getString("Title"),
                        rs.getString("Author"),
                        rs.getString("Publisher"),
                        rs.getString("PublishYear"),
                        rs.getString("Image-URL-M")
                );
                books.add(book); // Thêm sách vào danh sách
            }
        } catch (Exception e) {
            e.printStackTrace(); // Xử lý lỗi kết nối hoặc truy vấn
        }
        try (// Kết nối DB
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM books where PublishYear like '%" + text + "%' LIMIT ? OFFSET ?"
             )) {
            // Đặt tham số cho truy vấn
            statement.setInt(1, pageSize); // Giới hạn số lượng bản ghi
            statement.setInt(2, offset);  // Bỏ qua số lượng bản ghi

            ResultSet rs = statement.executeQuery();

            // Duyệt qua kết quả truy vấn và tạo đối tượng Book
            while (rs.next()) {
                Book book = new Book(
                        rs.getString("ISBN"),
                        rs.getString("Title"),
                        rs.getString("Author"),
                        rs.getString("Publisher"),
                        rs.getString("PublishYear"),
                        rs.getString("Image-URL-M")
                );
                books.add(book); // Thêm sách vào danh sách
            }
        } catch (Exception e) {
            e.printStackTrace(); // Xử lý lỗi kết nối hoặc truy vấn
        }
        return books; // Trả về danh sách sách
    }




    public boolean addDocument(String title, String author, String publishYear, String publisher, String imgUrl) {
        String sql = "INSERT INTO books (ISBN, Title, Author, PublishYear, Publisher,`Image-URL-M`) VALUES (?, ?, ?, ?, ?, ?)";
        String ISBN = generateISBN();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, ISBN);
            pstmt.setString(2, title);
            pstmt.setString(3, author);
            pstmt.setString(4, publishYear);
            pstmt.setString(5, publisher);
            pstmt.setString(6, imgUrl);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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

    public List<Book> searchDocumentsDBSingleColumn(String column, String keyword) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE " + column + " LIKE ? ORDER BY rating DESC LIMIT 80";

        try (
                PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, "%" + keyword + "%");

            System.out.println("Executing query: " + pstmt.toString());

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
            System.out.println("SQL exception: " + e.getMessage());
            e.printStackTrace();
        }

        return books;
    }

    private void loadImage(String imageUrl, ImageView imageView) {
        try {
            // Tạo URL và kết nối
            java.net.URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Lấy hình ảnh và gán cho ImageView
                InputStream input = connection.getInputStream();
                Image image = new Image(input);
                imageView.setImage(image);
            } else {
                System.err.println("Error loading image: Server returned HTTP response code: " + responseCode + " for URL: " + imageUrl);
            }
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
        }
    }



    public List<Book> searchDocuments(String keyword) {
        List<Book> books = new ArrayList<>();
        String sql;

        if (keyword == null || keyword.trim().isEmpty()) {
            sql = "SELECT * FROM books ORDER BY rating DESC LIMIT 300";
        } else {
            sql = "SELECT * FROM books WHERE Title LIKE ? OR Author LIKE ? ORDER BY rating DESC LIMIT 300";
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


    public List<Book> searchDocumentsDB(String keyword) {
        List<Book> books = new ArrayList<>();
        String sql;

        // If keyword is empty, fetch top-rated books
        if (keyword == null || keyword.trim().isEmpty()) {
            sql = "SELECT * FROM books ORDER BY rating DESC LIMIT 1000";
        } else {
            // If keyword is provided, search by Title or Author, then order by rating
            sql = "SELECT * FROM books WHERE Title LIKE ? OR Author LIKE ? ORDER BY rating DESC LIMIT 1000";
        }

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            // Set parameters only if keyword is not empty
            if (keyword != null && !keyword.trim().isEmpty()) {
                pstmt.setString(1, "%" + keyword + "%");
                pstmt.setString(2, "%" + keyword + "%");
            }

            System.out.println("Executing query: " + pstmt.toString());

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
    public List<Book> searchDocumentsAPI(String keyword) {
        ArrayList<Book> books = new ArrayList<>();
        System.out.println("No data returned from database so trying to testing api source.");
        List<Book> sample = ApiService.searchBooks(keyword);
        for(Book b : sample) books.add(b);
        if(books.size() == 0) {
            System.out.println("No data returned from API");
        }
        return books;
    }
    public boolean editDocument(String ISBN, String newTitle, String newAuthor, String newPublisher, String newPublishYear, String imgUrl) {
        String selectSql = "SELECT title, author, publisher, publishYear, `Image-URL-M` FROM books WHERE ISBN = ?";
        String updateSql = "UPDATE books SET title = ?, author = ?, publisher = ?, publishYear = ?, `Image-URL-M` = ?  WHERE ISBN = ?";
        try (
                PreparedStatement selectStmt = connection.prepareStatement(selectSql);
                PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {

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
                updateStmt.setString(5, imgUrl);
                updateStmt.setString(6, ISBN);

                updateStmt.executeUpdate();
                return true;
            } else {
                System.out.println("No record found with the provided ISBN.");
            }

        } catch (SQLException e) {
            System.out.println("SQL exception: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
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

    public Book getBookByISBN(String isbn) {
        Book book = null;
        String sql = "SELECT * FROM books WHERE ISBN = ?";

        try ( PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, isbn);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                book = new Book(
                        rs.getString("ISBN"),
                        rs.getString("Title"),
                        rs.getString("Author"),
                        rs.getString("Publisher"),
                        rs.getString("PublishYear"),
                        rs.getString("Image-URL-M"),
                        rs.getInt("quantity")
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
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

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

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

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


    public List<Comment> GetCommentList(String ISBN) {
        List<Comment> commentList = new ArrayList<>();

        // Truy vấn để lấy danh sách các bình luận
        String query = "SELECT id, book_id, user_id, content, created_at FROM book_comments WHERE book_id = ? AND parent_comment_id is null ";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

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

    public void PostCommentForBook(String Content, String ISBN, int user_id) {
        String query = "INSERT INTO book_comments (book_id, user_id, content, parent_comment_id) VALUES (?, ?, ?, NULL)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            // Gán giá trị cho các tham số trong câu truy vấn
            stmt.setString(1, ISBN); // Gán giá trị ISBN để tìm book_id
            stmt.setInt(2, user_id); // Gán user_id
            stmt.setString(3, Content); // Gán nội dung của bình luận (Content)

            // Thực thi truy vấn
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Comment successfully added for ISBN: " + ISBN);
            } else {
                System.out.println("Failed to add comment for ISBN: " + ISBN);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Comment> GetChildrenCommentList(int parent_id) {
        List<Comment> commentList = new ArrayList<>();

        // Truy vấn để lấy danh sách các bình luận
        String query = "SELECT id, book_id, user_id, content, created_at FROM book_comments WHERE parent_comment_id = ? ";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

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

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

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

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

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

    public List<Notification> getNotificationList() {
        List<Notification> result = new ArrayList<>();
        String sql = "SELECT * FROM notification";

        try ( PreparedStatement pstmt = connection.prepareStatement(sql)) {


            ResultSet rs = pstmt.executeQuery();

            int res = 0;
            while (rs.next()) {
                res++;
                result.add(new Notification(
                        rs.getString("content"),
                        rs.getInt("user_id"),
                        rs.getString("notify_date"),
                        rs.getInt("id"),
                        rs.getInt("admin_id")
                ));
            }
            System.out.println("total notify in database is " + res);
        } catch (SQLException e) {
            System.out.println("SQL exception: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Unexpected exception: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public boolean userNotify(int user_id, int type, String ISBN) {
        //1 change profile info
        //2 change password
        //3 request borrow
        //4 request return
        //5 undo borrow

        String content = "";

        if(ISBN.equals("")) {
            if(type == 1) {
                content = "You have made change to your profile information, please have a check";
            } else if(type == 2) {
                content = "Your password have been changed lately";
            }
        } else if(type == 3) {
            content = "you requested borrowing book with id: " + ISBN;
        } else if(type == 4) {
            content = "you requested returning book with id: " + ISBN;
        }
        else if(type == 5) {
            content = "you just undo borrowing book with id: " + ISBN;
        }

        int admin_id = 519;

        String query = "INSERT INTO notification (admin_id, user_id, content) VALUES (?, ?, ?)";

        // Tạo kết nối cơ sở dữ liệu
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, admin_id);
            preparedStatement.setInt(2, user_id);
            preparedStatement.setString(3, content);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Creating notification has been sent successfully.");
                return true;
            } else {
                System.out.println("Failed to create notification.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void clearUserNotify(int user_id) {
        String query = "DELETE FROM notification WHERE user_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            // Set the parameters for the query
            stmt.setInt(1, user_id);

            // Execute the update
            int rowsAffected = stmt.executeUpdate();

            // Check if a request was successfully undone
            if (rowsAffected > 0) {
                System.out.println("Request undone successfully for user_id: " + user_id);
            } else {
                System.out.println("No matching request found to undo for user_id: " + user_id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("An error occurred while undoing the request.");
        }
    }

    public void deleteComment(int comment_id) {
        String sql = "DELETE FROM book_comments WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, comment_id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}

