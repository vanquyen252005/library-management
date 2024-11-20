package com.example.demo.admin;

import com.example.demo.HelloApplication;
import com.example.demo.HelloController;
import com.sun.tools.javac.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

public class menucontroller extends admincontroller {
    @FXML
    public TreeView<String> miniBar;
    @FXML
    protected Button home;
    @FXML
    protected Button manageStudent;
    @FXML
    protected Button manageBook;
    @FXML
    protected Button search;
    @FXML
    protected Button handleRequest;

    @FXML
    public void initialize() {
        // Thiết lập ActionListener cho các nút
        if (user1 != null) {
            user = new admin(user1.getId(), user1.getUsername(),
                    user1.getPassword(), user1.getName(),
                    user1.getRole(), user1.getPhone());
        }
        home.setOnAction(event -> handleHomeAction(event));
        manageStudent.setOnAction(event -> handleManageStudentAction(event));
        manageBook.setOnAction(event -> handleManageBookAction(event));
        search.setOnAction(event -> handleSearchAction(event));
        handleRequest.setOnAction(event -> handleHandleRequestAction(event));

        TreeItem<String> rootItem = new TreeItem<>("Hello " );
        rootItem.setExpanded(true); // Mở rộng TreeView mặc định

        // Tạo các mục con
        TreeItem<String> logoutItem = new TreeItem<>("Logout");
        TreeItem<String> changeThemeItem = new TreeItem<>("Change Theme");

        // Thêm các mục con vào gốc
        rootItem.getChildren().addAll(logoutItem, changeThemeItem);

        // Tạo TreeView
//        TreeView<String> miniBar = new TreeView<>(rootItem);
miniBar.setRoot(rootItem);
        // Thêm sự kiện khi click vào các mục
        miniBar.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String selectedOption = newValue.getValue();
                switch (selectedOption) {
                    case "Logout":
                        user = null;
                        user1 = null;
                        HelloController.writeAdmin(null,"log.data");
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

    public void handleSearchAction(ActionEvent event) {
        displayScene(event, "menu.fxml");
    }

    public void handleHandleRequestAction(ActionEvent event) {
        displayScene(event, "menu.fxml");
    }

}
