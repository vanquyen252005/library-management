package com.example.demo.student;

import com.example.demo.designpattern.Singleton.NotificationManager;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class EditProfileController extends ProfileController {
    @FXML
    public Label Notification;
    @FXML
    public Button ChangePassWord_lbn;
    public ImageView imageView = new ImageView();
    @FXML
    private TextField usernameField;
    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField classField;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    public void initialize() {
        super.initialize();
        if (savedImageURI != null) {
            System.out.println("Loading saved image.");
            Image savedImage = new Image(savedImageURI);
            imageView.setImage(savedImage);
        }
    }

    private void saveUserProfile() {
        // Example: Save data to the database
        String username = usernameField.getText();
        String name = nameField.getText();
        String phone = phoneField.getText();
        String className = classField.getText();

        System.out.println("Saved Profile:");
        System.out.println("Username: " + username);
        System.out.println("Name: " + name);
        System.out.println("Phone: " + phone);
        System.out.println("Class: " + className);

        // TODO: Implement database save logic
        //user.updateUserProfile(user.getId(), username,name, phone,className);
        user.updateUserProfile(user.getId(), username,name, phone,className);
    }

    private void cancelEdit() {
        // Close the window or navigate back to the previous screen
        System.out.println("Edit cancelled.");
        // TODO: Add navigation logic
    }

    public void back(ActionEvent event) {
        displayScene(event,"Profile.fxml");
    }

    public void SaveProfile(ActionEvent event) {
        boolean isNotified = NotificationManager.getInstance().userNotify(Integer.parseInt(user.getId()),1,"");
        if(!isNotified) {
            System.out.println("#1 unable to notify borrowing book");
        }
        String username = usernameField.getText();
        String name = nameField.getText();
        String phone = phoneField.getText();
        String className = classField.getText();
        if(username.isEmpty() && name.isEmpty() && phone.isEmpty() && className.isEmpty())
        {
            Notification.setText("Khong co thay doi nao ca");
        }
      else {
          saveUserProfile();
          Notification.setText("Cap nhat du lieu thanh cong !");
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(e -> displayScene(event, "Profile.fxml"));
        pause.play();
        }
    }

    public void ChangePassWord(ActionEvent event) {
        try {
            displayScene(event, "ChangePassWord.fxml");
        } catch (Exception e) {
            System.out.println("Error loading ChangePassWord.fxml: " + e.getMessage());
            Notification.setText("Không thể mở trang đổi mật khẩu.");
            Notification.setStyle("-fx-text-fill: red;");
        }
    }

}