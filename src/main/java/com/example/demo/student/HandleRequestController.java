package com.example.demo.student;

import com.example.demo.designpattern.Singleton.NotificationManager;
import com.example.demo.book.BookBorrowed;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class HandleRequestController extends MenuController {

    public Button Back_btn;
    protected Student user = StudentController.getStudent();
    @FXML
    private ListView<BookBorrowed> bookListView;


    @FXML
    public void initialize() {
        super.initialize();
        // Lấy danh sách sách từ cơ sở dữ liệu
        ObservableList<BookBorrowed> books = fetchBooksFromDatabase();

        // Đặt danh sách vào ListView
        bookListView.setItems(books);

        // Tạo cell factory để hiển thị sách và nút trả sách
        bookListView.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(BookBorrowed book, boolean empty) {
                super.updateItem(book, empty);
                if (empty || book == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    HBox hBox = new HBox(10);
                    Label bookLabel = new Label(book.getISBN());
                    Label bookLabel1 = new Label(book.getTitle());
                    Label bookLabel2 = new Label(book.getAuthor());
                    Label bookLabel3 = new Label(book.getBorrowDate());
                    Button returnButton = new Button("Return");
                    returnButton.setOnAction(event -> handleReturnBook(book));
                    hBox.getChildren().addAll(bookLabel,bookLabel1,bookLabel2,bookLabel3,returnButton);
                    setGraphic(hBox);
                }
            }

            private void handleReturnBook(BookBorrowed book) {
                boolean isNotified = NotificationManager.getInstance().userNotify(Integer.parseInt(user.getId()),4,book.getISBN());
                if(!isNotified) {
                    System.out.println("#4 unable to notify borrowing book");
                }
                Jdbc db = Jdbc.getInstance();
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
    private ObservableList<BookBorrowed> fetchBooksFromDatabase() {
        ObservableList<BookBorrowed> books = FXCollections.observableArrayList();
        ArrayList<BookBorrowed> list = user.getBorrowingBook();
        for (BookBorrowed x : list) {
            books.add(new BookBorrowed(
                    x.getTitle(),x.getPublisher(),x.getPublishYear(),x.getAuthor(),
                    x.getISBN(), x.getBorrowDate(), x.getReturnDate()
            ));
        }
        return books;
    }

}