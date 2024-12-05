package com.example.demo.admin;

import com.example.demo.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Controller class for handling the admin login interface.
 * It extends the HelloController class and provides functionality
 * for logging in and handling user interaction in the admin UI.
 */
public class AdminController extends MainController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label loginFailLabel;

    public static Admin user = new Admin();

    /**
     * Initializes the controller by calling the parent class's initialize method.
     */
    @FXML
    @Override
    public void initialize() {
        super.initialize();
    }

    /**
     * Handles the login action when the user submits the login form.
     * If the login is successful, it redirects the user to the home page.
     * If login fails, it displays the login failure label.
     *
     * @param event The action event triggered by the login button.
     */
    public void loginAdmin(ActionEvent event) {
        if (user.loginAdmin(username.getText(), password.getText())) {
            MainController.writeUser(user, "log.txt");
            displayScene(event, "Home.fxml");
        } else {
            loginFailLabel.setVisible(true);
        }
    }

    /**
     * Handles the action when the back button is pressed.
     * It calls the undo method from the controller.
     *
     * @param event The action event triggered by the back button.
     */
    public void handleBack(ActionEvent event) {
        controller.undo();
    }
}
