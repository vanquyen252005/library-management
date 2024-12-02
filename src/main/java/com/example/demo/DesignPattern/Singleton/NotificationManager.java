package com.example.demo.DesignPattern.Singleton;

import com.example.demo.book.Database;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class NotificationManager {
    private static NotificationManager instance;
    private List<Notification> notificationList;

    private NotificationManager (){
        notificationList = Database.getInstance().getNotificationList();
        //content#date#user_id
//        System.out.println("form manager");
//        for (Notification x:notificationList) {
//            System.out.println(x.getNotify_date());
//        }
    }

    public static NotificationManager getInstance() {
        if (instance == null) {
            instance = new NotificationManager();
        }
        instance.notificationList = Database.getInstance().getNotificationList();
        return instance;
    }

    public List<Notification> getUserNotification(int user_id) {

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

    public List<Notification> getNotificationList() {
        return notificationList;
    }


    public boolean userNotify(int user_id, int type, String ISBN) {
        return Database.getInstance().userNotify(user_id, type, ISBN);
    }

    public void clearUserNotification(int user_id) {
        Database.getInstance().clearUserNotify(user_id);
    }


    public String convertAdminNotification(Notification notify) {

        String content = notify.getContent();
        int user_id = notify.getUser_id();

        //1 change profile info
        //2 change password
        //3 request borrow
        //4 request return

        String newContent = content;
        if(content.contains("profile")) {
            newContent = "user " + user_id + " have made change to the profile information";
        } else if(content.contains("returning") || content.contains("borrowing")) {
            newContent = "user " + user_id + content.substring(4);
        } else if(content.contains("password")) {
            newContent = "user " + user_id + " have changed the password";
        }

        return newContent;
    }

    public List<Notification> adminSortAndFiler(List<Notification> notificationList) {
        Stack<Notification> notificationStack = new Stack<>();

        for (Notification notification : notificationList) {
            // Kiểm tra nếu content chứa "undo"

            if (notification.getContent().contains("undo") && !notificationStack.isEmpty()) {
                notificationStack.pop();
                continue;
            }
            notificationStack.push(notification);
        }

        List<Notification> result = new ArrayList<>();

        for(Notification notify : notificationStack) {
            String content = notify.getContent();
            String newContent = convertAdminNotification(notify);
            notify.setContent(newContent);
            result.add(notify);
//            System.out.println("$$$"+notify.getContent());
        }
        return result;
    }

}
