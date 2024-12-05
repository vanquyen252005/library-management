package com.example.demo.student;

import com.example.demo.designpattern.Singleton.Notification;
import com.example.demo.designpattern.Singleton.NotificationManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NotificationController extends MenuController {

    @FXML
    private GridPane notifyView;

    @FXML
    private Button clearNotification;

    private Student user = StudentController.getStudent();

    public void initialize() {
        UpNotification();
    }

    public void UpNotification() {
        List<Notification> notificationList = NotificationManager.getInstance().getUserNotification(Integer.parseInt(user.getId()));
        System.out.println("notificationList = " + notificationList.size() );

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Collections.sort(notificationList, new Comparator<Notification>() {
            @Override
            public int compare(Notification o1, Notification o2) {
                LocalDateTime date1 = LocalDateTime.parse(o1.getNotify_date(), formatter);
                LocalDateTime date2 = LocalDateTime.parse(o2.getNotify_date(), formatter);
                return date2.compareTo(date1);
            }
        });


        notifyView.getChildren().clear();
        notifyView.getColumnConstraints().clear();
        notifyView.getRowConstraints().clear();
        notifyView.getColumnConstraints().add(new ColumnConstraints());
        notifyView.getColumnConstraints().add(new ColumnConstraints());

        int row = 0;

        for (Notification notification : notificationList) {
            String notify_date = notification.getNotify_date();
            String content = notification.getContent();

            Label dateLabel = new Label(notify_date);
            Label contentLabel = new Label(content);

            dateLabel.getStyleClass().add("dateLabel");
            contentLabel.getStyleClass().add("contentLabel");

            dateLabel.setPadding(new Insets(5, 10, 5, 10));
            contentLabel.setPadding(new Insets(5, 10, 5, 10));

            if (row >= notifyView.getRowCount()) {
                notifyView.getRowConstraints().add(new RowConstraints());
            }

            notifyView.add(dateLabel, 0, row);
            notifyView.add(contentLabel, 1, row);

            row++;
        }
    }

    @FXML
    public void clickClearNotification(ActionEvent e) {
        NotificationManager.getInstance().clearUserNotification(Integer.parseInt(user.getId()));
        UpNotification();
    }

}
