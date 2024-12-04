package com.example.demo.student;

import com.example.demo.DesignPattern.Singleton.Notification;
import com.example.demo.DesignPattern.Singleton.NotificationManager;
import com.example.demo.book.Database;
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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NotificationController extends menuController {

    @FXML
    private GridPane notifyView;

    @FXML
    private Button clearNotification;

    private Database database = Database.getInstance();
    private Student user = studentcontroller.getStudent();

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
                // Chuyển đổi notify_date từ chuỗi thành LocalDateTime
                LocalDateTime date1 = LocalDateTime.parse(o1.getNotify_date(), formatter);
                LocalDateTime date2 = LocalDateTime.parse(o2.getNotify_date(), formatter);

                // So sánh thời gian, ngày mới hơn sẽ đứng trước
                return date2.compareTo(date1);  // So sánh ngược lại để sắp xếp từ ngày mới nhất
            }
        });


        notifyView.getChildren().clear();
        notifyView.getColumnConstraints().clear();
        notifyView.getRowConstraints().clear();
        notifyView.getColumnConstraints().add(new ColumnConstraints());
        notifyView.getColumnConstraints().add(new ColumnConstraints());

        int row = 0;

        for (Notification notification : notificationList) {
            String notify_date = notification.getNotify_date();  // Lấy ngày thông báo
            String content = notification.getContent();  // Lấy nội dung thông báo

            // Tạo Label cho date và content
            Label dateLabel = new Label(notify_date);
            Label contentLabel = new Label(content);

            dateLabel.getStyleClass().add("dateLabel");
            contentLabel.getStyleClass().add("contentLabel");

            // Thiết lập padding cho các Label (nếu cần thiết)
            dateLabel.setPadding(new Insets(5, 10, 5, 10));  // Padding cho date
            contentLabel.setPadding(new Insets(5, 10, 5, 10));  // Padding cho content

            // Kiểm tra nếu GridPane có đủ hàng để thêm thông báo
            if (row >= notifyView.getRowCount()) {
                // Nếu số hàng ít hơn số notification, tự động thêm hàng
                notifyView.getRowConstraints().add(new RowConstraints());  // Thêm một hàng mới
            }

            // Đặt các Label vào các ô tương ứng trong GridPane
            notifyView.add(dateLabel, 0, row);  // Đặt date vào cột 0, hàng row
            notifyView.add(contentLabel, 1, row);  // Đặt content vào cột 1, hàng row

            // Tăng số hàng lên để cho các thông báo tiếp theo
            row++;
        }
    }

    @FXML
    public void clickClearNotification(ActionEvent e) {
        NotificationManager.getInstance().clearUserNotification(Integer.parseInt(user.getId()));
        UpNotification();
    }

}
