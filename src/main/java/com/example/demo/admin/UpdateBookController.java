package com.example.demo.admin;

import com.example.demo.book.RequestBook;
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
    RequestBook curBook = managebookcontroller.onClickBook;
    @Override
    @FXML
    public void initialize() {
        super.initialize();
        super.manageBook.getStyleClass().add("selected");
        isbn.setText(curBook.getISBN());
        title.setText(curBook.getTitle());
        author.setText(curBook.getAuthor());
        publisher.setText(curBook.getPublisher());
        publisyear.setText(curBook.getPublishYear());;
        imgUrl.setText(curBook.getImage());
    }
    @FXML
    public void updateBook(ActionEvent event) {
        RequestBook newBook = new RequestBook(isbn.getText()
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
