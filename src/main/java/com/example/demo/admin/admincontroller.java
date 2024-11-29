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
    public static admin user = null;

    @FXML
    @Override
    public void initialize() {
        super.initialize();
        user = new admin();
        if (user1 != null) {
            user = (admin)user1;
        }
    }

    public void login(ActionEvent event) {
        //System.out.println(username);
       if (user.login(username.getText(), password.getText())) {
//           user = (admin)user1;
            HelloController.writeAdmin( user,"log.txt");
            displayScene(event, "menu.fxml");
        }
        else {
            loginFailLabel.setVisible(true);
//            displayScene(event, "Admin/StudentLogin.fxml");
//            System.out.println("nah");
        }
    }
}
