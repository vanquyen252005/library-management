package com.example.demo.student;

import com.example.demo.MainApplication;
import com.example.demo.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MenuController extends MainController {

    public Button back;
    @FXML
    protected AnchorPane contentArea;
    @FXML
    protected Pane menuPane;
    @FXML
    protected Button homeButton;
    @FXML
    protected Button profileButton;
    @FXML
    protected Button returnBookButton;
    @FXML
    protected Button logOut;
    @FXML
    protected Button notificationButton;



    public void initialize() {
        homeButton.setOnAction(event -> {home(event);});
        profileButton.setOnAction(event -> {Profile(event);});
        returnBookButton.setOnAction(event -> {handleRequest(event);});
        back.setOnAction(event -> {handleBack(event);});
    }
    @FXML
    protected void Profile(ActionEvent event) {
        displayScene(MainApplication.getPrimaryStage(), "student/Profile.fxml");
    }
    @FXML
    protected void handleRequest(ActionEvent event) {
        displayScene(MainApplication.getPrimaryStage(), "student/HandleRequest.fxml");
    }
    @FXML
    protected void home(ActionEvent event) {
        displayScene(MainApplication.getPrimaryStage(), "student/Home.fxml");
    }
    @FXML
    protected void notification(ActionEvent event) {
        displayScene(MainApplication.getPrimaryStage(), "student/notification.fxml");
    }

    public void handleBack(ActionEvent event) {
        controller.undo();
    }

    @FXML
    public void logout(ActionEvent event) {
        user1 = null;
        MainController.writeUser(null,"log.txt");
        Stage stage = MainApplication.getPrimaryStage();
        MainController.displayScene(stage, "Main.fxml");
    }
}
