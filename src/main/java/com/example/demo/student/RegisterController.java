package com.example.demo.student;

import com.example.demo.HelloController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.util.List;
import java.util.regex.Pattern;

public class RegisterController extends HelloController {
    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField retextField;

    @FXML
    private TextField classNameField;

    // Label declarations
    @FXML
    private Label emailConditionLabel;

    @FXML
    private Label phoneConditionLabel;

    @FXML
    private Label usernameConditionLabel;

    @FXML
    private Label nameConditionLabel;

    @FXML
    private Label passwordConditionLabel;

    @FXML
    private Label retextConditionLabel;

    @FXML
    private Label classNameConditionLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label retextLabel;

    @FXML
    private Label classNameLabel;

    @FXML
    private Button registerButton;

    String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    String passwordRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    protected static Student user = new Student();
    private static jdbc Database = new jdbc();
    private boolean ableToRegister = true;



    @FXML
    public void initialize() {
        emailConditionLabel.setText("required to fill out"); //1
        usernameConditionLabel.setText("required to fill out"); //2
        passwordConditionLabel.setText("required to fill out"); //3
        retextConditionLabel.setText("required to fill out"); //4
        nameConditionLabel.setText("required to fill out"); //5
        phoneConditionLabel.setText("not required");
        classNameConditionLabel.setText("not required");

        emailField.setOnKeyTyped(event -> UpdateConditionLabel(emailField, emailLabel ,1));
        usernameField.setOnKeyTyped(event -> UpdateConditionLabel(usernameField, usernameLabel,2));
        passwordField.setOnKeyTyped(event -> UpdateConditionLabel(passwordField, passwordLabel,3));
        retextField.setOnKeyTyped(event -> UpdateConditionLabel(retextField, retextLabel,4));
        nameField.setOnKeyTyped(event -> UpdateConditionLabel(nameField, nameLabel,5));


    }

    public void ifFieldNull(Label InspectLabel, TextField InspectField) {
        if (InspectField.getText().equals("")) {
            InspectLabel.setText("required to fill out");
        }
    }

    public void UpdateConditionLabel(TextField InspectField, Label InspectLabel , int conditionRoute) {
//        InspectField.setOnKeyTyped(event -> {
        ifFieldNull(InspectLabel, InspectField);
            String onField = InspectField.getText();

                 if (conditionRoute == 1) {
                boolean isValid = Pattern.matches(emailRegex, onField) && Database.CheckExistedEmail(emailField.getText());
                ableToRegister &= isValid;
                if(isValid) {
                    emailConditionLabel.setText("proper email");
                }
                else emailConditionLabel.setText("this email is not valid");
            }
            else if (conditionRoute == 2) {
                boolean isValid = Database.CheckExistedUsername(usernameField.getText());
                ableToRegister &= isValid;
                if(isValid) {
                    usernameConditionLabel.setText("proper username");
                }
                else usernameConditionLabel.setText("this username existed");
            }
            else if (conditionRoute == 3) {
                boolean isValid = Pattern.matches(passwordRegex, onField);
                ableToRegister &= isValid;
                if(isValid) {
                    passwordConditionLabel.setText("proper password");
                }
                else passwordConditionLabel.setText("this password is not valid");

            }
            else if (conditionRoute == 4) {
                boolean isValid = retextField.getText().equals(passwordField.getText());
                ableToRegister &= isValid;
                if (isValid) {
                   retextLabel.setText("correct password");
                }
                else retextLabel.setText("unmatched password");
            }
            else if (conditionRoute == 5) {
                boolean isValid = Database.CheckExistedName(nameField.getText());
                ableToRegister &= isValid;
                if(isValid) {
                    nameLabel.setText("proper name");
                }
                else nameLabel.setText("this name existed");
            }
    }

    @FXML
    public void clickRegisterButton(ActionEvent event) {
        if(ableToRegister) {
            Student newStudent = new Student(
                    usernameField.getText(),
                    passwordField.getText(),
                    nameField.getText(),
                    "student",
                    phoneField.getText(),
                    classNameField.getText());
            Student.addStudent(newStudent);
            displayScene(event,"student/StudentLogin.fxml");
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi Đăng Ký");
            alert.setHeaderText("Đăng ký thất bại");
            alert.setContentText("Vui lòng kiểm tra thông tin và thử lại.");
            alert.showAndWait();
        }
    }

    public void backLoginForm(ActionEvent event) {
        displayScene(event,"student/StudentLogin.fxml");
    }






}



















