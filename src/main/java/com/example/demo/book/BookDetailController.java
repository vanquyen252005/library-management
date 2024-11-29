package com.example.demo.book;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import com.example.demo.student.Student;

import java.util.List;

public class BookDetailController {

    private Stage stage;
    private Scene homeScene; // Store the original home scene
    protected Scene scene;
    protected AnchorPane root;

    public void setStage(Stage stage) {
        this.stage = stage;
    }


    public void setHomeScene(Scene homeScene) {
        this.homeScene = homeScene;
    }


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

    private String ISBN;

    private ConnectDB BookDatabase = new ConnectDB();

    private BookQR bookQR = new BookQR();

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
        RequestBook book = BookDatabase.getBookByISBN(ISBN);

        if (book != null) {
            // Cập nhật các label và ảnh bìa sách
            titleLabel.setText(book.getTitle());
            authorLabel.setText(book.getAuthor());
            publisherLabel.setText(book.getPublisher());
            yearLabel.setText(book.getPublishYear());
            bookImageView = book.loadImage();
        }
    }




    @FXML
    private void BackToHome(ActionEvent event) {
        try {
            // Debugging to ensure both stage and homeScene are set
            if (stage == null) {
                System.err.println("BookDetailController: Stage is null.");
            }
            if (homeScene == null) {
                System.err.println("BookDetailController: Home scene is null.");
            }

            if (homeScene != null && stage != null) {
                stage.setScene(homeScene); // Return to the stored home scene
                stage.show();
                System.out.println("BookDetailController: Returned to home scene.");
            } else {
                System.err.println("BookDetailController: Home scene or stage is null.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void ClickBorrowButton(ActionEvent event) {
        System.out.println("beign in borrow button");
        System.out.println(Borrow_book.getText());
        if (Borrow_book.getText().equals("Borrow")) {
            Student current_user = new Student();
            //String studentID = current_user.getId();
            //studentID = (studentID != null) ? current_user.getId() : "10101";
            String bookISBN = this.ISBN;
            boolean isSent = BookDatabase.sendBorrowRequest("3", bookISBN);
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
        RequestBook book = BookDatabase.getBookByISBN(ISBN);
        ImageView QRView = bookQR.createQRCodeImageView(book.toString());
        QRImageView.setImage(QRView.getImage());
    }

    public void check_Isbn_And_User() {
        String status_of_request = BookDatabase.RequestStatus(3, ISBN); // check if you have requested borrowing
        boolean isBorrowed = BookDatabase.isBookBorrowed(3,ISBN); // check if is book borrowed
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
        BookDatabase.UndoRequest(3,ISBN);
        UndoRequestButton.setVisible(false);
        Borrow_book.setText("Borrow");
    }

    public void loadComment() {
        List<Comment> commentList = BookDatabase.GetCommentList(ISBN);

        ParentComment = commentList.size();
        // CommentField.setText("");

        if (commentList == null || commentList.isEmpty()) {
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


            TreeView<String> treeView = new TreeView<>();
            treeView.setPrefWidth(50); // Đặt chiều rộng cố định 300px
            treeView.setPrefHeight(50); // Đặt chiều cao cố định 400px

//            loadChildrenComment(treeView,comment);
//            FormatComment.add(treeView,0,row+2);


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
        BookDatabase.PostCommentForBook(PostContent,ISBN, 3);

        FormatComment.getChildren().clear();
        CommentView.getChildren().remove(FormatComment);


        loadComment();


        CommentField.clear();



    }



}

