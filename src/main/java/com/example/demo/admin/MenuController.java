package com.example.demo.admin;

import com.example.demo.designpattern.Singleton.Notification;
import com.example.demo.designpattern.Singleton.NotificationManager;
import com.example.demo.MainApplication;
import com.example.demo.MainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class MenuController extends MainController {
    @FXML
    public TreeView<String> miniBar;
    public Button back;
    public Pane pane1;
    public Pane pane2;
    public ListView notifyList;
    public ImageView adminNotify;
    public AnchorPane notifyPane;

    @FXML
    protected Button homeButton;
    @FXML
    protected Button manageStudent;
    @FXML
    protected Button manageBook;
    @FXML
    protected Button handleRequest;
    public Admin user = AdminController.user;

    @FXML
    public void initialize() {
        homeButton.getStyleClass().add("selected");
        manageStudent.getStyleClass().add("after");
        homeButton.setOnAction(event -> handleHomeAction(event));
        manageStudent.setOnAction(event -> handleManageStudentAction(event));
        manageBook.setOnAction(event -> handleManageBookAction(event));
        handleRequest.setOnAction(event -> handleHandleRequestAction(event));
        back.setOnAction(event -> handleBack(event));
        TreeItem<String> rootItem = new TreeItem<>("Hello " + user.getUsername() );
        rootItem.setExpanded(false);
        adminNotify.setImage(new Image(getClass().getResourceAsStream("/Picture/rb_2177.png")));
        TreeItem<String> logoutItem = new TreeItem<>("Logout");
        TreeItem<String> changeThemeItem = new TreeItem<>("Play/Pause music");

        rootItem.getChildren().addAll(logoutItem, changeThemeItem);
        notifyPane.setPrefHeight(0);
        notifyList.setOpacity(0);
        miniBar.setRoot(rootItem);
        miniBar.setShowRoot(true);
        miniBar.getStyleClass().add("tree-view");

        miniBar.setOnMouseEntered(event -> {
            rootItem.setExpanded(true);
            miniBar.getStyleClass().add("expanded");
        });
        miniBar.setOnMouseExited(event -> {
            rootItem.setExpanded(false);
            miniBar.getStyleClass().remove("expanded");
        });

        miniBar.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    String selectedOption = newValue.getValue();
                    switch (selectedOption) {
                        case "Logout":
                            user = null;
                            user1 = null;
                            MainController.writeUser(null,"log.txt");
                            Stage stage = MainApplication.getPrimaryStage();
                            displayScene(stage, "Main.fxml");
                            break;
                        case "Play/Pause music":
                            System.out.println("you choose play/pause music");
                            switch (music.getStatus()) {
                                case PLAYING:
                                    music.pauseSong();
                                    break;
                                case PAUSED:
                                    music.playSong();
                                    break;
                                default:
                                    System.out.println("Unable to run music!");
                            }
                            break;
                        default:
                            break;
                    }
        });
        UpNotification();
        notifyList.setPrefHeight(0);

//        if (music.getNameSong() == null) {
//            music.setNameSong("music.mp3");
//            music.playSong();
//        }

        // Nút phát nhạc

    }
    public void UpNotification() {
        List<Notification> notificationList = NotificationManager.getInstance().getNotificationList();

        notificationList = NotificationManager.getInstance().adminSortAndFiler(notificationList);

        for (Notification x : notificationList) {
            System.out.println(x.getContent());
        }

        ObservableList<Notification> notifications = FXCollections.observableArrayList(notificationList);

        notifyList.setItems(notifications);

        notifyList.setCellFactory(listView -> new ListCell<Notification>() {
            @Override
            protected void updateItem(Notification item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    VBox tempVBox = new VBox();
                    tempVBox.setSpacing(5);

                    Text dateText = new Text(item.getNotify_date());
                    dateText.setStyle("-fx-font-size: 10px; -fx-fill: gray;");

                    Text contentText = new Text(item.getContent());
                    contentText.setStyle("-fx-font-size: 14px; -fx-fill: black;");

                    tempVBox.getChildren().addAll(dateText, contentText);

                    setGraphic(tempVBox);
                }
            }
        });
    }


    public void handleHomeAction(ActionEvent event) {
        displayScene(event, "Home.fxml");
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
    }

    public void handleAdminNotifyAction(MouseEvent event) {
        if (notifyList.getOpacity() > 0) {
            notifyList.setOpacity(0);
            notifyList.setPrefHeight(0);
            notifyPane.setPrefHeight(0);
        } else {
            notifyList.setOpacity(1);
            notifyList.setPrefHeight(200);
            notifyPane.setPrefHeight(200);
        }
    }
}
