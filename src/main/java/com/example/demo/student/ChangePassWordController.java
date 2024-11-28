package com.example.demo.student;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class ChangePassWordController extends EditProfileController {
    public Label cfnewPassWord;

    public PasswordField oldPassWordField;
    public PasswordField newPassWordField;
    public PasswordField cfNewPassWordfield;
    public Label Notification;

    public Button submit_lbn;

    public void submit(ActionEvent event) {
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
        }
    }
}
