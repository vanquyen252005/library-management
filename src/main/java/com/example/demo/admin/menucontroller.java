package com.example.demo.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class menucontroller extends admincontroller {
    @FXML
    protected Button home;
    @FXML
    protected Button manageStudent;
    @FXML
    protected Button manageBook;
    @FXML
    protected Button search;
    @FXML
    protected Button handleRequest;

    @FXML
    public void initialize() {
        // Thiết lập ActionListener cho các nút
        home.setOnAction(event -> handleHomeAction(event));
        manageStudent.setOnAction(event -> handleManageStudentAction(event));
        manageBook.setOnAction(event -> handleManageBookAction(event));
        search.setOnAction(event -> handleSearchAction(event));
        handleRequest.setOnAction(event -> handleHandleRequestAction(event));
    }

    public void handleHomeAction(ActionEvent event) {
        displayScene(event, "menu.fxml");
    }

    public void handleManageStudentAction(ActionEvent event) {
        displayScene(event, "ManageStudent.fxml");
    }

    public void handleManageBookAction(ActionEvent event) {
        displayScene(event, "ManageBook.fxml");
    }

    public void handleSearchAction(ActionEvent event) {
        displayScene(event, "menu.fxml");
    }

    public void handleHandleRequestAction(ActionEvent event) {
        displayScene(event, "menu.fxml");
    }

}
