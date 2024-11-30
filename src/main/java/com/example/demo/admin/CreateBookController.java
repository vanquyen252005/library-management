package com.example.demo.admin;

import com.example.demo.book.Book;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CreateBookController extends menucontroller {
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
    @Override
    @FXML
    public void initialize() {
        super.initialize();
        super.manageBook.getStyleClass().add("selected");
        super.home.getStyleClass().remove("selected");
        super.manageStudent.getStyleClass().remove("selected");
        super.handleRequest.getStyleClass().remove("selected");
    }
    @FXML
    public void addBook(ActionEvent event) {
        Book newBook = new Book(isbn.getText()
                , title.getText()
                , author.getText()
        , publisyear.getText()
        , publisyear.getText()
        , imgUrl.getText());
        Book.addBook(newBook);
    }
}
