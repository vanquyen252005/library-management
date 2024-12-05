package com.example.demo.admin;

import com.example.demo.book.Book;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ManageBookController extends MenuController {
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

    @Override
    @FXML
    public void initialize() {
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
                    setGraphic(null);
                } else {
                    try {
                        Book book = getTableView().getItems().get(getIndex());
                        MultiThread.getExecutorService().submit(() -> {
                            try {
                                ImageView imageView = book.loadImage();
                                imageView.setFitHeight(50);
                                imageView.setFitWidth(50);
                                imageView.setPreserveRatio(true);

                                Platform.runLater(() -> setGraphic(imageView));
                            } catch (Exception e) {
                                e.printStackTrace();
                                Platform.runLater(() -> setGraphic(null));
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        Platform.runLater(() -> setGraphic(null));
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
                    private final HBox hbox = new HBox(10);

                    {
                        deleteButton.getStyleClass().add("delete-button");
                        deleteButton.setOnAction(event -> {
                            Book book = getTableView().getItems().get(getIndex());
                            book.deleteBook();
                            searchBook();
                        });

                        detailButton.getStyleClass().add("detail-button");
                        detailButton.setOnAction(event -> {
                            Book book = getTableView().getItems().get(getIndex());
                            detailBook(book.getBook(), event);
                        });

                        updateButton.getStyleClass().add("button-update");
                        updateButton.setOnAction(event -> {
                            Book book = getTableView().getItems().get(getIndex());
                            updateBook(book.getBook(), event);
                        });

                        hbox.getChildren().addAll(deleteButton, detailButton, updateButton);
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(hbox);
                        }
                    }
                };
                cell.getStyleClass().add("table-book-detail-cell");
                return cell;
            }
        };
        column1.setCellFactory(param -> new TableCell<Book, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                getStyleClass().add("table-book-id-cell");
            }
        });
        column2.setCellFactory(param -> new TableCell<Book, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                getStyleClass().add("table-book-title-cell");
            }
        });
        column3.setCellFactory(param -> new TableCell<Book, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                getStyleClass().add("table-book-author-cell");
            }
        });
        column4.setCellFactory(param -> new TableCell<Book, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                getStyleClass().add("table-book-publisher-cell");
            }
        });
        // Đặt cellFactory cho cột actions
        actionColumn.setCellFactory(cellFactory);
        column1.getStyleClass().add("table-book-id-cell");
        column2.getStyleClass().add("table-book-title-cell");
        column3.getStyleClass().add("table-book-author-cell");
        column4.getStyleClass().add("table-book-publisher-cell");
        actionColumn.getStyleClass().add("table-book-detail-cell");
        bookTable.getColumns().addAll(column1, column2, column3, column4, actionColumn, imageColumn);
        suggestionList.setVisible(false);
        input.setOnKeyTyped(this::onKeyTyped);
        suggestionList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                input.setText(suggestionList.getSelectionModel().getSelectedItem());
                suggestionList.setVisible(false);
                searchBook();
            }
        });
        verticalScrollBar = (ScrollBar) bookTable.lookup(".scroll-bar:vertical");
        loadMoreBooks();

    }

    private void loadMoreBooks() {
        loadingSpinner.setVisible(true);

        Task<List<Book>> task = new Task<>() {
            @Override
            protected List<Book> call() {

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
            loadingSpinner.setVisible(false);
        });

        task.setOnFailed(e -> {
            task.getException().printStackTrace();
            loadingSpinner.setVisible(false);
        });

        new Thread(task).start();
    }

    private List<String> getSearchSuggestions(String searchText) {
        List<String> suggestionList = new ArrayList<>();
        List<Book> bookList = Book.getBookDB(searchText);
        List<String> resultList = new ArrayList<>();
        for (Book book : bookList) {
            if (book.getTitle().toLowerCase().contains(searchText.toLowerCase())) {
                resultList.add(book.getTitle());
            }
        }
            for (Book book : bookList) {
                if (book.getISBN().toLowerCase().contains(searchText.toLowerCase())) {
                    resultList.add(book.getISBN());
                }
            }
            for (Book book : bookList) {
                if (book.getAuthor().toLowerCase().contains(searchText.toLowerCase())) {
                    resultList.add(book.getAuthor());
                }
            }
            for (Book book : bookList) {
                if (book.getPublisher().toLowerCase().contains(searchText.toLowerCase())) {
                    resultList.add(book.getPublisher());
                }
            }
            for (Book book : bookList) {
                if (book.getPublishYear().toLowerCase().contains(searchText.toLowerCase())) {
                    resultList.add(book.getPublishYear());
                }
            }
        return resultList.stream().distinct().collect(Collectors.toList());
    }

    private void onKeyTyped(KeyEvent event) {
        String searchText = input.getText().trim();

        if (searchText.isEmpty()) {
            suggestionList.setVisible(false);
            return;
        }

        loadingSpinner.setVisible(true);

        Task<List<String>> task = new Task<>() {
            @Override
            protected List<String> call() throws Exception {
                return getSearchSuggestions(searchText);
            }
        };

        task.setOnSucceeded(e -> {
            List<String> suggestions = task.getValue();
            suggestionList.setItems(FXCollections.observableArrayList(suggestions));
            suggestionList.setVisible(!suggestions.isEmpty());

            loadingSpinner.setVisible(false);
        });

        task.setOnFailed(e -> {
            task.getException().printStackTrace();
            loadingSpinner.setVisible(false);
        });

        new Thread(task).start();
    }

    @FXML
    public void searchBook() {
        books.clear();
        bookTable.refresh();
        currentPage = 0;
        currentPage++;
        loadMoreBooks();
        Platform.runLater(() -> {
             verticalScrollBar = (ScrollBar) bookTable.lookup(".scroll-bar:vertical");
            if (verticalScrollBar != null) {
                System.out.println("vertical scrollbar in admin manage book is not null ");
                verticalScrollBar.valueProperty().addListener((obs, oldValue, newValue) -> {
                    if (newValue.doubleValue() == 1.0) {
                        currentPage++;
                        loadMoreBooks();
                    }
                });
            }
        });
    }

    public void toggleTableViewVisibility(TableView<?> tableView, boolean isVisible) {
        tableView.setVisible(isVisible);
        tableView.setManaged(isVisible);
    }

    public void detailBook(Book curBook, ActionEvent event) {
        bookTable.setItems(FXCollections.observableArrayList());
        System.out.println(event.getTarget());
        onClickBook = curBook;
        displayScene(event,"DetailBook.fxml");
    }

    public void updateBook(Book curBook, ActionEvent event) {
        bookTable.setItems(FXCollections.observableArrayList());
        System.out.println(event.getTarget());
        onClickBook = curBook;
        displayScene(event,"UpdateBook.fxml");
    }

    public void createBook(ActionEvent event) {
        displayScene(event,"CreateBook.fxml");
    }
}
