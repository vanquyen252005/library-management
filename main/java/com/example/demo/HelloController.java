package com.example.demo;

import com.example.demo.user.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HelloController{
    protected Stage stage;
    protected Scene scene;
    protected AnchorPane root;
    protected User user = null;
    @FXML
    protected void displayScene(ActionEvent event, String fxmlLink) {
        try {
            root = FXMLLoader.load(getClass().getResource(fxmlLink));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void AdminLogin(ActionEvent event) {
        displayScene(event,"Admin/AdminLogin.fxml");
    }
    @FXML
    protected void StudentLogin(ActionEvent event) {
        displayScene(event,"StudentLogin");
    }
}