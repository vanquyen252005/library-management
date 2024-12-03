package com.example.demo.student;

import com.example.demo.HelloController;
import com.example.demo.book.Book;
import com.example.demo.book.Book_borrowed;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Objects;

import static java.lang.Integer.parseInt;


public class ProfileController extends menuController {
    public AnchorPane rootPane;
    public Button back;
    protected Student user = studentcontroller.getStudent();
    @FXML
    private Button editProfileButton;
    @FXML
    private TableView<UserInfo> userInfoTable;
    @FXML
    private TableColumn<UserInfo, String> fieldColumn;
    @FXML
    private TableColumn<UserInfo, String> valueColumn;
    @FXML
    private TableView<BookInfo> borrowedBooksTable;
    @FXML
    private TableColumn<BookInfo, String> bookIdColumn;
    @FXML
    private TableColumn<BookInfo, String> borrowedDateColumn;
    @FXML
    private TableColumn<BookInfo, String> returnDateColumn;
    @FXML
    private TableColumn<BookInfo, String> TitleColumn;
    @FXML
    private TableColumn<BookInfo, String> AuthorColumn;
    @FXML
    private TableColumn<BookInfo, String> PublishYearColumn;
    @FXML
    private TableColumn<BookInfo, String> PublisherColumn;
    public void initialize() {
        super.initialize();
        fieldColumn.setCellValueFactory(new PropertyValueFactory<>("field"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));


        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        TitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        AuthorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        PublishYearColumn.setCellValueFactory(new PropertyValueFactory<>("publishYear"));
        PublisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        borrowedDateColumn.setCellValueFactory(new PropertyValueFactory<>("borrowedDate"));
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

        loadUserInfo();
        loadBorrowedBooks();
    }

    public void loadUserInfo() {
        ObservableList<UserInfo> userInfoList = FXCollections.observableArrayList();
        jdbc db = jdbc.getInstance();
        Student userInfo = db.LoadUserInfor(Integer.parseInt(user.getId()));
        if (userInfo != null) {
            userInfoList.add(new UserInfo("Username", userInfo.getUsername()));
            userInfoList.add(new UserInfo("Name", userInfo.getName()));
            userInfoList.add(new UserInfo("Role", userInfo.getRole()));
            userInfoList.add(new UserInfo("Phone", userInfo.getPhone()));
            userInfoList.add(new UserInfo("Class", userInfo.getClassname()));
        } else {
            System.out.println("Không tìm thấy thông tin người dùng.");
        }

        userInfoTable.setItems(userInfoList);
    }

    private void loadBorrowedBooks() {
        ObservableList<BookInfo> bookInfoList = FXCollections.observableArrayList();

        ArrayList<Book_borrowed> list = new ArrayList<>();

        list = user.getBorrowingBook();
        for (Book_borrowed x:list) {
            bookInfoList.add(new BookInfo(
                    x.getISBN(),x.getTitle(),x.getAuthor(),x.getPublishYear(),x.getPublisher(),x.getBorrow_date(),x.getReturn_date()
            ));
        }

        borrowedBooksTable.setItems(bookInfoList);
    }

    public void editProfile(ActionEvent event) {
        displayScene(event,"editProfile.fxml");
    }

    public void BACK(ActionEvent event) {
        displayScene(event,"menu.fxml");
    }

    public static class UserInfo {
        private final String field;
        private final String value;

        public UserInfo(String field, String value) {
            this.field = field;
            this.value = value;
        }

        public String getField() {
            return field;
        }

        public String getValue() {
            return value;
        }
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