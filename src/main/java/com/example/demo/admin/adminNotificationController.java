package com.example.demo.admin;

import com.example.demo.DesignPattern.Singleton.Notification;
import com.example.demo.DesignPattern.Singleton.NotificationManager;
import com.example.demo.book.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.util.*;


public class adminNotificationController extends menucontroller {

    @FXML
    private TableView<Notification> notifyTableView; // Thay thế GridPane bằng TableView

    private Database database = Database.getInstance();

    public void initialize() {

        TableColumn<Notification, String> dateColumn =
                new TableColumn<>("Date");
        TableColumn<Notification, String> contentColumn =
                new TableColumn<>("Content");
        // Thiết lập cột cho TableView
        dateColumn.setCellValueFactory(
                new PropertyValueFactory<>("notify_date"));
        contentColumn.setCellValueFactory(
                new PropertyValueFactory<>("content"));

        notifyTableView.getColumns().addAll(dateColumn, contentColumn);
        // Cập nhật danh sách thông báo khi khởi tạo
        UpNotification();

    }

    public void UpNotification() {
        List<Notification> notificationList = NotificationManager.getInstance().getNotificationList();

        // Sắp xếp và lọc thông báo (nếu cần)
        notificationList = NotificationManager.getInstance().adminSortAndFiler(notificationList);
        for (Notification x:notificationList) {
            System.out.println(x.getContent());
        }
        // Chuyển List thành ObservableList để TableView hiển thị
        ObservableList<Notification> notifications = FXCollections.observableArrayList(notificationList);

        // Cập nhật TableView với dữ liệu mới
        notifyTableView.setItems(notifications);
    }

    @FXML
    public void clickClearNotification(ActionEvent e) {
        NotificationManager.getInstance().clearUserNotification(Integer.parseInt(user.getId()));
        UpNotification();
    }

    public void handleAdminNotifyAction(MouseEvent event) {
        if (notifyList.getOpacity() > 0) {
            notifyList.setOpacity(1);
        } else {
            notifyList.setOpacity(0);
            System.out.println(notifyList.getOpacity());
        }
    }
}
