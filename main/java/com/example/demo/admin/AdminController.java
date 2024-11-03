package com.example.demo.admin;

import com.example.demo.HelloController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class AdminController extends HelloController {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Label loginFailLabel;
    protected static Admin user = new Admin();

    public void login(ActionEvent event) {
        //System.out.println(username);
        if (user.login(username.getText(), password.getText())) {
            displayScene(event, "menu.fxml");
        }
        else {
            loginFailLabel.setVisible(true);
//            displayScene(event, "Admin/AdminLogin.fxml");
//            System.out.println("nah");
        }
    }
}
