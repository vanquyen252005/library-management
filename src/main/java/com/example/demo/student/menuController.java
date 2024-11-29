package com.example.demo.student;

import com.example.demo.HelloApplication;
import com.example.demo.HelloController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class menuController extends HelloController {

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


    public AnchorPane getAnchorPane() {return this.contentArea;}

    public void loadContent(AnchorPane contentArea, String fxmlPath) {
        try {
            // Tải giao diện mới
            Node content = FXMLLoader.load(getClass().getResource(fxmlPath));
            contentArea.getChildren().clear(); // Xóa nội dung cũ
            contentArea.getChildren().add(content); // Thêm nội dung mới
            // Đặt neo để nội dung khớp với kích thước của contentArea
            AnchorPane.setTopAnchor(content, 0.0);
            AnchorPane.setBottomAnchor(content, 0.0);
            AnchorPane.setLeftAnchor(content, 0.0);
            AnchorPane.setRightAnchor(content, 0.0);
        } catch (IOException e) {
            System.out.println("Failed to load content: " + fxmlPath);
        }
    }

    public void initialize() {
        //loadContent(contentArea,"home.fxml");
    }

    @FXML
    protected void Profile(ActionEvent event) {
        displayScene(HelloApplication.getPrimaryStage(), "student/Profile.fxml");
    }
    @FXML
    protected void handleRequest(ActionEvent event) {
        displayScene(HelloApplication.getPrimaryStage(), "student/HandleRequest.fxml");
    }
    @FXML
    protected void home(ActionEvent event) {
        displayScene(HelloApplication.getPrimaryStage(), "student/home.fxml");
    }
}
