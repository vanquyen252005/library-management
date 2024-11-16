package com.example.demo.admin;

import com.example.demo.student.student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class createstudentcontroller extends menucontroller {
    @FXML
    private TextField name;
    @FXML
    private TextField username;
    @FXML
    private TextField Password;
    @FXML
    private TextField Class;
    @FXML
    private TextField phone;
    @FXML
    private TextField id;
    @FXML
    public void addStudent(ActionEvent event) {
        student newStudent = new student(id.getText(),
                username.getText(),
                Password.getText(),
                name.getText(),
                "student",
                phone.getText(),
                Class.getText());
        user.addStudent(newStudent);
    }
}
