package com.example.demo.student;

import com.example.demo.HelloApplication;
import com.example.demo.HelloController;
import com.example.demo.book.Book;
import com.example.demo.book.BookDetailController;
import com.example.demo.book.Database;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class homeController extends HelloController {

    public BookDetailController bookcontroller = new BookDetailController();
    private static final int BOOKS_PER_PAGE = 6;
    private int currentPage = 1;

    private Stage stage;
    private Scene homeScene; // Store the initial home scene

    @FXML
    private Button search_button;
    @FXML
    private Button clear_button;
    @FXML
    private Label labelSearchResult;
    @FXML
    private TextField search_field;
    @FXML
    private ListView<String> suggestionList = new ListView<>();

    @FXML
    private VBox resultsVBox;

    private Database BookDatabase = new Database();

    public void setStageScene(Stage stage, Scene scene) {
        this.stage = stage;
        this.homeScene = scene; // Store the initial scene as the home scene
    }

    @FXML
    public void initialize() {
        suggestionList.setVisible(false);
        loadTopRatedBooks("");// Load top-rated books on startup
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
        List<Book> cur=  BookDatabase.searchDocuments(searchText);
        System.out.println("return search autocomplete: " + cur.size());
        for (Book x:cur) {
            if (x.getTitle().toLowerCase().contains(searchText.toLowerCase())) {
                suggestions.add(x.getTitle());
            }
        }
        return suggestions;
    }

    @FXML

    public void clickOKButton(ActionEvent e) {
        String keyword = search_field.getText();
        loadTopRatedBooks(keyword);
    }

    public void clickClearButton(ActionEvent e) {
        search_field.clear();
    }

    private void openBookDetailView(String isbn) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("BookDetailView.fxml"));
//            AnchorPane root = loader.load();
//
//            bookcontroller = loader.getController();
//            bookcontroller.setISBN(isbn);
//            System.out.println("isbn của class main controller :" + isbn);
//            if(stage == null) System.out.println("stage is null");
//            if(homeScene == null) System.out.println("scene is null");
//            bookcontroller.setStage(stage); // Pass the main stage to BookDetailController
//            bookcontroller.setHomeScene(homeScene); // Pass the home scene to BookDetailController
//            bookcontroller.initialize();
//            Scene scene = new Scene(root);
//            stage.setScene(scene);
//            stage.show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        Stage stage = HelloApplication.getPrimaryStage();

        displayScene( stage,"BookDetailView.fxml");
        // bookcontroller.setISBN(isbn);
    }

    public void loadTopRatedBooks(String keyword) {
        // Clear any previous content
        resultsVBox.getChildren().clear();

        // Get the top-rated books (assuming the BookDatabase class has a method for this)
        List<Book> topRatedBooks = BookDatabase.searchDocuments(keyword);

        if (keyword.isEmpty()) {
            labelSearchResult.setText("Top rated books for you");
        }
        else if (!topRatedBooks.isEmpty()) labelSearchResult.setText("searched books for: " + keyword);
        else labelSearchResult.setText("No book found with your require");

        // Set up the grid
        GridPane gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(10);

        // Define column constraints (3 columns, each taking up 33.33% of the width)
        for (int i = 0; i < 3; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(33.33);
            gridPane.getColumnConstraints().add(column);
        }

        int displayedBooks = 0;

        for(int bookIndex=0 ;bookIndex<6 ;bookIndex++) {
            if (bookIndex < topRatedBooks.size()) {

                Book book = topRatedBooks.get(bookIndex);
                ImageView imageView = new ImageView();
                loadImage(imageView, book.getImage());

                imageView.setFitWidth(100);
                imageView.setPreserveRatio(true);
                imageView.setOnMouseClicked(event ->displayScene( stage,"BookDetailView.fxml"));

                Label titleLabel = new Label(book.getTitle());

                // Calculate the row and column in the grid
                int row = bookIndex / 3;
                int col = bookIndex % 3;

                // Add ImageView and Label to the grid at the calculated positions
                gridPane.add(imageView, col, row * 2);
                gridPane.add(titleLabel, col, row * 2 + 1);
            }
        }

        gridPane.setAlignment(Pos.CENTER);
        resultsVBox.setAlignment(Pos.CENTER);
        resultsVBox.getChildren().add(gridPane);

    }

    private void loadImage( ImageView imageView, String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream input = connection.getInputStream();
                Image image = new Image(input);
                imageView.setImage(image);
            } else {
                System.err.println("Error loading image: Server returned HTTP response code: " + responseCode);
            }
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
        }
    }
}

