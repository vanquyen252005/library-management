package com.example.demo.admin;

import com.example.demo.book.Book;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;


public class managebookcontroller extends menucontroller {
    public ScrollPane scrollPane;
    public ScrollBar scrollBar;
    ScrollBar verticalScrollBar;
    public VBox vbox;
    private List<Book> books = new ArrayList<>();
    @FXML
    private TextField input;
    @FXML
    TableView<Book> bookTable;
    @FXML
    private ListView<String> suggestionList;

    @FXML
    private ProgressIndicator loadingSpinner;
    private int currentPage = 1;
    public static Book onClickBook = new Book();
//    private static final ExecutorService executorService = Executors.newFixedThreadPool(3);
    @Override
    @FXML
    public void initialize() {
        super.initialize();
        {home.getStyleClass().remove("selected");
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

        manageStudent.getStyleClass().add("pre");
        manageBook.getStyleClass().add("selected");
        handleRequest.getStyleClass().add("after");}
        TableColumn<Book, String> column1 =
                new TableColumn<>("ISBN");
        TableColumn<Book, String> column2 =
                new TableColumn<>("Title");
        TableColumn<Book, String> column3 =
                new TableColumn<>("Author");
        TableColumn<Book, String> column4 =
                new TableColumn<>("Publisher");
        TableColumn<Book, Void> actionColumn = new TableColumn<>("Action");
        TableColumn<Book, String> imageColumn = new TableColumn<>("Image");

        column1.setCellValueFactory(
                new PropertyValueFactory<>("ISBN"));
        column2.setCellValueFactory(
                new PropertyValueFactory<>("Title"));
        column3.setCellValueFactory(
                new PropertyValueFactory<>("Author"));
        column4.setCellValueFactory(
                new PropertyValueFactory<>("Publisher")
        );
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("Image")); // Thuộc tính "Image" phải trỏ đến URL hoặc đối tượng hợp lệ

        imageColumn.setCellFactory(param -> new TableCell<Book, String>() {

            @Override
            protected void updateItem(String imageUrl, boolean empty) {
                super.updateItem(imageUrl, empty);

                if (empty || imageUrl == null) {
                    setGraphic(null); // Nếu không có dữ liệu, không hiển thị gì
                } else {
                    try {
                        // Gọi hàm loadImage() từ đối tượng Book để tạo ImageView
                        Book book = getTableView().getItems().get(getIndex());
                        multiThread.getExecutorService().submit(() -> {
                            try {
                                // Load the image in the background
                                ImageView imageView = book.loadImage(); // Phương thức trả về ImageView đã được cấu hình
                                imageView.setFitHeight(50); // Chiều cao ảnh
                                imageView.setFitWidth(50);  // Chiều rộng ảnh
                                imageView.setPreserveRatio(true); // Duy trì tỷ lệ ảnh

                                // Update the UI (setGraphic) on the JavaFX Application thread
                                Platform.runLater(() -> setGraphic(imageView)); // Gắn ImageView vào ô
                            } catch (Exception e) {
                                e.printStackTrace();
                                Platform.runLater(() -> setGraphic(null)); // In case of error, clear the graphic
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        Platform.runLater(() -> setGraphic(null)); // In case of error, clear the graphic
                    }
                }
            }
        });


        Callback<TableColumn<Book, Void>, TableCell<Book, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Book, Void> call(final TableColumn<Book, Void> param) {
                final TableCell<Book, Void> cell = new TableCell<>() {
                    private Button deleteButton = new Button("Delete");
                    private Button detailButton = new Button("Detail");
                    private Button updateButton = new Button("Update");
                    private final HBox hbox = new HBox(10); // HBox để chứa các nút, khoảng cách giữa các nút là 10

                    {
                        deleteButton.getStyleClass().add("delete-button");
                        // Đặt sự kiện cho nút "Delete"
                        deleteButton.setOnAction(event -> {
                            Book book = getTableView().getItems().get(getIndex());
                            book.deleteBook();
                            searchBook();
                        });

                        detailButton.getStyleClass().add("detail-button");
                        // Đặt sự kiện cho nút "Detail"
                        detailButton.setOnAction(event -> {
                            Book book = getTableView().getItems().get(getIndex());
                            detailBook(book.getBook(), event);
                        });

                        updateButton.getStyleClass().add("button-update");
                        // Đặt sự kiện cho nút "Delete"
                        updateButton.setOnAction(event -> {
                            Book book = getTableView().getItems().get(getIndex());
                            updateBook(book.getBook(), event);
                        });

                        // Thêm các nút vào HBox
                        hbox.getChildren().addAll(deleteButton, detailButton, updateButton);
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(hbox); // Hiển thị HBox chứa các nút trong ô
                        }
                    }
                };
                cell.getStyleClass().add("table-student-detail-cell");
                return cell;
            }
        };
        column1.setCellFactory(param -> new TableCell<Book, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                getStyleClass().add("table-student-id-cell");
            }
        });
        column2.setCellFactory(param -> new TableCell<Book, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                getStyleClass().add("table-student-username-cell");
            }
        });
        column3.setCellFactory(param -> new TableCell<Book, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                getStyleClass().add("table-student-name-cell");
            }
        });
        column4.setCellFactory(param -> new TableCell<Book, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                getStyleClass().add("table-student-class-cell");
            }
        });
        // Đặt cellFactory cho cột actions
        actionColumn.setCellFactory(cellFactory);
        column1.getStyleClass().add("table-student-id-cell");
        column2.getStyleClass().add("table-student-username-cell");
        column3.getStyleClass().add("table-student-name-cell");
        column4.getStyleClass().add("table-student-class-cell");
        bookTable.getColumns().addAll(column1, column2, column3, column4, actionColumn, imageColumn);
        suggestionList.setVisible(false); // Ẩn gợi ý ban đầu
        input.setOnKeyTyped(this::onKeyTyped); // Lắng nghe sự kiện khi gõ
        suggestionList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // Single click
                input.setText(suggestionList.getSelectionModel().getSelectedItem());
                System.out.println("Clicked: " + input.getText());
                suggestionList.setVisible(false);
                searchBook();
            }
        });
        verticalScrollBar = (ScrollBar) bookTable.lookup(".scroll-bar:vertical");
//        scrollBar = (ScrollPane) bookTable.lookup(".scroll-pane");
        // Initial load with empty search
//        scrollPane.setOnScroll(event -> {
//            if (scrollPane.getVvalue() >= 0.7) {
//                currentPage++;  // Increase the page
//                searchBook();  // Load more books
//                scrollPane.setVvalue(0.2);
//            } else if (scrollPane.getVvalue() == 0.0 && currentPage > 0) {
//                currentPage--;
//                searchBook();  // Load more books
//                scrollPane.setVvalue(0.7);
//            }
//        });

    }
    private void loadMoreBooks() {
        loadingSpinner.setVisible(true); // Hiển thị spinner khi đang tải dữ liệu

        Task<List<Book>> task = new Task<>() {
            @Override
            protected List<Book> call() {
                // Gọi phương thức trong lớp Book để lấy dữ liệu theo trang
                return Book.getBooksByPage(currentPage, input.getText());
            }
        };

        task.setOnSucceeded(e -> {
            List<Book> newBook = task.getValue();
            if (newBook != null && !newBook.isEmpty()) {
              for (Book book:newBook) {
                  books.add(book);
              }
                bookTable.setItems(FXCollections.observableArrayList(books));
                toggleTableViewVisibility(bookTable, true);
            }
            loadingSpinner.setVisible(false); // Ẩn spinner sau khi hoàn tất
        });

        task.setOnFailed(e -> {
            task.getException().printStackTrace();
            loadingSpinner.setVisible(false);
        });

        new Thread(task).start();
    }

    private List<String> getSearchSuggestions(String searchText) {
        List<String> suggestions = new ArrayList<>();
        List<Book> books = Book.getBookDB(searchText);
        List<String> results = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(searchText.toLowerCase())) {
                results.add(book.getTitle());
            }
        }
            for (Book book : books) {
                if (book.getISBN().toLowerCase().contains(searchText.toLowerCase())) {
                    results.add(book.getISBN());
                }
            }
            for (Book book : books) {
                if (book.getAuthor().toLowerCase().contains(searchText.toLowerCase())) {
                    results.add(book.getAuthor());
                }
            }
            for (Book book : books) {
                if (book.getPublisher().toLowerCase().contains(searchText.toLowerCase())) {
                    results.add(book.getPublisher());
                }
            }
            for (Book book : books) {
                if (book.getPublishYear().toLowerCase().contains(searchText.toLowerCase())) {
                    results.add(book.getPublishYear());
                }
            }
        return results.stream().distinct().collect(Collectors.toList());
    }
    private void onKeyTyped(KeyEvent event) {
        String searchText = input.getText().trim();

        if (searchText.isEmpty()) {
            suggestionList.setVisible(false); // Ẩn khi không có từ khóa
            return;
        }

        // Hiển thị spinner
        loadingSpinner.setVisible(true);

        Task<List<String>> task = new Task<>() {
            @Override
            protected List<String> call() throws Exception {
                return getSearchSuggestions(searchText);
            }
        };

        // Khi tìm kiếm hoàn thành, cập nhật danh sách gợi ý
        task.setOnSucceeded(e -> {
            List<String> suggestions = task.getValue();
            suggestionList.setItems(FXCollections.observableArrayList(suggestions));
            suggestionList.setVisible(!suggestions.isEmpty()); // Hiển thị nếu có gợi ý

            // Ẩn spinner
            loadingSpinner.setVisible(false);
        });

        // Nếu có lỗi
        task.setOnFailed(e -> {
            task.getException().printStackTrace();
            loadingSpinner.setVisible(false);
        });

        // Chạy Task trong một luồng riêng
        new Thread(task).start();
    }

    @FXML
    public void searchBook() {
//            List<Book> books = Book.getBookDB(input.getText());
//            bookTable.setItems(FXCollections.observableArrayList(books));
//            toggleTableViewVisibility(bookTable, true);
        books.clear();
        bookTable.refresh();
        currentPage = 0;
        currentPage++;
        loadMoreBooks();
        Platform.runLater(() -> {
             verticalScrollBar = (ScrollBar) bookTable.lookup(".scroll-bar:vertical");
            if (verticalScrollBar != null) {
                System.out.println("dfsgbxb qqqq");
                verticalScrollBar.valueProperty().addListener((obs, oldValue, newValue) -> {
                    if (newValue.doubleValue() == 1.0) { // Kiểm tra cuộn đến cuối
                        currentPage++;
                        loadMoreBooks();
                    }
                });
            }
        });
    }

    public void toggleTableViewVisibility(TableView<?> tableView, boolean isVisible) {
//        boolean isVisible = tableView.isVisible();
        tableView.setVisible(isVisible);
        tableView.setManaged(isVisible);
    }
    public void detailBook(Book cur, ActionEvent event) {
        bookTable.setItems(FXCollections.observableArrayList());
        System.out.println(event.getTarget());
        onClickBook = cur;
        displayScene(event,"DetailBook.fxml");
    }
    public void updateBook(Book cur, ActionEvent event) {
        bookTable.setItems(FXCollections.observableArrayList());
        System.out.println(event.getTarget());
        onClickBook = cur;
        displayScene(event,"UpdateBook.fxml");
    }
    public void createBook(ActionEvent event) {
        displayScene(event,"CreateBook.fxml");
    }
}
