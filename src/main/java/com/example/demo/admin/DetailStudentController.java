package com.example.demo.admin;

import com.example.demo.book.BookBorrowed;
import com.example.demo.student.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

/**
 * Controller class for displaying the details of a student.
 * It manages the student's profile information, avatar, and the books they have borrowed.
 */

public class DetailStudentController extends MenuController {
    @FXML
    public Pane detailStudent;
    @FXML
    public TableView borrowingBook;
    @FXML
    public TableView requestBook1;
    @FXML
    public TreeView<String> miniBar;
    public Label nameLabel;
    public Label classLabel;
    public Label idLabel;
    public Label phoneLabel;
    public Label usernameLabel;
    protected static String savedImageURI;
    public ImageView userAvatar;
    @FXML
    protected ImageView profileImageView = new ImageView();
    @FXML
    private Student curStudent = ManageStudentController.onClickStudent;
    @FXML
    private TableView<BookInfo> borrowedBooksTable = new TableView<>();
    @FXML
    private TableColumn<BookInfo, String> bookIdColumn = new TableColumn<>();
    @FXML
    private TableColumn<BookInfo, String> borrowedDateColumn = new TableColumn<>();
    @FXML
    private TableColumn<BookInfo, String> returnDateColumn = new TableColumn<>();
    @FXML
    private TableColumn<BookInfo, String> titleColumn = new TableColumn<>();
    @FXML
    private TableColumn<BookInfo, String> authorColumn = new TableColumn<>();
    @FXML
    private TableColumn<BookInfo, String> publishYearColumn = new TableColumn<>();
    @FXML
    private TableColumn<BookInfo, String> publisherColumn = new TableColumn<>();

    public ImageView getProfileImageView() {
        return profileImageView;
    }

    /**
     * Initializes the controller with the student's details, including their borrowed books
     * and profile information.
     */
    @Override
    @FXML
    public void initialize() {
        if (savedImageURI != null) {
            System.out.println("Loading saved image.");
            Image savedImage = new Image(savedImageURI);
            userAvatar.setImage(savedImage);
        }

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

            homeButton.getStyleClass().add("pre");
            manageStudent.getStyleClass().add("selected");
            manageBook.getStyleClass().add("after");
        }
        nameLabel.setText(curStudent.getName());
        classLabel.setText(curStudent.getClassName());
        idLabel.setText(curStudent.getId());
        phoneLabel.setText(curStudent.getPhone());
        usernameLabel.setText(curStudent.getUsername());
        if (savedImageURI != null) {
            System.out.println("Loading saved image.");
            Image savedImage = new Image(savedImageURI);
            profileImageView.setImage(savedImage);
        }
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        publishYearColumn.setCellValueFactory(new PropertyValueFactory<>("publishYear"));
        publisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        borrowedDateColumn.setCellValueFactory(new PropertyValueFactory<>("borrowedDate"));
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

        loadBook();
    }

    /**
     * Loads the books borrowed by the current student and displays them in the table.
     */

    private void loadBook() {
        ObservableList<BookInfo> bookInfoList = FXCollections.observableArrayList();

        ArrayList<BookBorrowed> list = new ArrayList<>();

        list = curStudent.getBorrowingBook();
        for (BookBorrowed x:list) {
            bookInfoList.add(new BookInfo(
                    x.getISBN(),x.getTitle(),x.getAuthor(),x.getPublishYear(),x.getPublisher(),x.getBorrowDate(),x.getReturnDate()
            ));
        }

        borrowedBooksTable.setItems(bookInfoList);
    }

    /**
     * class BookInfo.
     */

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
