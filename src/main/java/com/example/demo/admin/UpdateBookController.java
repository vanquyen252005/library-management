package com.example.demo.admin;

import com.example.demo.book.Book;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class UpdateBookController extends menucontroller {

    @FXML
    public TextField isbn;
    @FXML
    public TextField title;
    @FXML
    public TextField author;
    @FXML
    public TextField publisyear;
    @FXML
    public TextField publisher;
    @FXML
    public TextField imgUrl;
    Book curBook = managebookcontroller.onClickBook;
    @Override
    @FXML
    public void initialize() {
        super.initialize();
        home.getStyleClass().remove("selected");
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
        handleRequest.getStyleClass().add("after");

        isbn.setText(curBook.getISBN());
        title.setText(curBook.getTitle());
        author.setText(curBook.getAuthor());
        publisher.setText(curBook.getPublisher());
        publisyear.setText(curBook.getPublishYear());;
        imgUrl.setText(curBook.getImage());
    }
    @FXML
    public void updateBook(ActionEvent event) {
        Book newBook = new Book(isbn.getText()
                , title.getText()
                , author.getText()
                , publisher.getText()
                , publisyear.getText()
                , imgUrl.getText());
        boolean flag = curBook.updateBook(newBook);
        if (flag == true) {
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
