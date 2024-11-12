package com.example.demo.admin;

import com.example.demo.student.student;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.util.List;


public class managestudentcontroller extends menucontroller {
    @FXML
    public TreeView<String> studentSearchType;
    @FXML
    private TextField input;
    @FXML
    TableView<student> studentTable;
    private String op;
    public static student onClickStudent = new student();
    @Override
    @FXML
    public void initialize() {
        home.setOnAction(event -> handleHomeAction(event));
        manageStudent.setOnAction(event -> handleManageStudentAction(event));
        manageBook.setOnAction(event -> handleManageBookAction(event));
        search.setOnAction(event -> handleSearchAction(event));
        handleRequest.setOnAction(event -> handleHandleRequestAction(event));
        TreeItem<String> root = new TreeItem<>("types");
        TreeItem<String> childItem1 = new TreeItem<>("ID");
        TreeItem<String> childItem2 = new TreeItem<>("Username");
        TreeItem<String> childItem3 = new TreeItem<>("name");
        root.getChildren().addAll(childItem1,childItem2,childItem3);
        studentSearchType.setRoot(root);
        studentSearchType.setPrefHeight(150);
//        studentSearchType.setShowRoot(true);
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
            }
        });

        TableColumn<student, String> column1 =
                new TableColumn<>("ID");
        TableColumn<student, String> column2 =
                new TableColumn<>("Username");
        TableColumn<student, String> column3 =
                new TableColumn<>("Name");
        TableColumn<student, String> column4 =
                new TableColumn<>("Classname");
        TableColumn<student, Void> actionColumn = new TableColumn<>("Action");

        column1.setCellValueFactory(
                new PropertyValueFactory<>("id"));
        column2.setCellValueFactory(
                new PropertyValueFactory<>("username"));
        column3.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        column4.setCellValueFactory(
                new PropertyValueFactory<>("classname")
        );
        Callback<TableColumn<student, Void>, TableCell<student, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<student, Void> call(final TableColumn<student, Void> param) {
                final TableCell<student, Void> cell = new TableCell<>() {
                    private Button deleteButton = new Button("Delete");
                    private Button detailButton = new Button("Detail");
                    private final HBox hbox = new HBox(10); // HBox để chứa các nút, khoảng cách giữa các nút là 10

                    {
                        deleteButton.getStyleClass().add("delete-button");
                        // Đặt sự kiện cho nút "Delete"
                        deleteButton.setOnAction(event -> {
                            student student = getTableView().getItems().get(getIndex());
                            student.deleteStudent(); // Gọi phương thức deleteStudent()
                        });

                        detailButton.getStyleClass().add("detail-button");
                        // Đặt sự kiện cho nút "Detail"
                        detailButton.setOnAction(event -> {
                            student student = getTableView().getItems().get(getIndex());
                            detailStudent(student.getStudent(), event);
                        });

                        // Thêm các nút vào HBox
                        hbox.getChildren().addAll(deleteButton, detailButton);
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(hbox); // Hiển thị HBox chứa các nút trong ô
                        }
                    }
                };
                cell.getStyleClass().add("table-student-detail-cell");
                return cell;
            }
        };
        column1.setCellFactory(param -> new TableCell<student, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                getStyleClass().add("table-student-id-cell");
            }
        });
        column2.setCellFactory(param -> new TableCell<student, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                getStyleClass().add("table-student-username-cell");
            }
        });
        column3.setCellFactory(param -> new TableCell<student, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                getStyleClass().add("table-student-name-cell");
            }
        });
        column4.setCellFactory(param -> new TableCell<student, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                getStyleClass().add("table-student-class-cell");
            }
        });
        // Đặt cellFactory cho cột actions
        actionColumn.setCellFactory(cellFactory);
        column1.getStyleClass().add("table-student-id-cell");
        column2.getStyleClass().add("table-student-username-cell");
        column3.getStyleClass().add("table-student-name-cell");
        column4.getStyleClass().add("table-student-class-cell");
        studentTable.getColumns().addAll(column1, column2, column3, column4, actionColumn);
    }


    public void searchStudent(ActionEvent event) {
        List<student> result = user.getStudentBy(op, input.getText());
        studentTable.setItems(FXCollections.observableArrayList());
        toggleTableViewVisibility(studentTable, true);
//        System.out.println(count++);
        for (student x:result) {
//            root1.getChildren().add(new TreeItem<>(x));
            studentTable.getItems().add(x);
            System.out.println(x.getUsername() + " " + x.getName());
        }
//        studentTable.setRoot(root1);
//        studentTable.setShowRoot(true);
    }
    public void toggleTableViewVisibility(TableView<?> tableView, boolean isVisible) {
//        boolean isVisible = tableView.isVisible();
        tableView.setVisible(isVisible);
        tableView.setManaged(isVisible);
    }
    public void detailStudent(student cur, ActionEvent event) {
        studentTable.setItems(FXCollections.observableArrayList());
        System.out.println(event.getTarget());
        onClickStudent = cur;
        displayScene(event,"DetailStudent.fxml");
    }

    public void createStudent(ActionEvent event) {
        displayScene(event,"CreateStudent.fxml");
    }
}
