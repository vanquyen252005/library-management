package com.example.demo.admin;

import com.example.demo.DesignPattern.Singleton.Music;
import com.example.demo.DesignPattern.Singleton.Notification;
import com.example.demo.DesignPattern.Singleton.NotificationManager;
import com.example.demo.HelloApplication;
import com.example.demo.HelloController;
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

public class menucontroller extends HelloController {
    @FXML
    public TreeView<String> miniBar;
    public Button back;
    public Pane pane1;
    public Pane pane2;
    public ListView notifyList;
    public ImageView adminNotify;
    public AnchorPane notifyPane;
    private static Music music = Music.getInstance();
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
        manageStudent.getStyleClass().add("after");
        home.setOnAction(event -> handleHomeAction(event));
        manageStudent.setOnAction(event -> handleManageStudentAction(event));
        manageBook.setOnAction(event -> handleManageBookAction(event));
        handleRequest.setOnAction(event -> handleHandleRequestAction(event));
        back.setOnAction(event -> handleBack(event));
        TreeItem<String> rootItem = new TreeItem<>("Hello " + user.getUsername() );
        rootItem.setExpanded(false); // Mở rộng TreeView mặc định
        adminNotify.setImage(new Image(getClass().getResourceAsStream("/Picture/rb_2177.png")));
        TreeItem<String> logoutItem = new TreeItem<>("Logout");
        TreeItem<String> changeThemeItem = new TreeItem<>("Play/Pause music");
        // Thêm các mục con vào gốc
        rootItem.getChildren().addAll(logoutItem, changeThemeItem);
        notifyPane.setPrefHeight(0);
        notifyList.setOpacity(0);
        miniBar.setRoot(rootItem);
        miniBar.setShowRoot(true);
        miniBar.getStyleClass().add("tree-view");

        // Thêm sự kiện hover (di chuột vào)
        miniBar.setOnMouseEntered(event -> {
            rootItem.setExpanded(true); // Mở rộng khi di chuột vào
            miniBar.getStyleClass().add("expanded"); // Thêm CSS nếu cần
        });
        miniBar.setOnMouseExited(event -> {
            rootItem.setExpanded(false); // Thu gọn khi di chuột ra
            miniBar.getStyleClass().remove("expanded"); // Xóa CSS nếu cần
        });
        // Thêm sự kiện khi click vào các mục
        miniBar.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    String selectedOption = newValue.getValue();
                    switch (selectedOption) {
                        case "Logout":
                            user = null;
                            user1 = null;
                            HelloController.writeUser(null,"log.txt");
                            Stage stage = HelloApplication.getPrimaryStage();
                            displayScene(stage, "hello-view.fxml");
                            // Xử lý logout ở đây
                            break;
                        case "Play/Pause music":
                            System.out.println("Đã chọn Change Theme");
                            switch (music.getStatus()) {
                                case PLAYING:
                                    music.pauseSong();
                                    break;
                                case PAUSED:
                                    music.playSong();
                                    break;
                                default:
                                    System.out.println("Nhạc không chạy!");
                            }
                            break;
                        default:
                            break;
                    }
        });
        UpNotification();
        notifyList.setPrefHeight(0);

        if (music.getNameSong() == null) {
            music.setNameSong("music.mp3");
            music.playSong();
        }

        // Nút phát nhạc

    }
    public void UpNotification() {
        List<Notification> notificationList = NotificationManager.getInstance().getNotificationList();

        // Sắp xếp và lọc thông báo (nếu cần)
        notificationList = NotificationManager.getInstance().adminSortAndFiler(notificationList);

        for (Notification x : notificationList) {
            System.out.println(x.getContent());
        }

        // Chuyển List thành ObservableList
        ObservableList<Notification> notifications = FXCollections.observableArrayList(notificationList);

        // Cập nhật ListView với dữ liệu mới
        notifyList.setItems(notifications);

        // Tùy chỉnh hiển thị cho mỗi mục trong ListView
        notifyList.setCellFactory(listView -> new ListCell<Notification>() {
            @Override
            protected void updateItem(Notification item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null); // Xóa nội dung nếu mục trống
                } else {
                    // Tạo VBox để chứa ngày và nội dung
                    VBox vbox = new VBox();
                    vbox.setSpacing(5); // Khoảng cách giữa date và content

                    // Tạo Text cho ngày
                    Text dateText = new Text(item.getNotify_date());
                    dateText.setStyle("-fx-font-size: 10px; -fx-fill: gray;"); // Phông chữ cho ngày

                    // Tạo Text cho nội dung
                    Text contentText = new Text(item.getContent());
                    contentText.setStyle("-fx-font-size: 14px; -fx-fill: black;"); // Phông chữ cho nội dung

                    // Thêm cả hai Text vào VBox (ngày và nội dung trên 2 dòng)
                    vbox.getChildren().addAll(dateText, contentText);

                    // Thiết lập VBox vào cell
                    setGraphic(vbox);
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
