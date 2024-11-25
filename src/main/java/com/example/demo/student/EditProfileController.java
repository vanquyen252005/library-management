package com.example.demo.student;

import com.example.demo.HelloController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.Scanner;

public class EditProfileController extends ProfileController {

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
        // Load existing user data into fields (example implementation)
        loadUserProfile();

        // Add event listeners
    }

    private void loadUserProfile() {
        // Example: Load data from a database or static source
        usernameField.setText(usernameField.getText());
        nameField.setText(nameField.getText());
        phoneField.setText(phoneField.getText());
        classField.setText(classField.getText());
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
        saveUserProfile();
        displayScene(event,"Profile.fxml");
    }
}