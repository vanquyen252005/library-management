package com.example.demo.DesignPattern.Command;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class NavigationSystem {
    private Stage stage;
    private Scene currentScene;
    private Scene previousScene;

    public NavigationSystem(Stage stage) {
        this.stage = stage;
    }

    public NavigationSystem(Stage stage, Scene currentScene) {
        this.stage = stage;
        this.currentScene = currentScene;
    }

    // Lưu trạng thái của cảnh trước khi chuyển
    public void switchToScene(Scene scene) {
        currentScene = stage.getScene();
        if (currentScene != null) {
            previousScene = currentScene;  // Lưu cảnh hiện tại trước khi chuyển
        }
        currentScene = scene;
//        scene = previousScene;

//        System.out.println("switchToScene in navSys " + currentScene + " " + previousScene);
        stage.setScene(scene);
    }

    // Quay lại cảnh trước đó
    public void goBack() {
        if (previousScene != null) {
            stage.setScene(previousScene);
            Scene temp = currentScene;
            currentScene = previousScene;
            previousScene = temp;
//            System.out.println("goBack in navSys " + currentScene + " " + previousScene);
        }
    }

    // Lấy cảnh hiện tại
    public Scene getCurrentScene() {
        return currentScene;
    }

    // Lấy cảnh trước đó
    public Scene getPreviousScene() {
        return previousScene;
    }
}

