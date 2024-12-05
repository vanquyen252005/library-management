package com.example.demo.admin;

import com.example.demo.book.Book;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

/**
 * Controller class for creating and adding a new book.
 * Handles the user input for book details and interacts with the Book class
 * to add the book to the system.
 */

public class CreateBookController extends MenuController {
    @FXML
    public TextField ISBN;
    @FXML
    public TextField Title;
    @FXML
    public TextField Author;
    @FXML
    public TextField publishYear;
    @FXML
    public TextField Publisher;
    @FXML
    public TextField imgUrl;

    /**
     * Initializes the controller by removing and adding specific style classes
     * for the navigation buttons to indicate the active section.
     */
    @Override
    @FXML
    public void initialize() {
        super.initialize();

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
        handleRequest.getStyleClass().add("after");
    }

    /**
     * Handles the action of adding a new book to the system.
     * It collects the information from the input fields, creates a new Book object,
     * and attempts to add it to the database.
     *
     * @param event The action event triggered when the user submits the book details.
     */
    @FXML
    public void addBook(ActionEvent event) {
        Book newBook = new Book(ISBN.getText()
                , Title.getText()
                , Author.getText()
                , publishYear.getText()
                , publishYear.getText()
                , imgUrl.getText());
        boolean flag = Book.addBook(newBook);
        if (flag) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add Book");
            alert.setHeaderText("Add Book");
            alert.setContentText("Add book '"
                    + newBook.getTitle() + "' successfully!"
            );

            // Hiển thị alert
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add Book");
            alert.setHeaderText("Add Book");
            alert.setContentText("Add book '"
                    + newBook.getTitle() + "' failed!"
            );

            alert.showAndWait();
        }
        controller.undo();
    }
}
