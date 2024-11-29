package com.example.demo.admin;

import com.example.demo.book.RequestBook;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;


public class managebookcontroller extends menucontroller {
    @FXML
    private TextField input;
    @FXML
    TableView<RequestBook> bookTable;
    @FXML
    private ListView<String> suggestionList;
    @FXML
    private Button backButton;

    @FXML
    private Button forwardButton;

    @FXML
    private ProgressIndicator loadingSpinner;
    public static RequestBook onClickBook = new RequestBook();
    @Override
    @FXML
    public void initialize() {
        super.initialize();
        TableColumn<RequestBook, String> column1 =
                new TableColumn<>("ISBN");
        TableColumn<RequestBook, String> column2 =
                new TableColumn<>("Title");
        TableColumn<RequestBook, String> column3 =
                new TableColumn<>("Author");
        TableColumn<RequestBook, String> column4 =
                new TableColumn<>("Publisher");
        TableColumn<RequestBook, Void> actionColumn = new TableColumn<>("Action");

        column1.setCellValueFactory(
                new PropertyValueFactory<>("ISBN"));
        column2.setCellValueFactory(
                new PropertyValueFactory<>("Title"));
        column3.setCellValueFactory(
                new PropertyValueFactory<>("Author"));
        column4.setCellValueFactory(
                new PropertyValueFactory<>("Publisher")
        );
        Callback<TableColumn<RequestBook, Void>, TableCell<RequestBook, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<RequestBook, Void> call(final TableColumn<RequestBook, Void> param) {
                final TableCell<RequestBook, Void> cell = new TableCell<>() {
                    private Button deleteButton = new Button("Delete");
                    private Button detailButton = new Button("Detail");
                    private Button updateButton = new Button("Update");
                    private final HBox hbox = new HBox(10); // HBox để chứa các nút, khoảng cách giữa các nút là 10

                    {
                        deleteButton.getStyleClass().add("delete-button");
                        // Đặt sự kiện cho nút "Delete"
                        deleteButton.setOnAction(event -> {
                            RequestBook book = getTableView().getItems().get(getIndex());
                            book.deleteBook();
                            searchBook();
                        });

                        detailButton.getStyleClass().add("detail-button");
                        // Đặt sự kiện cho nút "Detail"
                        detailButton.setOnAction(event -> {
                            RequestBook book = getTableView().getItems().get(getIndex());
                            detailBook(book.getBook(), event);
                        });

                        updateButton.getStyleClass().add("update-button");
                        // Đặt sự kiện cho nút "Delete"
                        updateButton.setOnAction(event -> {
                            RequestBook book = getTableView().getItems().get(getIndex());
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
        column1.setCellFactory(param -> new TableCell<RequestBook, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                getStyleClass().add("table-student-id-cell");
            }
        });
        column2.setCellFactory(param -> new TableCell<RequestBook, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                getStyleClass().add("table-student-username-cell");
            }
        });
        column3.setCellFactory(param -> new TableCell<RequestBook, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                getStyleClass().add("table-student-name-cell");
            }
        });
        column4.setCellFactory(param -> new TableCell<RequestBook, String>() {
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
        bookTable.getColumns().addAll(column1, column2, column3, column4, actionColumn);
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
    }
    private List<String> getSearchSuggestions(String searchText) {
        List<String> suggestions = new ArrayList<>();
        List<RequestBook> books = RequestBook.getBookDB(searchText);

        // Tạo ExecutorService với số luồng cố định
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        // Tạo danh sách các tác vụ
        List<Callable<List<String>>> tasks = new ArrayList<>();

        // Tác vụ kiểm tra Title
        tasks.add(() -> {
            List<String> results = new ArrayList<>();
            for (RequestBook book : books) {
                if (book.getTitle().toLowerCase().contains(searchText.toLowerCase())) {
                    results.add(book.getTitle());
                }
            }
            return results;
        });

        // Tác vụ kiểm tra ISBN
        tasks.add(() -> {
            List<String> results = new ArrayList<>();
            for (RequestBook book : books) {
                if (book.getISBN().toLowerCase().contains(searchText.toLowerCase())) {
                    results.add(book.getISBN());
                }
            }
            return results;
        });

        // Tác vụ kiểm tra Author
        tasks.add(() -> {
            List<String> results = new ArrayList<>();
            for (RequestBook book : books) {
                if (book.getAuthor().toLowerCase().contains(searchText.toLowerCase())) {
                    results.add(book.getAuthor());
                }
            }
            return results;
        });

        // Tác vụ kiểm tra Publisher
        tasks.add(() -> {
            List<String> results = new ArrayList<>();
            for (RequestBook book : books) {
                if (book.getPublisher().toLowerCase().contains(searchText.toLowerCase())) {
                    results.add(book.getPublisher());
                }
            }
            return results;
        });

        // Tác vụ kiểm tra PublishYear
        tasks.add(() -> {
            List<String> results = new ArrayList<>();
            for (RequestBook book : books) {
                if (book.getPublishYear().toLowerCase().contains(searchText.toLowerCase())) {
                    results.add(book.getPublishYear());
                }
            }
            return results;
        });

        try {
            // Thực hiện các tác vụ song song
            List<Future<List<String>>> results = executorService.invokeAll(tasks);

            // Tổng hợp kết quả từ các tác vụ
            for (Future<List<String>> result : results) {
                suggestions.addAll(result.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            // Tắt ExecutorService
            executorService.shutdown();
        }

        // Loại bỏ các gợi ý trùng lặp (nếu cần)
        return suggestions.stream().distinct().collect(Collectors.toList());
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
        // Hiển thị spinner
        loadingSpinner.setVisible(true);

        Task<List<RequestBook>> task = new Task<>() {
            @Override
            protected List<RequestBook> call() throws Exception {
                List<RequestBook> books = new ArrayList<>();

                // Tạo ExecutorService với số luồng cố định
                ExecutorService executorService = Executors.newFixedThreadPool(4);

                // Tạo danh sách các tác vụ
                List<Callable<List<RequestBook>>> tasks = new ArrayList<>();
                tasks.add(() -> RequestBook.searchBooksByTitle(input.getText())); // Tìm kiếm theo Title
                tasks.add(() -> RequestBook.searchBooksByAuthor(input.getText())); // Tìm kiếm theo Author
                tasks.add(() -> RequestBook.searchBooksByPublisher(input.getText())); // Tìm kiếm theo Publisher

                try {
                    // Thực hiện các tác vụ song song
                    List<Future<List<RequestBook>>> results = executorService.invokeAll(tasks);

                    // Tổng hợp kết quả từ các tác vụ
                    for (Future<List<RequestBook>> result : results) {
                        books.addAll(result.get());
                    }

                } finally {
                    executorService.shutdown();
                }

                // Loại bỏ các sách trùng lặp (nếu cần)
                return books.stream().distinct().collect(Collectors.toList());
            }
        };

        // Xử lý khi hoàn thành
        task.setOnSucceeded(e -> {
            List<RequestBook> books = task.getValue();
            bookTable.setItems(FXCollections.observableArrayList(books));
            toggleTableViewVisibility(bookTable, true);

            // Ẩn spinner sau khi hoàn thành
            loadingSpinner.setVisible(false);
        });

        // Xử lý khi có lỗi
        task.setOnFailed(e -> {
            task.getException().printStackTrace();
            loadingSpinner.setVisible(false);
        });

        // Chạy Task trong một luồng riêng
        new Thread(task).start();
    }

    //    public void searchBook() {
//        List<Book> books = new ArrayList<>();
//
//        // Tạo ExecutorService với số luồng cố định
//        ExecutorService executorService = Executors.newFixedThreadPool(4);
//
//        // Tạo danh sách các tác vụ
//        List<Callable<List<Book>>> tasks = new ArrayList<>();
//
//        // Chia công việc thành các truy vấn độc lập
//        tasks.add(() -> Book.searchBooksByTitle(input.getText())); // Tìm kiếm theo Title
//        tasks.add(() -> Book.searchBooksByAuthor(input.getText())); // Tìm kiếm theo Author
//        tasks.add(() -> Book.searchBooksByPublisher(input.getText())); // Tìm kiếm theo Publisher
//
//        try {
//            // Thực hiện các tác vụ song song
//            List<Future<List<Book>>> results = executorService.invokeAll(tasks);
//
//            // Tổng hợp kết quả từ các tác vụ
//            for (Future<List<Book>> result : results) {
//                books.addAll(result.get());
//            }
//
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        } finally {
//            executorService.shutdown();
//        }
//
//        // Loại bỏ các sách trùng lặp (nếu cần)
//        books = books.stream().distinct().collect(Collectors.toList());
//        bookTable.setItems(FXCollections.observableArrayList());
//        toggleTableViewVisibility(bookTable, true);
//        for (Book x:books) {
////            root1.getChildren().add(new TreeItem<>(x));
//            bookTable.getItems().add(x);
//            System.out.println(x);
//        }
////        System.out.println("Returning book list with size: " + books.size());
////        return books;
//    }
    public void toggleTableViewVisibility(TableView<?> tableView, boolean isVisible) {
//        boolean isVisible = tableView.isVisible();
        tableView.setVisible(isVisible);
        tableView.setManaged(isVisible);
    }
    public void detailBook(RequestBook cur, ActionEvent event) {
        bookTable.setItems(FXCollections.observableArrayList());
        System.out.println(event.getTarget());
        onClickBook = cur;
        displayScene(event,"DetailBook.fxml");
    }
    public void updateBook(RequestBook cur, ActionEvent event) {
        bookTable.setItems(FXCollections.observableArrayList());
        System.out.println(event.getTarget());
        onClickBook = cur;
        displayScene(event,"UpdateBook.fxml");
    }
    public void createBook(ActionEvent event) {
        displayScene(event,"CreateBook.fxml");
    }
}
