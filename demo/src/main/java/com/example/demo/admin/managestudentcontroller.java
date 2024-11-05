package com.example.demo.admin;

import com.example.demo.student.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;


public class managestudentcontroller extends menucontroller {
    @FXML
    public TreeView<String> studentSearchType;
    @FXML
    private TextField input;
    @FXML
    TableView<Student> studentTable;
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
        TableColumn<Student, String> column1 =
                new TableColumn<>("id");

        column1.setCellValueFactory(
                new PropertyValueFactory<>("id"));


        TableColumn<Student, String> column2 =
                new TableColumn<>("username");

        column2.setCellValueFactory(
                new PropertyValueFactory<>("username"));
        studentTable.getColumns().addAll(column1,column2);
        studentTable.getItems().add(new Student("1","dat","jav"));
        studentTable.getItems().add(new Student("2","dat2","java"));

//        username.setCellValueFactory(
//                TreeTableColumn.CellDataFeatures<Student, String> param) -> param.get
//        );
    }


    public void searchStudent(ActionEvent event) {
        List<Student> result = user.getStudentBy(op, input.getText());
        for (Student x:result) {
//            root1.getChildren().add(new TreeItem<>(x));
            System.out.println(x.getUsername() + " " + x.getName());
        }
//        studentTable.setRoot(root1);
//        studentTable.setShowRoot(true);
    }
}
