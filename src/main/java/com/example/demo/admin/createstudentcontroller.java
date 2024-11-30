package com.example.demo.admin;

import com.example.demo.student.Student;
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
    public void initialize() {
        super.initialize();
        super.manageBook.getStyleClass().remove("selected");
        super.home.getStyleClass().remove("selected");
        super.manageStudent.getStyleClass().add("selected");
        super.handleRequest.getStyleClass().remove("selected");

    }
    @FXML
    public void addStudent(ActionEvent event) {
        Student newStudent = new Student(//id.getText(),
                username.getText(),
                Password.getText(),
                name.getText(),
                "Student",
                phone.getText(),
                Class.getText());
        Student.addStudent(newStudent);
    }
}
