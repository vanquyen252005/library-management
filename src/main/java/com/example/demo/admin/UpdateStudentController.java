package com.example.demo.admin;

import com.example.demo.student.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class UpdateStudentController extends MenuController {

    private Student curStudent = ManageStudentController.onClickStudent;
    @FXML
    TextField usernameField;
    @FXML
    TextField nameField;
    @FXML
    TextField phoneField;
    @FXML
    TextField classField;

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

        usernameField.setText(curStudent.getUsername());
        nameField.setText(curStudent.getName());
        phoneField.setText(curStudent.getPhone());
        classField.setText(curStudent.getClassName());
    }
    @FXML
    public void updateStudent(ActionEvent event) {


        boolean flag = curStudent.updateUserProfile(curStudent.getId()
                , usernameField.getText()
                , nameField.getText()
                , phoneField.getText()
                , classField.getText());
        if (flag) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Update Student");
            alert.setHeaderText("Update Student");
            alert.setContentText("Update student '"
                    + curStudent.getName() + "' successfully!"
            );

            // Hiển thị alert
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Update Student");
            alert.setHeaderText("Update Student");
            alert.setContentText("Update student '"
                    + curStudent.getName() + "' successfully!"
            );

            // Hiển thị alert
            alert.showAndWait();
        }
        controller.undo();
    }
}
