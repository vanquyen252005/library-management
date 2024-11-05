package com.example.demo.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class menucontroller extends admincontroller {
    @FXML
    private Button home;
    @FXML
    private Button manageStudent;
    @FXML
    private Button manageBook;
    @FXML
    private Button search;
    @FXML
    private Button handleRequest;

    @FXML
    public void initialize() {
        // Thiết lập ActionListener cho các nút
        home.setOnAction(event -> handleHomeAction(event));
        manageStudent.setOnAction(event -> handleManageStudentAction(event));
        manageBook.setOnAction(event -> handleManageBookAction(event));
        search.setOnAction(event -> handleSearchAction(event));
        handleRequest.setOnAction(event -> handleHandleRequestAction(event));
    }

    private void handleHomeAction(ActionEvent event) {
        displayScene(event, "menu.fxml");
    }

    private void handleManageStudentAction(ActionEvent event) {
        displayScene(event, "ManageStudent.fxml");
    }

    private void handleManageBookAction(ActionEvent event) {
        displayScene(event, "menu.fxml");
    }

    private void handleSearchAction(ActionEvent event) {
        displayScene(event, "menu.fxml");
    }

    private void handleHandleRequestAction(ActionEvent event) {
        displayScene(event, "menu.fxml");
    }

}