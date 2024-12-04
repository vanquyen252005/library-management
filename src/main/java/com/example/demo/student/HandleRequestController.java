package com.example.demo.student;

import com.example.demo.DesignPattern.Singleton.NotificationManager;
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

import static java.lang.Integer.parseInt;

public class HandleRequestController extends menuController {

    public Button Back_btn;
    protected Student user = studentcontroller.getStudent();
    @FXML
    private ListView<Book_borrowed> bookListView;


    @FXML
    public void initialize() {
        super.initialize();
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
                    Label bookLabel1 = new Label(book.getTitle());
                    Label bookLabel2 = new Label(book.getAuthor());
                    Label bookLabel3 = new Label(book.getBorrow_date());
                    Button returnButton = new Button("Return");
                    returnButton.setOnAction(event -> handleReturnBook(book));
                    hBox.getChildren().addAll(bookLabel,bookLabel1,bookLabel2,bookLabel3,returnButton);
                    setGraphic(hBox);
                }
            }

            private void handleReturnBook(Book_borrowed book) {
                boolean isNotified = NotificationManager.getInstance().userNotify(Integer.parseInt(user.getId()),4,book.getISBN());
                if(!isNotified) {
                    System.out.println("#4 unable to notify borrowing book");
                }
                jdbc db = jdbc.getInstance();
                boolean success = db.insertReturnRequest(book.getISBN(), parseInt(user.getId()));
                if (success) {
                    System.out.println("Yêu cầu trả sách đã được gửi thành công.");
                } else {
                    System.out.println("Có lỗi xảy ra khi gửi yêu cầu trả sách.");
                }
            }
        });
    }

    // Hàm truy vấn sách từ cơ sở dữ liệu
    private ObservableList<Book_borrowed> fetchBooksFromDatabase() {
        ObservableList<Book_borrowed> books = FXCollections.observableArrayList();
        ArrayList<Book_borrowed> list = user.getBorrowingBook();
        for (Book_borrowed x : list) {
            books.add(new Book_borrowed(
                    x.getTitle(),x.getPublisher(),x.getPublishYear(),x.getAuthor(),
                    x.getISBN(), x.getBorrow_date(), x.getReturn_date()
            ));
        }
        return books;
    }

}