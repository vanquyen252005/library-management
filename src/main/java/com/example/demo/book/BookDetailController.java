package com.example.demo.book;


import com.example.demo.designpattern.Singleton.NotificationManager;
import com.example.demo.student.MenuController;
import com.example.demo.student.StudentController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import com.example.demo.student.Student;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class BookDetailController extends MenuController {

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
    private Button backHome;
    @FXML
    private Button borrowBook;
    @FXML
    private Button bookQRButton;
    @FXML
    private TextField commentField;
    @FXML
    private AnchorPane commentView;
    @FXML
    private GridPane formatComment = new GridPane();
    @FXML
    private Button undoRequestButton;
    @FXML
    private Label bookQuantity;

    public static String ISBN;

    private Database bookDatabase = Database.getInstance();

    private BookQR bookQR = new BookQR();

    private Student user = StudentController.getStudent();

    private int ParentComment;


    public void setISBN(String isbn) {
        ISBN = isbn;
        System.out.println("settingISBN is " + ISBN);
    }

    public void initialize() {
        System.out.println("ISBN for book detail view now is: " + ISBN);
        loadBookDetails();
        checkIsbnAndUser();
        loadComment();
    }

    private void loadBookDetails() {
        Book book = bookDatabase.getBookByISBN(ISBN);

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
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {

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
    private void clickBorrowButton(ActionEvent event) {
        boolean isNotified = NotificationManager.getInstance().userNotify(Integer.parseInt(user.getId()), 3, ISBN);
        if (!isNotified) {
            System.out.println("#3 unable to notify borrowing book");
        }
        System.out.println("be in in borrow button");
        System.out.println(borrowBook.getText());
        if (borrowBook.getText().equals("Borrow")) {
            String bookISBN = ISBN;
            boolean isSent = bookDatabase.sendBorrowRequest(user.getId(), bookISBN);
            if (isSent) {
                borrowBook.setText("Requested Borrowing");
                undoRequestButton.setVisible(true);
                System.out.println("Undo Button is visible");
            }
        } else {
            System.out.println("you borrowed this book");
        }
    }

    @FXML
    private void clickBookQR(ActionEvent event) {
        Book book = bookDatabase.getBookByISBN(ISBN);
        ImageView QRView = bookQR.createQRCodeImageView(book.toString());
        QRImageView.setImage(QRView.getImage());
    }

    public void checkIsbnAndUser() {
        String status_of_request = bookDatabase.RequestStatus(Integer.parseInt(user.getId()), ISBN); // check if you have requested borrowing
        boolean isBorrowed = bookDatabase.isBookBorrowed(Integer.parseInt(user.getId()), ISBN); // check if is book borrowed
        if (status_of_request.equals("pending")) {
            borrowBook.setText("Requested Borrow");
            undoRequestButton.setVisible(true);
            System.out.println("Undo Button is visible");
        } else if (isBorrowed) {
            borrowBook.setText("Borrowed");
        }

    }

    @FXML
    public void clickUndoRequestButton(ActionEvent event) {
        boolean isNotified = NotificationManager.getInstance().userNotify(Integer.parseInt(user.getId()), 5, ISBN);
        if (!isNotified) {
            System.out.println("#5 unable to notify borrowing book");
        }
        bookDatabase.UndoRequest(Integer.parseInt(user.getId()), ISBN);
        undoRequestButton.setVisible(false);
        borrowBook.setText("Borrow");
    }

    public void loadComment() {
        List<Comment> commentList = bookDatabase.GetCommentList(ISBN);

        ParentComment = commentList.size();

        if (commentList.isEmpty()) {
            System.out.println("No comments found for ISBN: " + ISBN);
            return;
        }


        formatComment.setHgap(10);
        formatComment.setVgap(10);
        formatComment.setPadding(new Insets(10));


        int row = 0;
        for (Comment comment : commentList) {
            // Tạo Label hiển thị User ID
            Label userLabel = new Label("User: " + comment.getUserId());
            Label dateLabel = new Label("User: " + comment.getDate());
            formatComment.add(userLabel, 0, row);
            formatComment.add(dateLabel, 1, row);

            TextArea commentTextArea = new TextArea(comment.getContent());
            commentTextArea.setWrapText(true);
            commentTextArea.setEditable(false);
            commentTextArea.setPrefHeight(50);
            formatComment.add(commentTextArea, 1, row + 1);

            row += 2;
        }

        commentView.getChildren().add(formatComment);

        AnchorPane.setTopAnchor(formatComment, 80.0);
        AnchorPane.setBottomAnchor(formatComment, 10.0);
        AnchorPane.setLeftAnchor(formatComment, 20.0);
        AnchorPane.setRightAnchor(formatComment, 10.0);
    }

    @FXML
    public void postComment(ActionEvent event) {
        String PostContent = commentField.getText();
        bookDatabase.PostCommentForBook(PostContent, ISBN, Integer.parseInt(user.getId()));
        formatComment.getChildren().clear();
        commentView.getChildren().remove(formatComment);
        loadComment();
        commentField.clear();
    }
}

