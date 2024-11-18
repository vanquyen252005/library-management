package com.example.demo;

import com.example.demo.user.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;
import java.util.Objects;

public class HelloController{
    protected Stage stage;
    protected Scene scene;
    protected AnchorPane root;
    protected static User user1 = null;
    @FXML
    protected void displayScene(ActionEvent event, String fxmlLink) {
        try {
//            System.out.println("hehe"+getClass().getResource(fxmlLink));
            root = FXMLLoader.load(getClass().getResource(fxmlLink));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public static void displayScene(Stage stage, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloController.class.getResource(fxmlFile));
            Parent root = loader.load();
            // Tạo Scene mới và gán vào Stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeAdmin(User user, String fileName) {
        String path = "src/main/resources/com/example/demo/" + fileName;

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(user);
            System.out.println("Đã lưu đối tượng vào tệp " + path);
        } catch (IOException e) {
            System.err.println("Lỗi khi ghi đối tượng: " + e.getMessage());
        }
    }
    public static User readAdmin(String fileName) {

        InputStream inputStream = HelloController.class.getResourceAsStream("/com/example/demo/" + fileName);

        // Kiểm tra nếu tài nguyên tồn tại
        if (inputStream != null) {
            try (ObjectInputStream ois = new ObjectInputStream(inputStream)) {
                // Đọc đối tượng và ép kiểu thành User
                return (User) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Lỗi khi đọc đối tượng: " + e.getMessage());
            }
        } else {
            System.err.println("Không tìm thấy tài nguyên: " + fileName);
        }
        return null;  // Trả về null nếu có lỗi hoặc tài nguyên không tồn tại
    }
    @FXML
    public void initialize() {
        if (HelloController.readAdmin("log.data") != null) {
            System.out.println("kdoaljdoids");
            user1 = HelloController.readAdmin("log.data");
            System.out.println(user1.getRole());
        }
    }
    @FXML
    protected void AdminLogin(ActionEvent event) {
        if (user1 != null && user1.getRole().equals("admin")) {
                displayScene(event, "admin/menu.fxml");
        } else {
        displayScene(event,"admin/AdminLogin.fxml");
    }
    }
    @FXML
    protected void StudentLogin(ActionEvent event) {
        displayScene(event,"student/StudentLogin.fxml");
    }
}