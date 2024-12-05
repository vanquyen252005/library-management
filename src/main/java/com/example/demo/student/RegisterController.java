package com.example.demo.student;

import com.example.demo.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RegisterController extends MainController {
    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmField;

    @FXML
    private TextField classNameField;

    // Label declarations
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
    @FXML
    private Button Back;

    String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    String passwordRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    protected static Student user = new Student();
    private static Jdbc Database = Jdbc.getInstance();
    private boolean ableToRegister = true;

    @FXML
    public void clickRegisterButton(ActionEvent event) {

        String email = emailField.getText();
        if (!email.matches(emailRegex)) {
            showErrorAlert("Invalid email address", "Please enter a valid email address.");
            return;
        }

        String password = passwordField.getText();
        if (!password.matches(passwordRegex)) {
            showErrorAlert("Invalid password",
                    "Password must be at least 8 characters long and include uppercase letters, lowercase letters, numbers, and special characters.");
            return;
        }

        String confirmPassword = confirmField.getText();
        if (!password.equals(confirmPassword)) {
            showErrorAlert("Passwords do not match", "You must confirm the correct password.");
            return;
        }

        if (nameField.getText().isEmpty() || phoneField.getText().isEmpty() || classNameField.getText().isEmpty()) {
            showErrorAlert("Incomplete information", "Please fill in all required fields.");
            return;
        }

        Student newStudent = new Student(
                usernameField.getText(),
                passwordField.getText(),
                nameField.getText(),
                "student",
                phoneField.getText(),
                classNameField.getText());
        Student.addStudent(newStudent);
        displayScene(event,"StudentLogin.fxml");
    }

    private void showErrorAlert(String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Registration Error");
        alert.setHeaderText(headerText);

        TextArea textArea = new TextArea(contentText);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxHeight(200);
        textArea.setMinWidth(300);
        alert.getDialogPane().setContent(textArea);
        alert.showAndWait();
    }


    public void backLoginForm(ActionEvent event) {
        displayScene(event,"StudentLogin.fxml");
    }
}