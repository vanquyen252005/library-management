package com.example.demo.student;

import com.example.demo.HelloController;
import com.example.demo.user.DisplayUserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class studentcontroller extends HelloController {
    public Button home;
    public Button Profile;
    public Button handleRequest;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Label loginFailLabel;
    protected static Student user = new Student();

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

    public void Profile(ActionEvent event) {
        displayScene(event,"Profile.fxml");
    }

    public void handlerequest(ActionEvent event) {
        displayScene(event,"HandleRequest.fxml");
        System.out.println("hehe");
    }
}
