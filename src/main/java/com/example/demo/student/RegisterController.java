package com.example.demo.student;

import com.example.demo.HelloController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmtField;

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
    private static jdbc Database = jdbc.getInstance();
    private boolean ableToRegister = true;

    @FXML
    public void clickRegisterButton(ActionEvent event) {

        String email = emailField.getText();
        if (!email.matches(emailRegex)) {
            showErrorAlert("Địa chỉ email không hợp lệ", "Vui lòng nhập một địa chỉ email hợp lệ.");
            return;
        }

        String password = passwordField.getText();
        if (!password.matches(passwordRegex)) {
            showErrorAlert("Mật khẩu không hợp lệ",
                    "Mật khẩu phải có ít nhất 8 ký tự, bao gồm cả chữ hoa, chữ thường, số và ký tự đặc biệt.");
            return;
        }

        String confirmPassword = confirmtField.getText();
        if (!password.equals(confirmPassword)) {
            showErrorAlert("Mật khẩu không khớp", "Bạn cần xác nhận đúng mật khẩu");
            return;
        }

        if (nameField.getText().isEmpty() || phoneField.getText().isEmpty() || classNameField.getText().isEmpty()) {
            showErrorAlert("Thông tin không đầy đủ", "Vui lòng điền đầy đủ thông tin.");
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
        displayScene(event,"student/StudentLogin.fxml");
    }

    private void showErrorAlert(String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi Đăng Ký");
        alert.setHeaderText(headerText);

        // Tạo một TextArea thay vì Label
        TextArea textArea = new TextArea(contentText);
        textArea.setEditable(false);  // Không cho phép chỉnh sửa
        textArea.setWrapText(true);   // Cho phép gói dòng
        textArea.setMaxHeight(200);   // Hạn chế chiều cao của TextArea nếu cần
        textArea.setMinWidth(300);    // Cài đặt chiều rộng tối thiểu
        alert.getDialogPane().setContent(textArea);
        alert.showAndWait();
    }


    public void backLoginForm(ActionEvent event) {
        displayScene(event,"StudentLogin.fxml");
    }
}