package com.example.demo.admin;

import com.example.demo.HelloController;
import com.example.demo.user.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

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
