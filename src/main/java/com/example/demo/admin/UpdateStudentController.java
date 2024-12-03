package com.example.demo.admin;

import com.example.demo.book.Book;
import com.example.demo.student.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.awt.*;

public class UpdateStudentController extends menucontroller {

    private Student curStudent = managestudentcontroller.onClickStudent;
    @FXML
    TextField username;
    @FXML
    TextField name;
    @FXML
    TextField phone;
    @FXML
    TextField Class;

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
//        System.out.println(curStudent.getUsername());
        username.setText(curStudent.getUsername());
        name.setText(curStudent.getName());
        phone.setText(curStudent.getPhone());
        Class.setText(curStudent.getClassname());
    }
    @FXML
    public void updateStudent(ActionEvent event) {


        boolean flag = curStudent.updateUserProfile(curStudent.getId()
                , username.getText()
                , name.getText()
                , phone.getText()
                , Class.getText());
        if (flag == true) {
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
