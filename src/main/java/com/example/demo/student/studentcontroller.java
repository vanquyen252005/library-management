package com.example.demo.student;

import com.example.demo.HelloController;
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
    @FXML
    private Button registerRoute;
    protected static Student user = new Student();


    public void login(ActionEvent event) {
        if (user.login(username.getText(), password.getText())) {
            displayScene(event, "menu.fxml");
        }
        else {
            loginFailLabel.setVisible(true);

        }
    }

    public void Profile(ActionEvent event) {
        System.out.println("truy cap thanh cong ProfileController");
        displayScene(event,"Profile.fxml");
    }


    public void register(ActionEvent event) {
        displayScene(event, "StudentRegister.fxml");
    }

    public void handlerequest(ActionEvent event) {
        displayScene(event,"HandleRequest.fxml");
    }

    public void home(ActionEvent event) {
        displayScene(event, "home.fxml");
    }
}