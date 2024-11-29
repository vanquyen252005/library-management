package com.example.demo.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class UpdateStudentController extends menucontroller {

    @FXML
    public void updateStudent(ActionEvent event) {
        super.initialize();
        super.manageStudent.getStyleClass().add("selected");
    }
}
