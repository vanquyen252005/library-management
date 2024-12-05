package com.example.demo.admin;

import com.example.demo.student.Student;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;


public class ManageStudentController extends MenuController {
    @FXML
    private ComboBox<String> studentSearchType;
    @FXML
    private TextField inputField;
    @FXML
    TableView<Student> studentTable;
    @FXML
    private Button backButton;

    @FXML
    private Button forwardButton;

    @FXML
    private ListView<String> suggestionList;
    private String op;
    public static Student onClickStudent = new Student();
    @Override
    @FXML
    public void initialize() {
        super.initialize();
        homeButton.getStyleClass().remove("selected");
        manageStudent.getStyleClass().remove("select");
        manageBook.getStyleClass().remove("selected");
        handleRequest.getStyleClass().remove("selected");

        homeButton.getStyleClass().remove("pre");
        manageStudent.getStyleClass().remove("pre");
        manageBook.getStyleClass().remove("pre");
        handleRequest.getStyleClass().remove("pre");

        homeButton.getStyleClass().remove("after");
        manageStudent.getStyleClass().remove("after");
        manageBook.getStyleClass().remove("after");
        handleRequest.getStyleClass().remove("after");

        homeButton.getStyleClass().add("pre");
        manageStudent.getStyleClass().add("selected");
        manageBook.getStyleClass().add("after");
        studentSearchType.getItems().addAll("ID", "Username", "Name");
        studentSearchType.setOnAction(event -> {
            op = studentSearchType.getValue();
            System.out.println("Bạn đã chọn: " + op);
            switch (op) {
                case "ID":
                    op = "id";
                    break;
                case "Username":
                    op = "username";
                    break;
                case "Name":
                    op = "name";
                    break;
            }
        });
        super.manageBook.getStyleClass().remove("selected");
        super.homeButton.getStyleClass().remove("selected");
        super.manageStudent.getStyleClass().add("selected");
        super.handleRequest.getStyleClass().remove("selected");
        TableColumn<Student, String> column1 =
                new TableColumn<>("ID");
        TableColumn<Student, String> column2 =
                new TableColumn<>("Username");
        TableColumn<Student, String> column3 =
                new TableColumn<>("Name");
        TableColumn<Student, String> column4 =
                new TableColumn<>("ClassName");
        TableColumn<Student, Void> actionColumn = new TableColumn<>("Action");

        column1.setCellValueFactory(
                new PropertyValueFactory<>("id"));
        column2.setCellValueFactory(
                new PropertyValueFactory<>("username"));
        column3.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        column4.setCellValueFactory(
                new PropertyValueFactory<>("className")
        );
        Callback<TableColumn<Student, Void>, TableCell<Student, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Student, Void> call(final TableColumn<Student, Void> param) {
                final TableCell<Student, Void> cell = new TableCell<>() {
                    private Button deleteButton = new Button("Delete");
                    private Button detailButton = new Button("Detail");
                    private Button updateButton = new Button("Update");
                    private final HBox hbox = new HBox(10); // HBox để chứa các nút, khoảng cách giữa các nút là 10

                    {
                        deleteButton.getStyleClass().add("delete-button");
                        // Đặt sự kiện cho nút "Delete"
                        deleteButton.setOnAction(event -> {
                            Student student = getTableView().getItems().get(getIndex());
                            student.deleteStudent(student.getId()); // Gọi phương thức deleteStudent()
                            searchStudent();
                        });

                        detailButton.getStyleClass().add("detail-button");
                        // Đặt sự kiện cho nút "Detail"
                        detailButton.setOnAction(event -> {
                            Student student = getTableView().getItems().get(getIndex());
                            detailStudent(student.getStudent(), event);
                        });
                        updateButton.getStyleClass().add("button-update");
                        // Đặt sự kiện cho nút "Detail"
                        updateButton.setOnAction(event -> {
                            Student student = getTableView().getItems().get(getIndex());
                            updateStudent(student.getStudent(), event);
                        });

                        // Thêm các nút vào HBox
                        hbox.getChildren().addAll(deleteButton, detailButton, updateButton);
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
        column1.setCellFactory(param -> new TableCell<Student, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                getStyleClass().add("table-student-id-cell");
            }
        });
        column2.setCellFactory(param -> new TableCell<Student, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                getStyleClass().add("table-student-username-cell");
            }
        });
        column3.setCellFactory(param -> new TableCell<Student, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                getStyleClass().add("table-student-name-cell");
            }
        });
        column4.setCellFactory(param -> new TableCell<Student, String>() {
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
        actionColumn.getStyleClass().add("table-student-detail-cell");
        studentTable.getColumns().addAll(column1, column2, column3, column4, actionColumn);

        suggestionList.setVisible(false);
        inputField.setOnKeyTyped(this::onKeyTyped);
        suggestionList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                 inputField.setText(suggestionList.getSelectionModel().getSelectedItem());
                System.out.println("Clicked: " + inputField.getText());
                suggestionList.setVisible(false);
                searchStudent();
            }
        });
    }

    private void onKeyTyped(KeyEvent event) {
        String searchText = inputField.getText().trim();
        if (searchText.isEmpty()) {
            suggestionList.setVisible(false);
            return;
        }
        Task<List<String>> task = new Task<>() {
            @Override
            protected List<String> call() throws Exception {
                return getSearchSuggestions(searchText);
            }
        };

        task.setOnSucceeded(e -> {
            List<String> suggestions = task.getValue();
            suggestionList.setItems(FXCollections.observableArrayList(suggestions));
            suggestionList.setVisible(!suggestions.isEmpty());
        });

        task.setOnFailed(e -> task.getException().printStackTrace());

        new Thread(task).start();
    }

    private List<String> getSearchSuggestions(String searchText) {
        List<String> suggestionList = new ArrayList<>();
        List<Student> studentList = Student.getStudentBy(op, searchText);
        switch (op) {
            case "id":
                for (Student x : studentList) {
                    suggestionList.add(x.getId());
                }
                break;
            case "username":
                for (Student x : studentList) {
                    suggestionList.add(x.getUsername());
                }
                break;
            case "name":
                for (Student x : studentList) {
                    suggestionList.add(x.getName());
                }
                break;
        }
        return suggestionList;
    }
    @FXML
    public void searchStudent() {
        List<Student> resultList = Student.getStudentBy(op, inputField.getText());
        studentTable.setItems(FXCollections.observableArrayList());
        toggleTableViewVisibility(studentTable, true);
        for (Student x:resultList) {
            studentTable.getItems().add(x);
            System.out.println(x.getUsername() + " " + x.getName());
        }
    }
    public void toggleTableViewVisibility(TableView<?> tableView, boolean isVisible) {
        tableView.setVisible(isVisible);
        tableView.setManaged(isVisible);
    }
    public void detailStudent(Student curStudent, ActionEvent event) {
        studentTable.setItems(FXCollections.observableArrayList());
        System.out.println(event.getTarget());
        onClickStudent = curStudent;
        displayScene(event,"DetailStudent.fxml");
    }
    public void createStudent(ActionEvent event) {
        displayScene(event,"CreateStudent.fxml");
    }

    public void updateStudent(Student curStudent, ActionEvent event){
        studentTable.setItems(FXCollections.observableArrayList());
        System.out.println(event.getTarget());
        onClickStudent = curStudent;
        displayScene(event,"UpdateStudent.fxml");
    }
}
