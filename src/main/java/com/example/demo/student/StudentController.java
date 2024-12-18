package com.example.demo.student;

import com.example.demo.MainApplication;
import com.example.demo.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class StudentController extends MenuController {
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
    public static Student user = new Student();

    public static Student getStudent() {return user;}

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
        //loadContent(contentArea,"Home.fxml");
    }


    public void login(ActionEvent event) {
        if (user.loginAdmin(username.getText(), password.getText())) {
            MainController.writeUser( user,"log.txt");
            //displayScene(event, "Home.fxml");
            displayScene(MainApplication.getPrimaryStage(), "student/Home.fxml");
        }
        else {
            loginFailLabel.setVisible(true);
        }
    }

//    public void Profile(ActionEvent event) {
//        System.out.println("truy cap thanh cong ProfileController");
//        displayScene(event,"Profile.fxml");
//    }


    public void register(ActionEvent event) {

        displayScene(event, "StudentRegister.fxml");
    }

    public void handleBack(ActionEvent event) {
        displayScene(MainApplication.getPrimaryStage(),"Main.fxml");
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
//        loadContent(contentArea,"Home.fxml");
//    }

}