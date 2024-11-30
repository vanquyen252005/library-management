package com.example.demo;

import com.example.demo.DesignPattern.Command.Command;
import com.example.demo.DesignPattern.Command.ConcreteCommand;
import com.example.demo.DesignPattern.Command.NavigationController;
import com.example.demo.DesignPattern.Command.NavigationSystem;
import com.example.demo.admin.admin;
import com.example.demo.admin.admincontroller;
import com.example.demo.student.Student;
import com.example.demo.student.studentcontroller;
import com.example.demo.user.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;

public class HelloController{
    protected Stage stage;
    protected Scene scene;
    protected AnchorPane root;
    protected static User user1 = null;
    protected NavigationSystem navigationSystem = new NavigationSystem(HelloApplication.getPrimaryStage());
    protected static NavigationController controller = new NavigationController();
    @FXML
    protected void displayScene(ActionEvent event, String fxmlLink) {
        try {
            root = FXMLLoader.load(getClass().getResource(fxmlLink));
            scene = new Scene(root);
            Command switchToScence = new ConcreteCommand(navigationSystem, scene);
            controller.executeCommand(switchToScence);
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

        try  {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
            oos.writeObject(user); // Ghi đối tượng vào tệp
            oos.close();
            System.out.println("Đã lưu đối tượng vào tệp " + path);
        } catch (IOException e) {
            System.err.println("Lỗi khi ghi đối tượng: " + e.getMessage());
        }
    }
    public static User readAdmin(String fileName) {
        String path = "src/main/resources/com/example/demo/" + fileName;
        try  {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
            Object o = ois.readObject();
            if (o instanceof Student) {
                System.out.println("student read");
                return (Student)o;
            } else if (o instanceof admin) {
                System.out.println("admin read");
                return (admin)o;
            }
//            return (User) ois.readObject(); // Đọc đối tượng từ tệp và ép kiểu
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Lỗi khi đọc đối tượng: " + e.getMessage());
        }
        return null;  // Trả về null nếu có lỗi
    }
    @FXML
    public void initialize() {
        if (HelloController.readAdmin("log.txt") != null) {
            System.out.println("kdoaljdoids");
            user1 = HelloController.readAdmin("log.txt");
            if (user1 instanceof admin) {
            admincontroller.user = (admin) user1;}
            else if (user1 instanceof Student) {
                studentcontroller.user = (Student)user1;
            }
//            System.out.println(user1.getRole());
        }
    }
    @FXML
    protected void AdminLogin(ActionEvent event) {
        try{
            if (user1 != null && user1 instanceof admin ) {
//                System.out.println('#' + user1.getRole());
//                root = FXMLLoader.load(getClass().getResource("admin/menu.fxml"));
//                scene = new Scene(root);
//                Command switchToScence = new ConcreteCommand(navigationSystem, scene);
//                controller.executeCommand(switchToScence);
            displayScene(event, "admin/menu.fxml");
            } else {
                displayScene(event,"admin/AdminLogin.fxml");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void StudentLogin(ActionEvent event) {
        if (user1 != null && user1 instanceof Student) {
            System.out.println('#' + ((Student) user1).getClassname());
            displayScene(event, "student/menu.fxml");
        } else {
            displayScene(event,"student/StudentLogin.fxml");
        }
    }
}