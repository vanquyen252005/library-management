package com.example.demo.admin;

import com.example.demo.book.Book;
import com.example.demo.book.BookQR;
import com.example.demo.book.Comment;
import com.example.demo.book.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Controller for displaying the details of a book.
 * Manages the book's information, comments, and QR code.
 */

public class DetailBookController extends MenuController {
    @FXML
    public Pane detailStudent;
    @FXML
    public TableView borrowingBook;
    @FXML
    public TableView requestBook1;
    @FXML
    public TreeView<String> miniBar;
    @FXML
    public ImageView imageViewQR;
    public TextField commentField;
    public AnchorPane commentView;
    public Label yearLabel;
    public Label publisherLabel;
    public Label authorLabel;
    public Label titleLabel;
    @FXML
    private GridPane formatComment = new GridPane();
    @FXML
    private Book curBook = ManageBookController.onClickBook;
    @FXML
    protected ImageView bookImageView;
    private int ParentComment;


    /**
     * Initializes the book details and loads the book image and basic information.
     */

    @Override
    @FXML
    public void initialize() {
        super.initialize();
        {
            homeButton.getStyleClass().remove("selected");
            manageStudent.getStyleClass().remove("select");
            manageBook.getStyleClass().remove("selected");
            handleRequest.getStyleClass().remove("selected");

            homeButton.getStyleClass().remove("pre");
            manageStudent.getStyleClass().remove("pre");
            manageBook.getStyleClass().remove("pre");
            handleRequest.getStyleClass().remove("pre");

            homeButton.getStyleClass().remove("after");
            manageStudent.getStyleClass().remove("after");
            manageBook.getStyleClass().remove("after");
            handleRequest.getStyleClass().remove("after");

            manageStudent.getStyleClass().add("pre");
            manageBook.getStyleClass().add("selected");
            handleRequest.getStyleClass().add("after");
        }

        bookImageView.setFitWidth(200); 
        bookImageView.setFitHeight(300);
        bookImageView.setPreserveRatio(true);
        bookImageView.setImage(curBook.loadImage().getImage());
        titleLabel.setText(curBook.getTitle());
        authorLabel.setText(curBook.getAuthor());
        publisherLabel.setText(curBook.getPublisher());
        yearLabel.setText(curBook.getPublishYear());

    }

    /**
     * Handles the QR code display when the QR button is clicked.
     * Generates and displays the QR code for the current book.
     */

    public void clickBookQR(ActionEvent event) {
        ImageView QRView = BookQR.createQRCodeImageView(curBook.toString());
        imageViewQR.setImage(QRView.getImage());
    }

    /**
     * Loads the comments for the current book, sorts them, and displays them in the UI.
     */

    public void loadComment() {

        List<Comment> commentList = curBook.getCommentList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Collections.sort(commentList, new Comparator<Comment>() {
            @Override
            public int compare(Comment o1, Comment o2) {
                LocalDateTime date1 = LocalDateTime.parse(o1.getDate(), formatter);
                LocalDateTime date2 = LocalDateTime.parse(o2.getDate(), formatter);
                return date2.compareTo(date1);
            }
        });


        ParentComment = commentList.size();
        // CommentField.setText("");

        if (commentList.isEmpty()) {
            System.out.println("No comments found for ISBN: " + curBook.getISBN());
            return;
        }


        formatComment.setHgap(10); // Khoảng cách giữa các cột
        formatComment.setVgap(10); // Khoảng cách giữa các hàng
        formatComment.setPadding(new Insets(10)); // Padding xung quanh GridPane


        // Duyệt qua danh sách bình luận và thêm vào GridPane
        int row = 0;
        for (Comment comment : commentList) {
            Button deleteCommentButton = new Button("Delete comment");
            Label userLabel = new Label("Admin" + comment.getUserId());
            Label dateLabel = new Label("Time: " + comment.getDate());
            formatComment.add(userLabel, 0, row);
            formatComment.add(dateLabel, 1, row);
            formatComment.add(deleteCommentButton, 2, row + 1);


            TextArea commentTextArea = new TextArea(comment.getContent());
            commentTextArea.setWrapText(true);
            commentTextArea.setEditable(false);
            commentTextArea.setPrefHeight(50);
            formatComment.add(commentTextArea, 1, row + 1);


            deleteCommentButton.setOnAction(event -> {
                clickOnDeleteComment(event, comment);
            });

            row += 2; // Chuyển sang hàng tiếp theo
        }

        commentView.getChildren().add(formatComment);

        AnchorPane.setTopAnchor(formatComment, 80.0);    // Cách cạnh trên 10px
        AnchorPane.setBottomAnchor(formatComment, 10.0); // Cách cạnh dưới 10px
        AnchorPane.setLeftAnchor(formatComment, 20.0);   // Cách cạnh trái 10px
        AnchorPane.setRightAnchor(formatComment, 10.0);  // Cách cạnh phải 10px

    }

    /**
     * Deletes a comment when the delete button is clicked.
     *
     * @param event   The action event triggered by the delete button.
     * @param comment The comment to be deleted.
     */
    @FXML
    public void clickOnDeleteComment(ActionEvent event, Comment comment) {
        System.out.println("you click delete comment");
        Database.getInstance().deleteComment(comment.getId());
        formatComment.getChildren().clear();
        commentView.getChildren().remove(formatComment);
        loadComment();
    }

    /**
     * Posts a new comment to the current book.
     *
     * @param event The action event triggered by the post comment button.
     */

    @FXML
    public void postComment(ActionEvent event) {
        System.out.println(user.getId());
        String PostContent = commentField.getText();
        System.out.println("user id comment is " + user.getId());
        Database.getInstance().PostCommentForBook(PostContent, curBook.getISBN(), Integer.parseInt(user.getId()));

        formatComment.getChildren().clear();
        commentView.getChildren().remove(formatComment);
        loadComment();
        commentField.clear();

    }


}
