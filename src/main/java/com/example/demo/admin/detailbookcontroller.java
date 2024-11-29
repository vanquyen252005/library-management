package com.example.demo.admin;

import com.example.demo.book.*;
import com.example.demo.book.RequestBook;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

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
    private RequestBook curBook = managebookcontroller.onClickBook;
    @FXML
    protected ImageView bookImageView;
    private int ParentComment;

    private ConnectDB BookDatabase = new ConnectDB();
    @Override
    @FXML
    public void initialize() {
        super.initialize();
        super.manageBook.getStyleClass().add("selected");
//        bookImageView = curBook.loadImage();
        bookImageView.setFitWidth(200); // Chiều rộng (px)
        bookImageView.setFitHeight(300);
        bookImageView.setPreserveRatio(true);
        bookImageView.setImage(curBook.loadImage().getImage());
    }

    public void ClickBookQR(ActionEvent event) {
        ImageView QRView = BookQR.createQRCodeImageView(curBook.toString());
        QRImageView.setImage(QRView.getImage());
        System.out.println("hehe1");
    }


    public void loadComment() {
        //List<Comment> commentList = BookDatabase.GetCommentList(ISBN);
        List<Comment> commentList = curBook.getCommentList();

        ParentComment = commentList.size();
        // CommentField.setText("");

        if (commentList == null || commentList.isEmpty()) {
            System.out.println("No comments found for ISBN: " + curBook.getISBN());
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
        //BookDatabase.PostCommentForBook(PostContent,curBook.getISBN(), 3);
//        curBook.getNewComment(PostContent,user.getId());
//        FormatComment.getChildren().clear();
//        CommentView.getChildren().remove(FormatComment);
//        loadComment();
//        CommentField.clear();



    }


}
