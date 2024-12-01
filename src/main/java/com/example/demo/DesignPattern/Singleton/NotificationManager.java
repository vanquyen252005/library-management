package com.example.demo.DesignPattern.Singleton;

import com.example.demo.book.Database;

import java.util.ArrayList;
import java.util.List;

public class NotificationManager {
    private static NotificationManager instance;
    private List<Notification> notificationList;

    private NotificationManager (){
        notificationList = Database.getInstance().getNotificationList();
        //content#date#user_id
    }

    public static NotificationManager getInstance() {
        if (instance == null) {
            instance = new NotificationManager();
        }
        return instance;
    }

    public List<Notification> getUserNotification(int user_id) {
        notificationList = Database.getInstance().getNotificationList();
        System.out.println("before take notify list for usser id , the total size is " + notificationList.size());
        List<Notification> resultList = new ArrayList<>();

        // Lặp qua tất cả các thông báo trong notificationList
        for (Notification notification : notificationList) {
            if(notification.getUser_id() == user_id) {
                resultList.add(notification);
            }
        }
        return resultList;
    }

    public boolean userNotify(int user_id, int type, String ISBN) {
        return Database.getInstance().userNotify(user_id, type, ISBN);
    }

    public void clearUserNotification(int user_id) {
        Database.getInstance().clearUserNotify(user_id);
    }

}
