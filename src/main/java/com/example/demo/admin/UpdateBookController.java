package com.example.demo.admin;

import com.example.demo.book.Book;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class UpdateBookController extends MenuController {

    @FXML
    public TextField ISBNField;
    @FXML
    public TextField titleField;
    @FXML
    public TextField authorField;
    @FXML
    public TextField publishYearField;
    @FXML
    public TextField publisherField;
    @FXML
    public TextField imgUrl;
    Book curBook = ManageBookController.onClickBook;
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

        ISBNField.setText(curBook.getISBN());
        titleField.setText(curBook.getTitle());
        authorField.setText(curBook.getAuthor());
        publisherField.setText(curBook.getPublisher());
        publishYearField.setText(curBook.getPublishYear());;
        imgUrl.setText(curBook.getImage());
    }
    @FXML
    public void updateBook(ActionEvent event) {
        Book newBook = new Book(ISBNField.getText()
                , titleField.getText()
                , authorField.getText()
                , publisherField.getText()
                , publishYearField.getText()
                , imgUrl.getText());
        boolean flag = curBook.updateBook(newBook);
        if (flag) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Update Book");
            alert.setHeaderText("Update Book");
            alert.setContentText("Update book '"
                    + curBook.getTitle() + "' successfully!"
            );

            // Hiển thị alert
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Update Book");
            alert.setHeaderText("Update Book");
            alert.setContentText("Update book '"
                    + curBook.getTitle() + "' failed!"
            );

            // Hiển thị alert
            alert.showAndWait();
        }
    }

}
