package com.example.demo.admin;

import com.example.demo.student.Student;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class detailstudentcontroller extends menucontroller {
    @FXML
    public Pane detailStudent;
    @FXML
    public TableView borrowingBook;
    @FXML
    public TableView requestBook1;
    @FXML
    public Text name;
    @FXML
    public Text id;
    @FXML
    public Text Class;
    @FXML
    public Text phone;
    private Student cur = managestudentcontroller.onClickStudent;

    @Override
    public void initialize() {
        name.setText(cur.getName());
        id.setText(cur.getId());
        Class.setText(cur.getClassname());
        phone.setText(cur.getPhone());
        home.setOnAction(event -> handleHomeAction(event));
        manageStudent.setOnAction(event -> handleManageStudentAction(event));
        manageBook.setOnAction(event -> handleManageBookAction(event));
        search.setOnAction(event -> handleSearchAction(event));
        handleRequest.setOnAction(event -> handleHandleRequestAction(event));
    }
}
