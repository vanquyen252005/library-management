package com.example.demo;

import com.example.demo.designpattern.Command.Command;
import com.example.demo.designpattern.Command.ConcreteCommand;
import com.example.demo.designpattern.Command.NavigationController;
import com.example.demo.designpattern.Command.NavigationSystem;
import com.example.demo.designpattern.Singleton.Music;
import com.example.demo.admin.Admin;
import com.example.demo.admin.AdminController;
import com.example.demo.student.Student;
import com.example.demo.student.StudentController;
import com.example.demo.user.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;

public class MainController {
    protected Stage stage;
    protected Scene scene;
    protected AnchorPane root;
    protected static User user1 = null;
    protected NavigationSystem navigationSystem = new NavigationSystem(MainApplication.getPrimaryStage());
    protected static NavigationController controller = new NavigationController();
    protected static Music music = Music.getInstance();
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
            FXMLLoader loader = new FXMLLoader(MainController.class.getResource(fxmlFile));
            Parent root = loader.load();
            // Tạo Scene mới và gán vào Stage
            Scene scene = new Scene(root);
            NavigationSystem navigationSystem1 = new NavigationSystem(stage);
            Command switchToScence = new ConcreteCommand(navigationSystem1, scene);
            controller.executeCommand(switchToScence);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeUser(User user, String fileName) {
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
    public static User readUser(String fileName) {
        String path = "src/main/resources/com/example/demo/" + fileName;
        try  {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
            Object o = ois.readObject();
            if (o instanceof Student) {
                System.out.println("student read");
                return (Student)o;
            } else if (o instanceof Admin) {
                System.out.println("admin read");
                return (Admin)o;
            }
//            return (User) ois.readObject(); // Đọc đối tượng từ tệp và ép kiểu
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Lỗi khi đọc đối tượng: " + e.getMessage());
        }
        return null;  // Trả về null nếu có lỗi
    }
    @FXML
    public void initialize() {
        if (MainController.readUser("log.txt") != null) {
            user1 = MainController.readUser("log.txt");
            if (user1 instanceof Admin) {
            AdminController.user = (Admin) user1;}
            else if (user1 instanceof Student) {
                StudentController.user = (Student)user1;
            }
        }

        if (music.getNameSong() == null) {
            music.setNameSong("vnt.mp3");
            music.playSong();
        }

    }
    @FXML
    protected void AdminLogin(ActionEvent event) {
        try{
            if (user1 != null && user1 instanceof Admin) {
            displayScene(event, "admin/Home.fxml");
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
            System.out.println('#' + ((Student) user1).getClassName());
            displayScene(event, "student/Home.fxml");
        } else {
            displayScene(event,"student/StudentLogin.fxml");
        }
    }
}