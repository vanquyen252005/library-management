package com.example.demo.student;

import com.example.demo.book.Book_borrowed;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class HandleRequestController extends studentcontroller {

    @FXML
    private ListView<Book_borrowed> bookListView;


   Student user = new Student();
    @FXML
    public void initialize() {
        // Lấy danh sách sách từ cơ sở dữ liệu
        ObservableList<Book_borrowed> books = fetchBooksFromDatabase();

        // Đặt danh sách vào ListView
        bookListView.setItems(books);

        // Tạo cell factory để hiển thị sách và nút trả sách
        bookListView.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Book_borrowed book, boolean empty) {
                super.updateItem(book, empty);
                if (empty || book == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    HBox hBox = new HBox(10);
                    Label bookLabel = new Label(book.getISBN());
                    Button returnButton = new Button("Trả Sách");
                    returnButton.setOnAction(event -> handleReturnBook(book));
                    hBox.getChildren().addAll(bookLabel, returnButton);
                    setGraphic(hBox);
                }
            }

            private void handleReturnBook(Book_borrowed book) {

            }
        });
    }

    // Hàm truy vấn sách từ cơ sở dữ liệu
    private ObservableList<Book_borrowed> fetchBooksFromDatabase() {
        ObservableList<Book_borrowed> books = FXCollections.observableArrayList();
        ArrayList<Book_borrowed> list = user.getBorrowingBook();
        for (Book_borrowed x : list) {
            books.add(new Book_borrowed(
                    x.getISBN(), x.getBorrow_date(), x.getReturn_date()
            ));
        }
        return books;
    }

    private void deleteBookFromDatabase(String bookISBN) {
        String jdbcURL = "jdbc:mysql://localhost:3306/LibraryDB";
        String username = "root";
        String password = "password";

        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {
            String sql = "DELETE FROM books WHERE isbn = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, bookISBN);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleReturnAllBooks(ActionEvent event) {
    }
}
