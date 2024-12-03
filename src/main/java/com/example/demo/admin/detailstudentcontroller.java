package com.example.demo.admin;

import com.example.demo.book.Book_borrowed;
import com.example.demo.student.Student;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class detailstudentcontroller extends menucontroller {
    @FXML
    public Pane detailStudent;
    @FXML
    public TableView borrowingBook;
    @FXML
    public TableView requestBook1;
    @FXML
    public TreeView<String> miniBar;
    public Label name;
    public Label Class;
    public Label id;
    public Label phone;
    public Label username;
    @FXML
    private Student curStudent = managestudentcontroller.onClickStudent;

    @Override
    @FXML
    public void initialize() {
        super.initialize();
        {
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

            home.getStyleClass().add("pre");
            manageStudent.getStyleClass().add("selected");
            manageBook.getStyleClass().add("after");
        }
        name.setText(curStudent.getName());
        Class.setText(curStudent.getClassname());
        id.setText(curStudent.getId());
        phone.setText(curStudent.getPhone());
        username.setText(curStudent.getUsername());
        loadBook();
    }

    private void loadBook() {
        ArrayList<Book_borrowed> cur = curStudent.getBorrowingBook();
        for (Book_borrowed x:cur) {
            borrowingBook.getItems().add(x);
//            System.out.println(x.getTitle());
        }
    }

}
