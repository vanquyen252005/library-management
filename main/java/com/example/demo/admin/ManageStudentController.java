package com.example.demo.admin;

import com.example.demo.student.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.ResultSet;
import java.util.List;


public class ManageStudentController extends MenuController{
    @FXML
    public TreeView<String> studentSearchType;
    @FXML
    private TextField input;
    @FXML
    private TreeTableView<Student> studentTable;
    @FXML
    private TreeTableColumn<Student, String> username;
    @FXML
    private TreeTableColumn<Student, String> name;
    @FXML
    private TreeTableColumn<Student, String> Class;
    @FXML
    private TreeTableColumn<Student, Button> detail;
    private  TreeItem<Student> root1 = new TreeItem<>(new Student("Username", "name", "Class"));
    private String op;
    @Override
    @FXML
    public void initialize() {
        TreeItem<String> root = new TreeItem<>("types");
        TreeItem<String> childItem1 = new TreeItem<>("ID");
        TreeItem<String> childItem2 = new TreeItem<>("Username");
        TreeItem<String> childItem3 = new TreeItem<>("name");
        root.getChildren().addAll(childItem1,childItem2,childItem3);
        studentSearchType.setRoot(root);
        studentSearchType.setPrefWidth(150);
        studentSearchType.setShowRoot(true);
        studentSearchType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
           if (newValue == childItem1) {
                op = "ID";
//               studentSearchType.setPrefHeight(20);
            } else if (newValue == childItem2) {
                op = "username";
//               studentSearchType.setPrefHeight(20);

            } else if (newValue == childItem3) {
                op = "name";
//               studentSearchType.setPrefHeight(20);
            } else if (newValue == root) {
               studentSearchType.setPrefHeight(120);
           }
        });

        username.setCellValueFactory(
                TreeTableColumn.CellDataFeatures<Student, String> param) -> param.get
        );
    }


    public void searchStudent(ActionEvent event) {
        List<Student> result = user.getStudentBy(op, input.getText());
        for (Student x:result) {
            root1.getChildren().add(new TreeItem<>(x));
            System.out.println(x.getUsername() + " " + x.getName());
        }
        studentTable.setRoot(root1);
//        studentTable.setShowRoot(true);
    }
}
