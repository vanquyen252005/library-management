//package com.example.demo.student;
//
//import com.example.demo.DesignPattern.Proxy.BookProxy;
//import com.example.demo.DesignPattern.Proxy.Interface.BookCover;
//import com.example.demo.DesignPattern.Proxy.VirtualProxy.BookCoverProxy;
//import com.example.demo.HelloApplication;
//import com.example.demo.book.BookDetailController;
//import com.example.demo.book.ConnectDB;
//import com.example.demo.book.Database;
//import javafx.application.Platform;
//import javafx.concurrent.Task;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.TextField;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.scene.control.ScrollPane;
//import com.example.demo.book.Book;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class homeController extends menuController {
//
//    private static final int BOOKS_PER_ROW = 4;  // Mỗi hàng có 4 sách
//    private static final int BOOKS_PER_PAGE = 20;  // Mỗi lần tải 12 sách
//
//    @FXML
//    private Button search_button;
//    @FXML
//    private Label labelSearchResult;
//    @FXML
//    private TextField search_field;
//    @FXML
//    private Button clear_button;
//
//    private BookProxy bookProxy = new BookProxy();// Sử dụng Proxy
//    //private GridPane gridPane = new GridPane();
//
//
//    @FXML
//    private ScrollPane scrollPane;  // ScrollPane để chứa GridPane
//
//    private int currentPage = 1;
//    private ConnectDB bookDatabase = new ConnectDB();
//    private Map<Integer, List<Book>> cache = new HashMap<>();
//    VBox vbox = new VBox();
//
//    // Trang hiện tại, bắt đầu từ 1
//
//    @FXML
//    public void initialize() {
//        scrollPane.setContent(vbox);
//        loadTopRatedBooks("");
//        scrollPane.setOnScroll(event -> {
//            System.out.println("Scroll value: " + scrollPane.getVvalue());
//            if (scrollPane.getVvalue() >= 0.7) {
//                 // If the scroll is near the bottom
//                currentPage++;  // Increase the page
//                loadTopRatedBooks(search_field.getText());  // Load more books
//                scrollPane.setVvalue(0.2);
//            } else if(scrollPane.getVvalue() == 0.0 && currentPage > 0) {
//                currentPage--;
//                loadTopRatedBooks(search_field.getText());  // Load more books
//                scrollPane.setVvalue(0.7);
//            }
//        });// Load sách khi khởi động
//    }
//
//    // Phương thức tải sách từ proxy và cập nhật UI
//    private void loadTopRatedBooks(String keyword) {
////        List<Book> books = bookDatabase.searchDocuments(keyword);
////        List<BookCover> bookCoverList = new ArrayList<>();
////        for(int i=(currentPage-1)*BOOKS_PER_ROW; i<(currentPage-1)*BOOKS_PER_ROW+ BOOKS_PER_PAGE; i++) {
////            Book curBook = books.get(i);
////            bookCoverList.add(new BookCoverProxy(curBook.getTitle(), curBook.getImage()));
////        }
////        updateUIWithBooks(bookCoverList, keyword);
//        List<Book> books;
//
//        // Kiểm tra xem trang hiện tại đã có trong cache chưa
//        if (cache.containsKey(currentPage)) {
//            books = cache.get(currentPage);  // Lấy sách từ cache
//            System.out.println("Returning books from cache for page: " + currentPage);
//        } else {
//            // Nếu chưa có trong cache, tải sách từ cơ sở dữ liệu
//            books = bookDatabase.searchDocuments(keyword);
//            // Lưu kết quả vào cache
//            cache.put(currentPage, books);
//            System.out.println("Fetching books from database for page: " + currentPage);
//        }
//
//        List<BookCover> bookCoverList = new ArrayList<>();
//        for(int i=(currentPage-1)*BOOKS_PER_ROW; i<Math.min((currentPage-1)*BOOKS_PER_ROW + BOOKS_PER_PAGE, 1000); i++) {
//            Book curBook = books.get(i);
//            bookCoverList.add(new BookCoverProxy(curBook.getISBN(),curBook.getTitle(), curBook.getImage()));
//        }
//        System.out.println("-----size cua cover list" + bookCoverList.size());
//
//        updateUIWithBooks(bookCoverList, keyword);
//    }
//
//    private void openDetailBook(String ISBN) {
//        BookDetailController.ISBN = ISBN;
//        displayScene(HelloApplication.getPrimaryStage(),"book/BookDetailView.fxml");
//    }
//
//
//    private void updateUIWithBooks(List<BookCover> books, String keyword) {
//        if (keyword.isEmpty()) {
//            labelSearchResult.setText("Top rated books for you");
//        } else if (!books.isEmpty()) {
//            labelSearchResult.setText("Searched books for: " + keyword);
//        } else {
//            labelSearchResult.setText("No books found with your query");
//        }
//
//
//
//        // Clear existing content before adding new content
//        GridPane gridPane = new GridPane();
//        gridPane.getChildren().clear();
//        gridPane.setHgap(10);  // Horizontal gap between book covers
//        gridPane.setVgap(10);
//        gridPane.setPrefHeight(300);
//        gridPane.setPrefWidth(300);  // Set a preferred width for the GridPane
//
//        if (scrollPane.getContent() == null) {
//            scrollPane.setContent(gridPane);  // Set GridPane to ScrollPane
//        }
//
//        // Add book covers to the GridPane
//        int row = gridPane.getRowCount();  // Get current row count in GridPane
//        for (int i = 0; i < books.size(); i++) {
//            BookCover book = books.get(i);
//
//            // Create ImageView for book cover
//            ImageView imageView = new ImageView();
//            book.display(imageView);
//
//            imageView.setFitWidth(100);  // Set width for the image
//            imageView.setFitHeight(150);  // Optionally, set height for the image
//            imageView.setPreserveRatio(true);  // Keep aspect ratio of the image
//            imageView.setOnMouseClicked(event -> openDetailBook(book.getISBN()));
//
//            // Add book cover to GridPane at the appropriate position
//            int column = (i + row * BOOKS_PER_ROW) % BOOKS_PER_ROW;
//            int newRow = (i + row * BOOKS_PER_ROW) / BOOKS_PER_ROW;
//            gridPane.add(imageView, column, newRow);  // Add ImageView to GridPane
//        }
//
//        // Use Platform.runLater to ensure layout is completed
//        Platform.runLater(() -> {
//            System.out.println("GridPane width: " + gridPane.getWidth());
//            System.out.println("GridPane height: " + gridPane.getHeight());
//            System.out.println("ScrollPane width: " + scrollPane.getWidth());
//            System.out.println("ScrollPane height: " + scrollPane.getHeight());
//        });
//
//        vbox.getChildren().clear();
//        vbox.getChildren().addAll(gridPane);
//
//        // Log size of scroll pane and grid pane after layout is completed
//    }
//
//
//
//
//    @FXML
//    public void clickOKButton(ActionEvent e) {
//        //scrollPane.getContent().getChildren().clear();  // Xóa bìa sách cũ
//        currentPage = 1;  // Đặt lại trang khi tìm kiếm mới
//        loadTopRatedBooks(search_field.getText());  // Tải lại danh sách sách khi tìm kiếm
//    }
//
//    @FXML
//    public void clickClearButton(ActionEvent e) {
//        search_field.clear();
//    }
//}
package com.example.demo.student;

import com.example.demo.DesignPattern.Proxy.Interface.BookCover;
import com.example.demo.DesignPattern.Proxy.VirtualProxy.BookCoverProxy;
import com.example.demo.HelloApplication;
import com.example.demo.book.BookDetailController;
import com.example.demo.book.ConnectDB;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import com.example.demo.book.Book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class homeController extends menuController {

    private static final int BOOKS_PER_ROW = 4;  // Mỗi hàng có 4 sách
    private static final int BOOKS_PER_PAGE = 20;  // Mỗi lần tải 12 sách

    @FXML
    private Button search_button;
    @FXML
    private Label labelSearchResult;
    @FXML
    private TextField search_field;
    @FXML
    private Button clear_button;

    private int currentPage = 1;
    private ConnectDB bookDatabase = ConnectDB.getInstance();
    private Map<String, List<Book>> cache = new HashMap<>();

    private VBox vbox = new VBox();

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private ListView<String> suggestionList;
    // ScrollPane để chứa GridPane

    @FXML
    public void initialize() {
        scrollPane.setContent(vbox);
        loadTopRatedBooks("");  // Initial load with empty search
        scrollPane.setOnScroll(event -> {
            if (scrollPane.getVvalue() >= 0.7) {
                currentPage++;  // Increase the page
                loadTopRatedBooks(search_field.getText());  // Load more books
                scrollPane.setVvalue(0.2);
            } else if (scrollPane.getVvalue() == 0.0 && currentPage > 0) {
                currentPage--;
                loadTopRatedBooks(search_field.getText());  // Load more books
                scrollPane.setVvalue(0.7);
            }
        });

        suggestionList.setVisible(false);
        search_field.setOnKeyTyped(this::onKeyTyped);
        // Handle mouse click event to detect click count
        search_field.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 1) {
                String selectedSuggestion = suggestionList.getSelectionModel().getSelectedItem();
                search_field.setText(selectedSuggestion);
                System.out.println("Clicked: " + search_field.getText());
                suggestionList.setVisible(false);
            }
        });
        suggestionList.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 1) { // Single click on the ListView item
                String selectedSuggestion = suggestionList.getSelectionModel().getSelectedItem();
                if (selectedSuggestion != null) {
                    search_field.setText(selectedSuggestion); // Update search field with selected suggestion
                    System.out.println("Selected Suggestion: " + selectedSuggestion);
                    suggestionList.setVisible(false); // Hide suggestion list
                    clickOKButton(null); // Trigger the search action
                }
            }
        });
    }

    private void onKeyTyped(KeyEvent event) {
        String searchText = search_field.getText().trim();
//        System.out.println(searchText);
        if (searchText.isEmpty()) {
            suggestionList.setVisible(false); // Ẩn khi không có từ khóa
            return;
        }
        // Tạo một Task để lấy các gợi ý không đồng bộ
        Task<List<String>> task = new Task<>() {
            @Override
            protected List<String> call() throws Exception {
                return getSearchSuggestions(searchText); // Lấy gợi ý từ cơ sở dữ liệu
            }
        };

        // Khi tìm kiếm hoàn thành, cập nhật danh sách gợi ý
        task.setOnSucceeded(e -> Platform.runLater(() -> {
            List<String> suggestions = task.getValue();
            suggestionList.setItems(FXCollections.observableArrayList(suggestions));
            suggestionList.setVisible(!suggestions.isEmpty());
        }));


        // Nếu có lỗi
        task.setOnFailed(e -> task.getException().printStackTrace());

        // Chạy Task trong một luồng riêng
        new Thread(task).start();
    }

    private List<String> getSearchSuggestions(String searchText) {
        List<String> suggestions = new ArrayList<>();
        List<Book> cur=  bookDatabase.searchDocuments(searchText);
        System.out.println("return search autocomplete: " + cur.size());
        for (Book x:cur) {
            if (x.getTitle().toLowerCase().contains(searchText.toLowerCase())) {
                suggestions.add(x.getTitle());
            }
        }
        return suggestions;
    }

    public boolean cacheCheckKeyword(String keyword) {
        String cacheKey = generateCacheKey(currentPage, keyword);
        return cache.containsKey(cacheKey);
    }

    private String generateCacheKey(int currentPage, String keyword) {
        return currentPage + "-" + keyword.toLowerCase();
    }



    // Phương thức tải sách từ proxy và cập nhật UI (now using Task for threading)
    private void loadTopRatedBooks(String keyword) {
        Task<List<BookCover>> task = new Task<>() {
            @Override
            protected List<BookCover> call() throws Exception {
                List<Book> books;
                String cacheKey = generateCacheKey(currentPage, keyword);

                // Kiểm tra xem trang hiện tại đã có trong cache chưa
                if (cache.containsKey(cacheKey)) {
                    System.out.println("cache memory with " + keyword);
                    books = cache.get(cacheKey);  // Lấy sách từ cache
                } else {
                    // Nếu chưa có trong cache, tải sách từ cơ sở dữ liệu
                    books = bookDatabase.searchDocuments(keyword);
                    cache.put(cacheKey, books);  // Lưu kết quả vào cache
                }

                List<BookCover> bookCoverList = new ArrayList<>();
                for (int i = (currentPage - 1) * BOOKS_PER_ROW; i < Math.min((currentPage - 1) * BOOKS_PER_ROW + BOOKS_PER_PAGE, books.size()); i++) {
                    Book curBook = books.get(i);
                    bookCoverList.add(new BookCoverProxy(curBook.getISBN(), curBook.getTitle(), curBook.getImage()));
                }

                return bookCoverList;
            }
        };

        task.setOnSucceeded(event -> {
            List<BookCover> bookCoverList = task.getValue();
            updateUIWithBooks(bookCoverList, keyword);
        });

        task.setOnFailed(event -> {
            // Handle any errors that might occur during the task
            Throwable exception = task.getException();
            System.out.println("Error loading books: " + exception.getMessage());
        });

        // Run the task in a background thread
        new Thread(task).start();
    }

    private void updateUIWithBooks(List<BookCover> books, String keyword) {
        if (keyword.isEmpty()) {
            labelSearchResult.setText("Top rated books for you");
        } else if (!books.isEmpty()) {
            labelSearchResult.setText("Searched books for: " + keyword);
        } else {
            labelSearchResult.setText("No books found with your query");
        }

        // Clear existing content before adding new content
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);  // Horizontal gap between book covers
        gridPane.setVgap(10);
        gridPane.setPrefHeight(300);
        gridPane.setPrefWidth(300);  // Set a preferred width for the GridPane

        // Add book covers to the GridPane
        for (int i = 0; i < books.size(); i++) {
            BookCover book = books.get(i);

            // Create ImageView for book cover
            ImageView imageView = new ImageView();
            book.display(imageView);

            imageView.setFitWidth(100);  // Set width for the image
            imageView.setFitHeight(150);  // Optionally, set height for the image
            imageView.setPreserveRatio(true);  // Keep aspect ratio of the image
            imageView.setOnMouseClicked(event -> openDetailBook(book.getISBN()));

            // Add book cover to GridPane at the appropriate position
            int column = i % BOOKS_PER_ROW;
            int row = i / BOOKS_PER_ROW;
            gridPane.add(imageView, column, row);
        }

        // Clear previous content and add new grid to VBox
        vbox.getChildren().clear();
        vbox.getChildren().add(gridPane);
    }

    private void openDetailBook(String ISBN) {
        BookDetailController.ISBN = ISBN;
        displayScene(HelloApplication.getPrimaryStage(), "book/BookDetailView.fxml");
    }

    @FXML
    public void clickOKButton(ActionEvent e) {
        currentPage = 1;  // Reset to the first page
        loadTopRatedBooks(search_field.getText());
        suggestionList.setVisible(false);

    }

    @FXML
    public void clickClearButton(ActionEvent e) {
        search_field.clear();
    }
}
