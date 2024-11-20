package com.example.demo.user;

import com.example.demo.HelloController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class LoginController extends HelloController {
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Label loginFailLabel;

    public LoginController() throws SQLException {
    }
}
