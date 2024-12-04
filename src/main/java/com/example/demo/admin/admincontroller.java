package com.example.demo.admin;

import com.example.demo.HelloController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class admincontroller extends HelloController {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Label loginFailLabel;
    public static admin user = new admin();

    @FXML
    @Override
    public void initialize() {
        super.initialize();
    }

    public void login(ActionEvent event) {
        //System.out.println(username);
       if (user.login(username.getText(), password.getText())) {
            HelloController.writeUser( user,"log.txt");
            displayScene(event, "Home.fxml");
        }
        else {
            loginFailLabel.setVisible(true);
//            displayScene(event, "Admin/StudentLogin.fxml");
//            System.out.println("nah");
        }
    }

    public void handleBack(ActionEvent event) {
        controller.undo();
    }
}
