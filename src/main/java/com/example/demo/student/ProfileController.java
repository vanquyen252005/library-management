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


public class ProfileController extends studentcontroller{
    public AnchorPane rootPane;
    public Button back;
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

    public void initialize() {
        //rootPane.getScene().getStylesheets().add(Objects.requireNonNull(getClass().getResource("student/student.css")).toExternalForm());
        // Cài đặt cột cho bảng thông tin người dùng
        fieldColumn.setCellValueFactory(new PropertyValueFactory<>("field"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));


        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        borrowedDateColumn.setCellValueFactory(new PropertyValueFactory<>("borrowedDate"));
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

        // Load dữ liệu
        loadUserInfo();
        loadBorrowedBooks();
    }

    private void loadUserInfo() {
        ObservableList<UserInfo> userInfoList = FXCollections.observableArrayList();
//        try (Connection connection =ProfileDatabase.getConnection()) {
//            String query = "SELECT username, password, name, role, phone, classname FROM userdata WHERE id = ?";
//            PreparedStatement statement = connection.prepareStatement(query);
//            statement.setInt(1, parseInt(user.getId()) );
//            ResultSet resultSet = statement.executeQuery();

//            if (resultSet.next()) {
                userInfoList.add(new UserInfo("Username", user.getUsername()));
                userInfoList.add(new UserInfo("Name", user.getName()));
                userInfoList.add(new UserInfo("Role",user.getRole()));
                userInfoList.add(new UserInfo("Phone", user.getPhone()));
                userInfoList.add(new UserInfo("Class", user.getClassname()));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        userInfoTable.setItems(userInfoList);
    }

   private void loadBorrowedBooks() {
      ObservableList<BookInfo> bookInfoList = FXCollections.observableArrayList();

           ArrayList<Book_borrowed> list = new ArrayList<>();
           list = user.getBorrowingBook();
           for (Book_borrowed x:list) {
               bookInfoList.add(new BookInfo(
                       x.getISBN(),x.getBorrow_date(),x.getReturn_date()
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
        private final String bookId;
        private final String borrowedDate;
        private final String returnDate;

        public BookInfo(String bookId, String borrowedDate, String returnDate) {
            this.bookId = bookId;
            this.borrowedDate = borrowedDate;
            this.returnDate = returnDate;
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

