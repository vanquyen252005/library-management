//```
//        package com.example.demo.admin;
//
//import com.example.demo.book.Book;
//import javafx.collections.FXCollections;
//import javafx.concurrent.Task;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.*;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.input.KeyEvent;
//import javafx.scene.layout.HBox;
//import javafx.util.Callback;
//
//import java.sql.ResultSet;
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class managebookcontroller extends menucontroller {
//    @FXML
//    private TextField input;
//    @FXML
//    TableView<Book> bookTable;
//    @FXML
//    private ListView<String> suggestionList;
//    public static Book onClickBook = new Book();
//    @Override
//    @FXML
//    public void initialize() {
//        home.setOnAction(event -> handleHomeAction(event));
//        manageStudent.setOnAction(event -> handleManageStudentAction(event));
//        manageBook.setOnAction(event -> handleManageBookAction(event));
//        search.setOnAction(event -> handleSearchAction(event));
//        handleRequest.setOnAction(event -> handleHandleRequestAction(event));
//
//        TableColumn<Book, String> column1 =
//                new TableColumn<>("ISBN");
//        TableColumn<Book, String> column2 =
//                new TableColumn<>("Title");
//        TableColumn<Book, String> column3 =
//                new TableColumn<>("Author");
//        TableColumn<Book, String> column4 =
//                new TableColumn<>("Publisher");
//        TableColumn<Book, Void> actionColumn = new TableColumn<>("Action");
//
//        column1.setCellValueFactory(
//                new PropertyValueFactory<>("ISBN"));
//        column2.setCellValueFactory(
//                new PropertyValueFactory<>("Title"));
//        column3.setCellValueFactory(
//                new PropertyValueFactory<>("Author"));
//        column4.setCellValueFactory(
//                new PropertyValueFactory<>("Publisher")
//        );
//        Callback<TableColumn<Book, Void>, TableCell<Book, Void>> cellFactory = new Callback<>() {
//            @Override
//            public TableCell<Book, Void> call(final TableColumn<Book, Void> param) {
//                final TableCell<Book, Void> cell = new TableCell<>() {
//                    private Button deleteButton = new Button("Delete");
//                    private Button detailButton = new Button("Detail");
//                    private final HBox hbox = new HBox(10); // HBox để chứa các nút, khoảng cách giữa các nút là 10
//
//                    {
//                        deleteButton.getStyleClass().add("delete-button");
//                        // Đặt sự kiện cho nút "Delete"
//                        deleteButton.setOnAction(event -> {
//                            Book book = getTableView().getItems().get(getIndex());
//                            book.deleteBook();
//                            searchBook();
//                        });/-strong/-heart:>:o:-((:-h detailButton.getStyleClass().add("detail-button");
//                        // Đặt sự kiện cho nút "Detail"
//                        detailButton.setOnAction(event -> {
//                            Book book = getTableView().getItems().get(getIndex());
//                            detailBook(book.getBook(), event);
//                        });
//
//                        // Thêm các nút vào HBox
//                        hbox.getChildren().addAll(deleteButton, detailButton);
//                    }
//
//                    @Override
//                    protected void updateItem(Void item, boolean empty) {
//                        super.updateItem(item, empty);
//                        if (empty) {
//                            setGraphic(null);
//                        } else {
//                            setGraphic(hbox); // Hiển thị HBox chứa các nút trong ô
//                        }
//                    }
//                };
//                cell.getStyleClass().add("table-student-detail-cell");
//                return cell;
//            }
//        };
//        column1.setCellFactory(param -> new TableCell<Book, String>() {
//            @Override
//            protected void updateItem(String item, boolean empty) {
//                super.updateItem(item, empty);
//                setText(item);
//                getStyleClass().add("table-student-id-cell");
//            }
//        });
//        column2.setCellFactory(param -> new TableCell<Book, String>() {
//            @Override
//            protected void updateItem(String item, boolean empty) {
//                super.updateItem(item, empty);
//                setText(item);
//                getStyleClass().add("table-student-username-cell");
//            }
//        });
//        column3.setCellFactory(param -> new TableCell<Book, String>() {
//            @Override
//            protected void updateItem(String item, boolean empty) {
//                super.updateItem(item, empty);
//                setText(item);
//                getStyleClass().add("table-student-name-cell");
//            }
//        });
//        column4.setCellFactory(param -> new TableCell<Book, String>() {
//            @Override
//            protected void updateItem(String item, boolean empty) {
//                super.updateItem(item, empty);
//                setText(item);
//                getStyleClass().add("table-student-class-cell");
//            }
//        });
//        // Đặt cellFactory cho cột actions
//        actionColumn.setCellFactory(cellFactory);
//        column1.getStyleClass().add("table-student-id-cell");
//        column2.getStyleClass().add("table-student-username-cell");
//        column3.getStyleClass().add("table-student-name-cell");
//        column4.getStyleClass().add("table-student-class-cell");
//        bookTable.getColumns().addAll(column1, column2, column3, column4, actionColumn);
//        suggestionList.setVisible(false); // Ẩn gợi ý ban đầu
//        input.setOnKeyTyped(this::onKeyTyped); // Lắng nghe sự kiện khi gõ/-strong/-heart:>:o:-((:-h suggestionList.setOnMouseClicked(event -> {
//        if (event.getClickCount() == 1) { // Single click
//            input.setText(suggestionList.getSelectionModel().getSelectedItem());
//            System.out.println("Clicked: " + input.getText());
//            suggestionList.setVisible(false);
//            searchBook();
//        }
//    });
//}
//
//private void onKeyTyped(KeyEvent event) {
//    String searchText = input.getText().trim();
////        System.out.println(searchText);
//    if (searchText.isEmpty()) {
//        suggestionList.setVisible(false); // Ẩn khi không có từ khóa
//        return;
//    }
//    // Tạo một Task để lấy các gợi ý không đồng bộ
//    Task<List<String>> task = new Task<>() {
//        @Override
//        protected List<String> call() throws Exception {
//            return getSearchSuggestions(searchText); // Lấy gợi ý từ cơ sở dữ liệu
//        }
//    };
//
//    // Khi tìm kiếm hoàn thành, cập nhật danh sách gợi ý
//    task.setOnSucceeded(e -> {
//        List<String> suggestions = task.getValue();
//        suggestionList.setItems(FXCollections.observableArrayList(suggestions));
//        suggestionList.setVisible(!suggestions.isEmpty()); // Hiển thị nếu có gợi ý
//    });
//
//    // Nếu có lỗi
//    task.setOnFailed(e -> task.getException().printStackTrace());
//
//    // Chạy Task trong một luồng riêng
//    new Thread(task).start();
//}
//
//private List<String> getSearchSuggestions(String searchText) {
//    List<String> suggestions = new ArrayList<>();
//    List<Book> cur=  Book.getBook(searchText);
//    for (Book x:cur) {
//        if (x.getTitle().toLowerCase().contains(searchText.toLowerCase())) {
//            suggestions.add(x.getTitle());}
//        if (x.getTitle().toLowerCase().contains(searchText.toLowerCase())) {
//            suggestions.add(x.getISBN());}
//        if (x.getTitle().toLowerCase().contains(searchText.toLowerCase())) {
//            suggestions.add(x.getAuthor());}
//        if (x.getTitle().toLowerCase().contains(searchText.toLowerCase())) {
//            suggestions.add(x.getPublisher());}
//        if (x.getTitle().toLowerCase().contains(searchText.toLowerCase())) {
//            suggestions.add(x.getPublishYear());}
//    }
//    return suggestions;
//}
//@FXML
//public void searchBook() {
//    List<Book> result = Book.getBook(input.getText());
//    bookTable.setItems(FXCollections.observableArrayList());
//    toggleTableViewVisibility(bookTable, true);
////        System.out.println(count++);
//    for (Book x:result) {
////            root1.getChildren().add(new TreeItem<>(x));
//        bookTable.getItems().add(x);
//    }
//}
//public void toggleTableViewVisibility(TableView<?> tableView, boolean isVisible) {
////        boolean isVisible = tableView.isVisible();
//    tableView.setVisible(isVisible);/-strong/-heart:>:o:-((:-h tableView.setManaged(isVisible);
//}
//public void detailBook(Book cur, ActionEvent event) {
//    bookTable.setItems(FXCollections.observableArrayList());
//    System.out.println(event.getTarget());
//    onClickBook = cur;
//    displayScene(event,"DetailBook.fxml");
//}
//
//public void createBook(ActionEvent event) {
//    displayScene(event,"CreateStudent.fxml");
//}
//}
