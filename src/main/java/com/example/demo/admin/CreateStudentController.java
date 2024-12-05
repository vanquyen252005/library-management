package com.example.demo.admin;

import com.example.demo.student.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

/**
 * Controller class for creating and adding a new student.
 * This class handles the user input for student details and interacts with the Student class
 * to add the student to the system.
 */

public class CreateStudentController extends MenuController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField classField;
    @FXML
    private TextField phoneField;

    /**
     * Initializes the controller by adjusting the style classes for navigation buttons
     * to indicate the current active section.
     */
    @FXML
    public void initialize() {
        super.initialize();
        homeButton.getStyleClass().remove("selected");
        manageStudent.getStyleClass().remove("select");
        manageBook.getStyleClass().remove("selected");
        handleRequest.getStyleClass().remove("selected");

        homeButton.getStyleClass().remove("pre");
        manageStudent.getStyleClass().remove("pre");
        manageBook.getStyleClass().remove("pre");
        handleRequest.getStyleClass().remove("pre");

        homeButton.getStyleClass().remove("after");
        manageStudent.getStyleClass().remove("after");
        manageBook.getStyleClass().remove("after");
        handleRequest.getStyleClass().remove("after");

        homeButton.getStyleClass().add("pre");
        manageStudent.getStyleClass().add("selected");
        manageBook.getStyleClass().add("after");

    }

    /**
     * Handles the action of adding a new student to the system.
     * It collects the information from the input fields, creates a new Student object,
     * and attempts to add it to the database.
     *
     * @param event The action event triggered when the user submits the student details.
     */
    @FXML
    public void addStudent(ActionEvent event) {
        Student newStudent = new Student(//id.getText(),
                usernameField.getText(),
                passwordField.getText(),
                nameField.getText(),
                "student",
                phoneField.getText(),
                classField.getText());
        boolean flag =  Student.addStudent(newStudent);
        if (flag) {
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

            alert.showAndWait();
        }
        controller.undo();
    }
}
