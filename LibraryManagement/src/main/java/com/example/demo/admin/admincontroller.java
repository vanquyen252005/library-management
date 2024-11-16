package com.example.demo.admin;

import com.example.demo.HelloController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class admincontroller extends HelloController {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Label loginFailLabel;
    protected static admin user = new admin();

    public void login(ActionEvent event) {
        //System.out.println(username);
        if (user.login(username.getText(), password.getText())) {
            displayScene(event, "menu.fxml");
        }
        else {
            loginFailLabel.setVisible(true);
//            displayScene(event, "Admin/StudentLogin.fxml");
//            System.out.println("nah");
        }
    }
}
