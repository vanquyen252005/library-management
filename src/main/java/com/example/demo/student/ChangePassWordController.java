package com.example.demo.student;

import com.example.demo.DesignPattern.Singleton.NotificationManager;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.util.Duration;

public class ChangePassWordController extends EditProfileController {
    public Label cfnewPassWord;
    public PasswordField oldPassWordField;
    public PasswordField newPassWordField;
    public PasswordField cfNewPassWordfield;
    public Label Notification;
    public Button submit_lbn;
    public Button Cancel_btn;

    public void submit(ActionEvent event) {
        boolean isNotified = NotificationManager.getInstance().userNotify(Integer.parseInt(user.getId()),2,"");
        if(!isNotified) {
            System.out.println("#2 unable to notify borrowing book");
        }

        String oldPassWord = oldPassWordField.getText();
        String newPassWord = newPassWordField.getText();
        String cfNewPassWord = cfNewPassWordfield.getText();
        if(!oldPassWord .equals(user.getPassword())) {
            Notification.setText("Old PassWord is not correct.");
        }
        else if(!cfNewPassWord.equals(newPassWord)) {
            Notification.setText("The new password and confirmation do not match.");
        }
        else if(newPassWord.equals("")) {
            Notification.setText("new passWord is null");
        }
        else {
            user.updatePassWord(user.getId(), newPassWord);
            Notification.setText("New PassWord is updated successfully");
            Notification.setStyle("-fx-font-size: 16px; -fx-text-fill: red;");
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> displayScene(event, "editProfile.fxml"));
            pause.play();
        }
    }

    public void cancel(ActionEvent event) {
        displayScene(event,"editProfile.fxml");
    }

}
