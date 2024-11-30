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


public class managestudentcontroller extends menucontroller {
    @FXML
    private ComboBox<String> studentSearchType;
    @FXML
    private TextField input;
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
        super.home.getStyleClass().remove("selected");
        super.manageStudent.getStyleClass().add("selected");
        super.handleRequest.getStyleClass().remove("selected");
//        backButton.setOnAction(this::handleBackAction);
//        forwardButton.setOnAction(this::handleForwardAction);
        TableColumn<Student, String> column1 =
                new TableColumn<>("ID");
        TableColumn<Student, String> column2 =
                new TableColumn<>("Username");
        TableColumn<Student, String> column3 =
                new TableColumn<>("Name");
        TableColumn<Student, String> column4 =
                new TableColumn<>("Classname");
        TableColumn<Student, Void> actionColumn = new TableColumn<>("Action");

        column1.setCellValueFactory(
                new PropertyValueFactory<>("id"));
        column2.setCellValueFactory(
                new PropertyValueFactory<>("username"));
        column3.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        column4.setCellValueFactory(
                new PropertyValueFactory<>("classname")
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
                        updateButton.getStyleClass().add("update-button");
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

        suggestionList.setVisible(false); // Ẩn gợi ý ban đầu
        input.setOnKeyTyped(this::onKeyTyped); // Lắng nghe sự kiện khi gõ
        suggestionList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // Single click
                 input.setText(suggestionList.getSelectionModel().getSelectedItem());
                System.out.println("Clicked: " + input.getText());
                suggestionList.setVisible(false);
                searchStudent();
            }
        });
    }

    private void onKeyTyped(KeyEvent event) {
        String searchText = input.getText().trim();
//        System.out.println(searchText);
        if (searchText.isEmpty()) {
            suggestionList.setVisible(false); // Ẩn khi không có từ khóa
            return;
        }
        // Tạo một Task để lấy các gợi ý không đồng bộ
        Task<List<String>> task = new Task<>() {
            @Override
            protected List<String> call() throws Exception {
                return getSearchSuggestions(searchText); // Lấy gợi ý từ cơ sở dữ liệu
            }
        };

        // Khi tìm kiếm hoàn thành, cập nhật danh sách gợi ý
        task.setOnSucceeded(e -> {
            List<String> suggestions = task.getValue();
            suggestionList.setItems(FXCollections.observableArrayList(suggestions));
            suggestionList.setVisible(!suggestions.isEmpty()); // Hiển thị nếu có gợi ý
        });

        // Nếu có lỗi
        task.setOnFailed(e -> task.getException().printStackTrace());

        // Chạy Task trong một luồng riêng
        new Thread(task).start();
    }

    private List<String> getSearchSuggestions(String searchText) {
        List<String> suggestions = new ArrayList<>();
        List<Student> cur=  Student.getStudentBy(op, searchText);
        switch (op) {
            case "id":
                for(Student x:cur) {
                    suggestions.add(x.getId());
                }
                break;
            case "username":
                for(Student x:cur) {
                    suggestions.add(x.getUsername());
                }
                break;
            case "name":
                for(Student x:cur) {
                    suggestions.add(x.getName());
                }
                break;
        }
        return suggestions;
    }
    @FXML
    public void searchStudent() {
        List<Student> result = Student.getStudentBy(op, input.getText());
        studentTable.setItems(FXCollections.observableArrayList());
        toggleTableViewVisibility(studentTable, true);
//        System.out.println(count++);
        for (Student x:result) {
//            root1.getChildren().add(new TreeItem<>(x));
            studentTable.getItems().add(x);
            System.out.println(x.getUsername() + " " + x.getName());
        }
    }
    public void toggleTableViewVisibility(TableView<?> tableView, boolean isVisible) {
//        boolean isVisible = tableView.isVisible();
        tableView.setVisible(isVisible);
        tableView.setManaged(isVisible);
    }
    public void detailStudent(Student cur, ActionEvent event) {
        studentTable.setItems(FXCollections.observableArrayList());
        System.out.println(event.getTarget());
        onClickStudent = cur;
        displayScene(event,"DetailStudent.fxml");
    }
    public void createStudent(ActionEvent event) {
        displayScene(event,"CreateStudent.fxml");
    }
    public void updateStudent(Student cur, ActionEvent event){
        studentTable.setItems(FXCollections.observableArrayList());
        System.out.println(event.getTarget());
        onClickStudent = cur;
        displayScene(event,"UpdateStudent.fxml");
    }
}
