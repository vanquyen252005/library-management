package com.example.demo.admin;

import com.example.demo.student.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
    @FXML
    public void addStudent(ActionEvent event) {
        Student newStudent = new Student(//id.getText(),
                username.getText(),
                Password.getText(),
                name.getText(),
                "Student",
                phone.getText(),
                Class.getText());
        boolean flag =  Student.addStudent(newStudent);
        if (flag == true) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add Student");
            alert.setHeaderText("Add Student");
            alert.setContentText("Add Student '"
                    + newStudent.getName() + "' successfully!"
            );

            // Hiển thị alert
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add Student");
            alert.setHeaderText("Add Student");
            alert.setContentText("Add Student '"
                    +  newStudent.getName()  + "' failed!"
            );

            // Hiển thị alert
            alert.showAndWait();
        }
        controller.undo();
    }
}
