package com.example.demo.student;

import com.example.demo.HelloController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

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
    @FXML
    private AnchorPane contentArea;
    protected static Student user = new Student();

    //public AnchorPane getAnchorPane() {return this.contentArea;}

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


    public void login(ActionEvent event) {
        if (user.login(username.getText(), password.getText())) {
            displayScene(event, "home.fxml");


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



//    public void Profile(ActionEvent event) {
//        displayScene(event,"Profile.fxml");
//    }
//

//
//    public void handlerequest(ActionEvent event) {
//        displayScene(event,"HandleRequest.fxml");
//    }
//
//    public void home(ActionEvent event) {
//        loadContent(contentArea,"home.fxml");
//    }

}