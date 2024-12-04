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
import com.example.demo.book.Database;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import com.example.demo.book.Book;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class homeController extends menuController {

    private static final int BOOKS_PER_ROW = 4;  // Mỗi hàng có 4 sách
    private static final int BOOKS_PER_PAGE = 32;  // Mỗi lần tải 12 sách
    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    @FXML
    private Button search_button;
    @FXML
    private Label labelSearchResult;
    @FXML
    private TextField search_field;
    @FXML
    private Button clear_button;
    private Database bookDatabase = Database.getInstance();
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private ListView<String> suggestionList;


    private GridPane bookGridPane = new GridPane();
    @FXML
    private VBox preView;
    private List<BookCoverProxy> bookList; // Danh sách sách từ cơ sở dữ liệu
    private static List<BookCoverProxy> cachedBookList; // Lưu danh sách sách được tải lần đầu
    private static boolean isBooksDisplayed = false; // Kiểm tra xem sách đã được hiển thị hay chưa
    private int currentDisplayedBooks = 32; // Số sách hiện tại đã hiển thị


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


    @FXML
    public void initialize() {
        bookGridPane.setHgap(50);
        bookGridPane.setVgap(30);
        scrollPane.setContent(bookGridPane);
        bookGridPane.setAlignment(Pos.CENTER);
        scrollPane.vvalueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.doubleValue() >= 0.7) {
                loadMoreBooks();
            }
        });
        suggestionList.setVisible(false);

        if (cachedBookList != null && isBooksDisplayed) {
            // Nếu sách đã được hiển thị trước đó, chỉ cần hiển thị lại
            bookList = cachedBookList;
            currentDisplayedBooks = Math.min(BOOKS_PER_PAGE, bookList.size());
            displayBooks();
        } else {
            // Tải danh sách sách từ cơ sở dữ liệu lần đầu sử dụng Singleton BookDAO
            loadBooksAsync();
        }

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

    private void loadBooksAsync() {
        executorService.submit(() -> {
            try {

                List<Book> bookData = bookDatabase.searchDocuments(search_field.getText());
                List<BookCoverProxy> bookCoverProxyList = new ArrayList<>();

                for(Book buk : bookData) {
                    bookCoverProxyList.add(new BookCoverProxy(
                            buk.getISBN(),
                            buk.getTitle(),
                            buk.getImage()
                    ));
                }

                cachedBookList = bookCoverProxyList;
                bookList = cachedBookList;
                currentDisplayedBooks = Math.min(BOOKS_PER_PAGE, bookList.size());

                Platform.runLater(() -> {
                    displayBooks();
                    isBooksDisplayed = true;
                });
            } catch (Exception e) {
                System.out.println("Lỗi khi tải sách");
            }
        });
    }

    private void loadMoreBooks() {
        System.out.println("Đang tải thêm sách...");

        executorService.submit(() -> {
            if (bookList != null && currentDisplayedBooks < bookList.size()) {
                currentDisplayedBooks = Math.min(currentDisplayedBooks + BOOKS_PER_PAGE, bookList.size());

                Platform.runLater(() -> displayBooks());
            }
        });
    }

    private void showAlert(String title, String message) {
        // Tạo một Alert kiểu thông báo
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);  // Tiêu đề của Alert
        alert.setHeaderText(null);  // Không có header
        alert.setContentText(message);  // Nội dung của thông báo
        alert.showAndWait();  // Hiển thị thông báo và đợi người dùng đóng
    }


    private void displayBooks() {
        if (bookList == null || bookList.isEmpty()) {
            System.out.println("display no book");
            return;
        }
        System.out.println("Số lượng sách hiển thị: " + currentDisplayedBooks);

        int columns = 4; // Số cột hiển thị
        int row = bookGridPane.getRowCount(); // Bắt đầu từ hàng hiện tại
        int col = 0; // Bắt đầu từ cột đầu tiên

        if(row ==0) {
            currentDisplayedBooks = 0;
        }
        // Hiển thị sách từ currentDisplayedBooks đã hiển thị
        for (int i = currentDisplayedBooks; i < Math.min(currentDisplayedBooks + BOOKS_PER_PAGE, bookList.size()); i++) {
            BookCoverProxy bookProxy = bookList.get(i);
            ImageView imageView = new ImageView();

            // Tải hình ảnh sách trong một luồng riêng biệt
            int finalCol = col;
            int finalRow = row;

            executorService.submit(() -> {
                bookProxy.display(imageView); // Tải hình ảnh
                Platform.runLater(() -> {
                    imageView.setOnMouseClicked(event -> openDetailBook(bookProxy.getISBN()));
                    VBox temVBox = new VBox();
                    Label titleLabel = new Label();
                    titleLabel.setText(bookProxy.getTitle());
                    titleLabel.setMaxWidth(160);
                    temVBox.getChildren().addAll(imageView, titleLabel);
                    bookGridPane.add(temVBox, finalCol, finalRow);

                });
            });

            col++;
            if (col >= columns) { // Nếu đã đủ 4 ảnh trên 1 hàng
                col = 0;
                row++;
            }
        }

        currentDisplayedBooks = Math.min(bookList.size(), currentDisplayedBooks + BOOKS_PER_PAGE);
    }

    private void openDetailBook(String ISBN) {
        BookDetailController.ISBN = ISBN;
        displayScene(HelloApplication.getPrimaryStage(), "book/BookDetailView.fxml");
    }

    @FXML
    public void clickOKButton(ActionEvent e) {
        bookGridPane.getChildren().clear();
        currentDisplayedBooks = 0;
        loadBooksAsync();
        suggestionList.setVisible(false);
    }

    @FXML
    public void clickClearButton(ActionEvent e) {
        search_field.clear();
    }

    public void logOut(ActionEvent event) {

    }
}
