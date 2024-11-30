package com.example.demo.admin;

import com.example.demo.HelloApplication;
import com.example.demo.HelloController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

public class menucontroller extends HelloController {
    @FXML
    public TreeView<String> miniBar;
    public Button back;
    @FXML
    protected Button home;
    @FXML
    protected Button manageStudent;
    @FXML
    protected Button manageBook;
    @FXML
    protected Button handleRequest;
    public admin user = admincontroller.user;
    @FXML
    public void initialize() {
        home.getStyleClass().add("selected");
        manageBook.getStyleClass().remove("selected");
       manageStudent.getStyleClass().remove("selected");
       handleRequest.getStyleClass().remove("selected");
        home.setOnAction(event -> handleHomeAction(event));
        manageStudent.setOnAction(event -> handleManageStudentAction(event));
        manageBook.setOnAction(event -> handleManageBookAction(event));
        handleRequest.setOnAction(event -> handleHandleRequestAction(event));
        back.setOnAction(event -> handleBack(event));
        TreeItem<String> rootItem = new TreeItem<>("Hello " + user.getUsername() );
        rootItem.setExpanded(false); // Mở rộng TreeView mặc định

        // Tạo các mục con
        TreeItem<String> logoutItem = new TreeItem<>("Logout");
        TreeItem<String> changeThemeItem = new TreeItem<>("Change Theme");

        // Thêm các mục con vào gốc
        rootItem.getChildren().addAll(logoutItem, changeThemeItem);

        // Tạo TreeView
//        TreeView<String> miniBar = new TreeView<>(rootItem);
        miniBar.setRoot(rootItem);
        miniBar.setShowRoot(true);
        miniBar.getStyleClass().add("tree-view");
        // Thêm sự kiện khi click vào các mục
        miniBar.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String selectedOption = newValue.getValue();
                switch (selectedOption) {
                    case "Logout":
                        user = null;
                        user1 = null;
                        HelloController.writeAdmin(null,"log.txt");
                        Stage stage = HelloApplication.getPrimaryStage();
                        HelloController.displayScene(stage, "hello-view.fxml");
                        // Xử lý logout ở đây
                        break;
                    case "Change Theme":
                        System.out.println("Đã chọn Change Theme");
                        // Xử lý đổi theme ở đây
                        break;
                    default:
                        break;
                }
            }
        });

    }

    public void handleHomeAction(ActionEvent event) {
        displayScene(event, "menu.fxml");
    }

    public void handleManageStudentAction(ActionEvent event) {
        displayScene(event, "ManageStudent.fxml");
    }

    public void handleManageBookAction(ActionEvent event) {
        displayScene(event, "ManageBook.fxml");
    }

    public void handleHandleRequestAction(ActionEvent event) {
        displayScene(event, "HandleRequest.fxml");
    }

    public void handleBack(ActionEvent event) {
        controller.undo();
        System.out.println("heeh");
    }
}
