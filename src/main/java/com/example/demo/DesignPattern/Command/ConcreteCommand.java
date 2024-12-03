package com.example.demo.DesignPattern.Command;

import javafx.scene.Scene;

public class ConcreteCommand implements Command {
    private NavigationSystem navigationSystem;
    private Scene targetScene;

    public ConcreteCommand(NavigationSystem navigationSystem, Scene targetScene) {
        this.navigationSystem = navigationSystem;
        this.targetScene = targetScene;
    }

    @Override
    public void execute() {
        navigationSystem.switchToScene(targetScene);  // Chuyển sang cảnh mới
    }

    @Override
    public void undo() {
        navigationSystem.goBack();  // Quay lại cảnh trước
    }
}
