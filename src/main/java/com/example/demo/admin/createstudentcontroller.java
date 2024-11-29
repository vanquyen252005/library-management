package com.example.demo.admin;

import com.example.demo.student.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;

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
        super.manageStudent.getStyleClass().add("selected");
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
