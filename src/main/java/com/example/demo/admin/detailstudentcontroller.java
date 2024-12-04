package com.example.demo.admin;

import com.example.demo.book.Book_borrowed;
import com.example.demo.student.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class detailstudentcontroller extends menucontroller {
    @FXML
    public Pane detailStudent;
    @FXML
    public TableView borrowingBook;
    @FXML
    public TableView requestBook1;
    @FXML
    public TreeView<String> miniBar;
    public Label name;
    public Label Class;
    public Label id;
    public Label phone;
    public Label username;
    private static ImageView savedImageView;
    protected static String savedImageURI;
    @FXML
    protected ImageView profileImageView = new ImageView();
    @FXML
    private Student curStudent = managestudentcontroller.onClickStudent;
    @FXML
    private TableView<BookInfo> borrowedBooksTable = new TableView<>();
    @FXML
    private TableColumn<BookInfo, String> bookIdColumn = new TableColumn<>();
    @FXML
    private TableColumn<BookInfo, String> borrowedDateColumn = new TableColumn<>();
    @FXML
    private TableColumn<BookInfo, String> returnDateColumn = new TableColumn<>();
    @FXML
    private TableColumn<BookInfo, String> TitleColumn = new TableColumn<>();
    @FXML
    private TableColumn<BookInfo, String> AuthorColumn = new TableColumn<>();
    @FXML
    private TableColumn<BookInfo, String> PublishYearColumn = new TableColumn<>();
    @FXML
    private TableColumn<BookInfo, String> PublisherColumn = new TableColumn<>();

    public ImageView getProfileImageView() {
        return profileImageView;
    }

    @Override
    @FXML
    public void initialize() {
        super.initialize();

        {
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

            home.getStyleClass().add("pre");
            manageStudent.getStyleClass().add("selected");
            manageBook.getStyleClass().add("after");
        }
        name.setText(curStudent.getName());
        Class.setText(curStudent.getClassname());
        id.setText(curStudent.getId());
        phone.setText(curStudent.getPhone());
        username.setText(curStudent.getUsername());
        if (savedImageURI != null) {
            System.out.println("Loading saved image.");
            Image savedImage = new Image(savedImageURI);
            profileImageView.setImage(savedImage);
        }
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        TitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        AuthorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        PublishYearColumn.setCellValueFactory(new PropertyValueFactory<>("publishYear"));
        PublisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        borrowedDateColumn.setCellValueFactory(new PropertyValueFactory<>("borrowedDate"));
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

        loadBook();
    }

    private void loadBook() {
        ObservableList<BookInfo> bookInfoList = FXCollections.observableArrayList();

        ArrayList<Book_borrowed> list = new ArrayList<>();

        list = curStudent.getBorrowingBook();
        for (Book_borrowed x:list) {
            bookInfoList.add(new BookInfo(
                    x.getISBN(),x.getTitle(),x.getAuthor(),x.getPublishYear(),x.getPublisher(),x.getBorrow_date(),x.getReturn_date()
            ));
        }

        borrowedBooksTable.setItems(bookInfoList);
    }
    public static class BookInfo {
        private  String bookId;
        private  String borrowedDate;
        private  String returnDate;
        private  String title;
        private String author;
        private String publishYear;
        private String publisher;

        public BookInfo(String bookId, String title, String author, String publishYear, String publisher, String borrowDate, String returnDate) {
            this.bookId = bookId;
            this.title = title;
            this.author = author;
            this.publishYear= publishYear;
            this.publisher = publisher;
            this.borrowedDate = borrowDate;
            this.returnDate = returnDate;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        public String getPublishYear() {
            return publishYear;
        }

        public String getPublisher() {
            return publisher;
        }

        public String getBookId() {
            return bookId;
        }

        public String getBorrowedDate() {
            return borrowedDate;
        }

        public String getReturnDate() {
            return returnDate;
        }
    }

}
