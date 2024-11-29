package com.example.demo.admin;

import com.example.demo.book.RequestBook;
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
    }
    @FXML
    public void addBook(ActionEvent event) {
        RequestBook newBook = new RequestBook(isbn.getText()
                , title.getText()
                , author.getText()
        , publisyear.getText()
        , publisyear.getText()
        , imgUrl.getText());
        RequestBook.addBook(newBook);
    }
}
