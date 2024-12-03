package com.example.demo.book;


import com.example.demo.DesignPattern.Singleton.NotificationManager;
import com.example.demo.student.menuController;
import com.example.demo.student.studentcontroller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import com.example.demo.student.Student;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class BookDetailController extends menuController{

    protected Scene scene;
    protected AnchorPane root;


    @FXML
    private Label titleLabel;
    @FXML
    private ImageView bookImageView;
    @FXML
    private ImageView QRImageView;
    @FXML
    private Label authorLabel;
    @FXML
    private Label publisherLabel;
    @FXML
    private Label yearLabel;
    @FXML
    private Button back_home;
    @FXML
    private Button Borrow_book;
    @FXML
    private Button BookQR;
    @FXML
    private TextField CommentField;
    @FXML
    private AnchorPane CommentView;
    @FXML
    private GridPane FormatComment = new GridPane();
    @FXML
    private  Button UndoRequestButton;
    @FXML
    private Label bookQuantity;

    public static String ISBN;

    private Database BookDatabase = Database.getInstance();

    private BookQR bookQR = new BookQR();

    private Student user = studentcontroller.getStudent();

    private int ParentComment;


    // Phương thức để nhận ISBN từ controller khác
    public void setISBN(String isbn) {
        ISBN = isbn;
        System.out.println("settingISBN is " + ISBN);

        // Gọi phương thức để tải thông tin sách
    }

    public void initialize() {
        System.out.println("ISBN for book detail view now is: " + ISBN);
        loadBookDetails();
        check_Isbn_And_User();
        loadComment();
    }

    // Phương thức để tải chi tiết sách từ database
    private void loadBookDetails() {
        Book book = BookDatabase.getBookByISBN(ISBN);

        if (book != null) {
            titleLabel.setText(book.getTitle());
            authorLabel.setText(book.getAuthor());
            publisherLabel.setText(book.getPublisher());
            yearLabel.setText(book.getPublishYear());
            bookQuantity.setText(String.valueOf(book.getQuantity()));
            loadImage(book.getImage(), bookImageView);
        }
    }


    private void loadImage(String imageUrl, ImageView imageView) {
        try {
            // Tạo URL và kết nối
            URL url = new URL(imageUrl);
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

    @FXML
    private void BackToHome(ActionEvent event) {
        System.out.println("click on back to home button");
    }

    @FXML
    private void ClickBorrowButton(ActionEvent event) {
        boolean isNotified = NotificationManager.getInstance().userNotify(Integer.parseInt(user.getId()),3,ISBN);
        if(!isNotified) {
            System.out.println("#3 unable to notify borrowing book");
        }
        System.out.println("beign in borrow button");
        System.out.println(Borrow_book.getText());
        if (Borrow_book.getText().equals("Borrow")) {
            Student current_user = new Student();
            //String studentID = current_user.getId();
            //studentID = (studentID != null) ? current_user.getId() : "10101";
            String bookISBN = ISBN;
            boolean isSent = BookDatabase.sendBorrowRequest(user.getId(), bookISBN);
            if (isSent) {
                Borrow_book.setText("Requested Borrowing");
                UndoRequestButton.setVisible(true);
                System.out.println("Undo Button is visible");
            }
        }
        else {
            System.out.println("you borrowed this book");
        }
    }

    @FXML
    private void ClickBookQR(ActionEvent event) {
        Book book = BookDatabase.getBookByISBN(ISBN);
        ImageView QRView = bookQR.createQRCodeImageView(book.toString());
        QRImageView.setImage(QRView.getImage());
    }

    public void check_Isbn_And_User() {
        String status_of_request = BookDatabase.RequestStatus(Integer.parseInt(user.getId()), ISBN); // check if you have requested borrowing
        boolean isBorrowed = BookDatabase.isBookBorrowed(Integer.parseInt(user.getId()),ISBN); // check if is book borrowed
        if (status_of_request.equals("pending")) {
            Borrow_book.setText("Requested Borrow");
            UndoRequestButton.setVisible(true);
            System.out.println("Undo Button is visible");
        } else if (isBorrowed) {
            Borrow_book.setText("Borrowed");
        }

    }

    @FXML
    public void clickUndoRequestButton(ActionEvent event) {
        boolean isNotified = NotificationManager.getInstance().userNotify(Integer.parseInt(user.getId()),5,ISBN);
        if(!isNotified) {
            System.out.println("#5 unable to notify borrowing book");
        }
        BookDatabase.UndoRequest(Integer.parseInt(user.getId()),ISBN);
        UndoRequestButton.setVisible(false);
        Borrow_book.setText("Borrow");
    }
    public void loadComment() {
        List<Comment> commentList = BookDatabase.GetCommentList(ISBN);

        ParentComment = commentList.size();
        // CommentField.setText("");

        if (commentList.isEmpty()) {
            System.out.println("No comments found for ISBN: " + ISBN);
            return;
        }


        FormatComment.setHgap(10); // Khoảng cách giữa các cột
        FormatComment.setVgap(10); // Khoảng cách giữa các hàng
        FormatComment.setPadding(new Insets(10)); // Padding xung quanh GridPane


        // Duyệt qua danh sách bình luận và thêm vào GridPane
        int row = 0;
        for (Comment comment : commentList) {
            // Tạo Label hiển thị User ID
            Label userLabel = new Label("User: " + comment.getUserId());
            Label dateLabel = new Label("User: " + comment.getDate());
            FormatComment.add(userLabel, 0, row);
            FormatComment.add(dateLabel, 1, row); // Thêm Label vào cột 0, hàng `row`
            // Cột 0, Hàng `row`

            TextArea commentTextArea = new TextArea(comment.getContent());
            commentTextArea.setWrapText(true); // Tự xuống dòng nếu nội dung dài
            commentTextArea.setEditable(false); // Chỉ đọc
            commentTextArea.setPrefHeight(50); // Chiều cao cố định
            FormatComment.add(commentTextArea, 1, row+1);// Cột 1, Hàng `row`

            row+=2; // Chuyển sang hàng tiếp theo
        }

        CommentView.getChildren().add(FormatComment);

        AnchorPane.setTopAnchor(FormatComment, 80.0);    // Cách cạnh trên 10px
        AnchorPane.setBottomAnchor(FormatComment, 10.0); // Cách cạnh dưới 10px
        AnchorPane.setLeftAnchor(FormatComment, 20.0);   // Cách cạnh trái 10px
        AnchorPane.setRightAnchor(FormatComment, 10.0);  // Cách cạnh phải 10px
    }

    @FXML
    public void PostComment(ActionEvent event) {
        String PostContent = CommentField.getText();
        BookDatabase.PostCommentForBook(PostContent,ISBN, Integer.parseInt(user.getId()));
        FormatComment.getChildren().clear();
        CommentView.getChildren().remove(FormatComment);
        loadComment();
        CommentField.clear();
    }
}

