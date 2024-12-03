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
import javafx.scene.text.Text;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class detailbookcontroller extends menucontroller {
    @FXML
    public Pane detailStudent;
    @FXML
    public TableView borrowingBook;
    @FXML
    public TableView requestBook1;
    @FXML
    public Text name;
    @FXML
    public Text id;
    @FXML
    public Text Class;
    @FXML
    public Text phone;
    @FXML
    public TreeView<String> miniBar;
    @FXML
    public ImageView QRImageView;
    public TextField CommentField;
    public AnchorPane CommentView;
    @FXML
    private GridPane FormatComment = new GridPane();
    @FXML
    private Book curBook = managebookcontroller.onClickBook;
    @FXML
    protected ImageView bookImageView;
    private int ParentComment;

    @Override
    @FXML
    public void initialize() {
        super.initialize();
        home.getStyleClass().remove("selected");
        manageStudent.getStyleClass().remove("select");
        manageBook.getStyleClass().remove("selected");
        handleRequest.getStyleClass().remove("selected");

        home.getStyleClass().remove("pre");
        manageStudent.getStyleClass().remove("pre");
        manageBook.getStyleClass().remove("pre");
        handleRequest.getStyleClass().remove("pre");

        home.getStyleClass().remove("after");
        manageStudent.getStyleClass().remove("after");
        manageBook.getStyleClass().remove("after");
        handleRequest.getStyleClass().remove("after");

        manageStudent.getStyleClass().add("pre");
        manageBook.getStyleClass().add("selected");
        handleRequest.getStyleClass().add("after");

        bookImageView.setFitWidth(200); // Chiều rộng (px)
        bookImageView.setFitHeight(300);
        bookImageView.setPreserveRatio(true);
        bookImageView.setImage(curBook.loadImage().getImage());

        loadComment();
    }

    public void ClickBookQR(ActionEvent event) {
        ImageView QRView = BookQR.createQRCodeImageView(curBook.toString());
        QRImageView.setImage(QRView.getImage());
//        System.out.println("hehe1");
    }


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


        FormatComment.setHgap(10); // Khoảng cách giữa các cột
        FormatComment.setVgap(10); // Khoảng cách giữa các hàng
        FormatComment.setPadding(new Insets(10)); // Padding xung quanh GridPane


        // Duyệt qua danh sách bình luận và thêm vào GridPane
        int row = 0;
        for (Comment comment : commentList) {
            Button deleteCommentButton = new Button("Xóa bình luận");
            Label userLabel = new Label("Admin" + comment.getUserId());
            Label dateLabel = new Label("Time: " + comment.getDate());
            FormatComment.add(userLabel, 0, row);
            FormatComment.add(dateLabel, 1, row);
            FormatComment.add(deleteCommentButton,2, row+1);


            TextArea commentTextArea = new TextArea(comment.getContent());
            commentTextArea.setWrapText(true);
            commentTextArea.setEditable(false);
            commentTextArea.setPrefHeight(50);
            FormatComment.add(commentTextArea, 1, row+1);


            deleteCommentButton.setOnAction(event -> {
                clickOndeleteComment(event,comment);
            });

            row+=2; // Chuyển sang hàng tiếp theo
        }

        CommentView.getChildren().add(FormatComment);

        AnchorPane.setTopAnchor(FormatComment, 80.0);    // Cách cạnh trên 10px
        AnchorPane.setBottomAnchor(FormatComment, 10.0); // Cách cạnh dưới 10px
        AnchorPane.setLeftAnchor(FormatComment, 20.0);   // Cách cạnh trái 10px
        AnchorPane.setRightAnchor(FormatComment, 10.0);  // Cách cạnh phải 10px

    }

    @FXML
    public void clickOndeleteComment(ActionEvent event, Comment comment) {
        System.out.println("you click delete comment");
        Database.getInstance().deleteComment(comment.getId());
        FormatComment.getChildren().clear();
        CommentView.getChildren().remove(FormatComment);
        loadComment();
    }

    @FXML
    public void PostComment(ActionEvent event) {
        System.out.println(user.getId());
        String PostContent = CommentField.getText();
        Database.getInstance().PostCommentForBook(PostContent,curBook.getISBN(),Integer.parseInt(admincontroller.user.getId()) );

        //curBook.getNewComment(PostContent,Integer.parseInt(user.getId()));
        FormatComment.getChildren().clear();
        CommentView.getChildren().remove(FormatComment);
        loadComment();
        CommentField.clear();

    }


}
