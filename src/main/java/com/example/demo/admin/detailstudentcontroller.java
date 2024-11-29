package com.example.demo.admin;

import com.example.demo.book.Book_borrowed;
import com.example.demo.student.Student;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class detailstudentcontroller extends menucontroller {
    @FXML
    public Pane detailStudent;
    @FXML
    public TableView borrowingBook;
    @FXML
    public TableView requestBook1;
    @FXML
    public Text name;
    @FXML
    public Text id;
    @FXML
    public Text Class;
    @FXML
    public Text phone;
    @FXML
    public TreeView<String> miniBar;
    @FXML
    private Student curStudent = managestudentcontroller.onClickStudent;

    @Override
    @FXML
    public void initialize() {
        super.initialize();
        super.manageStudent.getStyleClass().add("selected");

        TableColumn<Book_borrowed, String> column1 =
                new TableColumn<>("ISBN");
        TableColumn<Book_borrowed, String> column2 =
                new TableColumn<>("Title");
        TableColumn<Book_borrowed, String> column3 =
                new TableColumn<>("borrow_date");
        TableColumn<Book_borrowed, String> column4 =
                new TableColumn<>("return_date");
//        TableColumn<Book_borrowed, Void> actionColumn = new TableColumn<>("Action");

        column1.setCellValueFactory(
                new PropertyValueFactory<>("ISBN"));
        column2.setCellValueFactory(
                new PropertyValueFactory<>("Title"));
        column3.setCellValueFactory(
                new PropertyValueFactory<>("borrow_date"));
        column4.setCellValueFactory(
                new PropertyValueFactory<>("return_date")
        );
//        Callback<TableColumn<Book_borrowed, Void>, TableCell<Book_borrowed, Void>> cellFactory = new Callback<>() {
//            @Override
//            public TableCell<Book_borrowed, Void> call(final TableColumn<Book_borrowed, Void> param) {
//                final TableCell<Book_borrowed, Void> cell = new TableCell<>() {
//                    private Button deleteButton = new Button("Delete");
//                    private Button detailButton = new Button("Detail");
//                    private final HBox hbox = new HBox(10); // HBox để chứa các nút, khoảng cách giữa các nút là 10
//
//                    {
//                        deleteButton.getStyleClass().add("delete-button");
//                        // Đặt sự kiện cho nút "Delete"
//                        deleteButton.setOnAction(event -> {
//                            Book_borrowed student = getTableView().getItems().get(getIndex());
//                            student.deleteStudent(student.getId()); // Gọi phương thức deleteStudent()
//                        });
//
//                        detailButton.getStyleClass().add("detail-button");
//                        // Đặt sự kiện cho nút "Detail"
//                        detailButton.setOnAction(event -> {
//                            Book_borrowed student = getTableView().getItems().get(getIndex());
//
//                        });
//
//                        // Thêm các nút vào HBox
//                        hbox.getChildren().addAll(deleteButton, detailButton);
//                    }
//
//                    @Override
//                    protected void updateItem(Void item, boolean empty) {
//                        super.updateItem(item, empty);
//                        if (empty) {
//                            setGraphic(null);
//                        } else {
//                            setGraphic(hbox); // Hiển thị HBox chứa các nút trong ô
//                        }
//                    }
//                };
//                cell.getStyleClass().add("table-student-detail-cell");
//                return cell;
//            }
//        };
        column1.setCellFactory(param -> new TableCell<Book_borrowed, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                getStyleClass().add("table-student-id-cell");
            }
        });
        column2.setCellFactory(param -> new TableCell<Book_borrowed, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                getStyleClass().add("table-student-username-cell");
            }
        });
        column3.setCellFactory(param -> new TableCell<Book_borrowed, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                getStyleClass().add("table-student-name-cell");
            }
        });
        column4.setCellFactory(param -> new TableCell<Book_borrowed, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                getStyleClass().add("table-student-class-cell");
            }
        });
        // Đặt cellFactory cho cột actions
//        actionColumn.setCellFactory(cellFactory);
        column1.getStyleClass().add("table-student-id-cell");
        column2.getStyleClass().add("table-student-username-cell");
        column3.getStyleClass().add("table-student-name-cell");
        column4.getStyleClass().add("table-student-class-cell");
        borrowingBook.getColumns().addAll(column1, column2, column3, column4);
        loadBook();
    }

    private void loadBook() {
        ArrayList<Book_borrowed> cur = curStudent.getBorrowingBook();
        for (Book_borrowed x:cur) {
            borrowingBook.getItems().add(x);
            System.out.println(x.getTitle());
        }
    }

}
