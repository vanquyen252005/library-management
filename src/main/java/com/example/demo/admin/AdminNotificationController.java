package com.example.demo.admin;

import com.example.demo.designpattern.Singleton.Notification;
import com.example.demo.designpattern.Singleton.NotificationManager;
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


public class AdminNotificationController extends MenuController {

    /**
     * Controller class for managing and displaying notifications for the admin user.
     * This class is responsible for displaying, updating, and clearing notifications.
     */

    @FXML
    private TableView<Notification> notifyTableView;

    private Database database = Database.getInstance();

    /**
     * Initializes the TableView columns and sets up the notification list.
     */

    public void initialize() {

        TableColumn<Notification, String> dateColumn =
                new TableColumn<>("Date");
        TableColumn<Notification, String> contentColumn =
                new TableColumn<>("Content");
        dateColumn.setCellValueFactory(
                new PropertyValueFactory<>("notify_date"));
        contentColumn.setCellValueFactory(
                new PropertyValueFactory<>("content"));

        notifyTableView.getColumns().addAll(dateColumn, contentColumn);

        UpNotification();

    }

    /**
     * Updates the TableView with the latest notifications.
     * Retrieves the notification list from the NotificationManager and sets it in the TableView.
     */

    public void UpNotification() {
        List<Notification> notificationList = NotificationManager.getInstance().getNotificationList();

        notificationList = NotificationManager.getInstance().adminSortAndFiler(notificationList);
        for (Notification x:notificationList) {
            System.out.println(x.getContent());
        }
        ObservableList<Notification> notifications = FXCollections.observableArrayList(notificationList);

        notifyTableView.setItems(notifications);
    }

    /**
     * Clears the notifications for the current admin user.
     *
     * @param e The action event triggered by the "Clear Notifications" button.
     */
    @FXML
    public void clickClearNotification(ActionEvent e) {
        NotificationManager.getInstance().clearUserNotification(Integer.parseInt(user.getId()));
        UpNotification();
    }

    /**
     * Handles the click action on the notification list.
     * Toggles the visibility of the notification list based on its current opacity.
     *
     * @param event The mouse event triggered when the user interacts with the notification list.
     */

    public void handleAdminNotifyAction(MouseEvent event) {
        if (notifyList.getOpacity() > 0) {
            notifyList.setOpacity(1);
        } else {
            notifyList.setOpacity(0);
            System.out.println(notifyList.getOpacity());
        }
    }
}
