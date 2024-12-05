package com.example.demo.student;

import com.example.demo.book.BookBorrowed;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;


public class ProfileController extends MenuController {
    public AnchorPane rootPane;
    public Button back;
    protected Student user = StudentController.getStudent();
    private static ImageView savedImageView;
    protected static String savedImageURI;
    @FXML
    protected ImageView profileImageView = new ImageView();
    @FXML
    private Button editProfileButton;
    @FXML
    private TableView<UserInfo> userInfoTable = new TableView<>();
    @FXML
    private TableColumn<UserInfo, String> fieldColumn = new TableColumn<>();
    @FXML
    private TableColumn<UserInfo, String> valueColumn = new TableColumn<>();
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

    public void initialize() {
        if (savedImageURI != null) {
            System.out.println("Loading saved image.");
            Image savedImage = new Image(savedImageURI);
            profileImageView.setImage(savedImage);
        }

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
        Jdbc db = Jdbc.getInstance();
        Student userInfo = db.LoadUserInfor(Integer.parseInt(user.getId()));
        if (userInfo != null) {
            userInfoList.add(new UserInfo("Username", userInfo.getUsername()));
            userInfoList.add(new UserInfo("Name", userInfo.getName()));
            userInfoList.add(new UserInfo("Role", userInfo.getRole()));
            userInfoList.add(new UserInfo("Phone", userInfo.getPhone()));
            userInfoList.add(new UserInfo("Class", userInfo.getClassName()));
        } else {
            System.out.println("Không tìm thấy thông tin người dùng.");
        }

        userInfoTable.setItems(userInfoList);
    }

    private void loadBorrowedBooks() {
        ObservableList<BookInfo> bookInfoList = FXCollections.observableArrayList();

        ArrayList<BookBorrowed> list = new ArrayList<>();

        list = user.getBorrowingBook();
        for (BookBorrowed x:list) {
            bookInfoList.add(new BookInfo(
                    x.getISBN(),x.getTitle(),x.getAuthor(),x.getPublishYear(),x.getPublisher(),x.getBorrowDate(),x.getReturnDate()
            ));
        }

        borrowedBooksTable.setItems(bookInfoList);
    }

    public void editProfile(ActionEvent event) {
        displayScene(event,"EditProfile.fxml");
    }

    public void BACK(ActionEvent event) {
        displayScene(event,"Menu.fxml");
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

    public void chooseProfileImage() {
        // Mở hộp thoại để chọn ảnh
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn ảnh đại diện");

        // Đặt thư mục ban đầu
        File initialDirectory = new File("C:/library-management/src/main/resources/Picture");
        if (initialDirectory.exists() && initialDirectory.isDirectory()) {
            fileChooser.setInitialDirectory(initialDirectory);
        }

        // Bộ lọc để chọn file ảnh
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        // Hiển thị hộp thoại và lấy file được chọn
        File selectedFile = fileChooser.showOpenDialog(profileImageView.getScene().getWindow());
        if (selectedFile != null) {
            // Đặt ảnh mới làm ảnh đại diện
            Image newImage = new Image(selectedFile.toURI().toString());
            profileImageView.setImage(newImage);
            savedImageURI = selectedFile.toURI().toString();

            // Thông báo thành công
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thành công");
            alert.setHeaderText(null);
            alert.setContentText("Ảnh đại diện đã được thay đổi!");
            alert.showAndWait();
        }
    }
}
