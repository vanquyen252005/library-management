package com.example.demo.admin;

import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class HandleRequestController extends menucontroller{
    @FXML
    public TableView<Request> requestTable;
    @FXML
    public Button accept;
    @FXML
    private ProgressIndicator loadingSpinner;
    @FXML
    public void initialize() {
        super.initialize();
        super.handleRequest.getStyleClass().add("selected");
        super.home.getStyleClass().remove("selected");
//        System.out.println(user.getRole());
        TableColumn<Request, String> column1 =
                new TableColumn<>("ID");
        TableColumn<Request, String> column2 =
                new TableColumn<>("Book Id");
        TableColumn<Request, String> column3 =
                new TableColumn<>("User Id");
        TableColumn<Request, String> column4 =
                new TableColumn<>("RequestvDate");
        TableColumn<Request, String> column5 =
                new TableColumn<>("Status");

        column1.setCellValueFactory(
                new PropertyValueFactory<>("id"));
        column2.setCellValueFactory(
                new PropertyValueFactory<>("bookId"));
        column3.setCellValueFactory(
                new PropertyValueFactory<>("userId"));
        column4.setCellValueFactory(
                new PropertyValueFactory<>("requestDate"));
        column5.setCellValueFactory(
                new PropertyValueFactory<>("status"));
//        List<Request> requests = user.getRequestBook();
//         Hiển thị các yêu cầu trong table view
        requestTable.getColumns().addAll(column1, column2, column3, column4, column5);
loadRequests();
    }
    private void loadRequests() {
        // Bắt đầu spinner
        loadingSpinner.setVisible(true);

        // Tạo một task để tải dữ liệu yêu cầu trong background
        Task<List<Request>> task = new Task<List<Request>>() {
            @Override
            protected List<Request> call() throws Exception {
                return Request.getRequest();
            }
        };

        // Khi task hoàn thành
        task.setOnSucceeded(event -> {
            // Ẩn spinner
            loadingSpinner.setVisible(false);

            // Cập nhật dữ liệu vào TableView
            requestTable.setItems(FXCollections.observableArrayList(task.getValue()));
        });

        // Nếu task có lỗi
        task.setOnFailed(event -> {
            loadingSpinner.setVisible(false);
            task.getException().printStackTrace();
        });

        // Chạy task trong một thread riêng biệt
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    public void acceptRequest(ActionEvent event) {
        Request selectedRequest = requestTable.getSelectionModel().getSelectedItem();

        if (selectedRequest != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Accept Request");
            alert.setHeaderText("Are you sure you want to accept this request?");
            alert.setContentText("Book: " + selectedRequest.getBookId() +
                    "\nUser: " + selectedRequest.getUserId() +
                    "\nRequested on: " + selectedRequest.getRequestDate());

            ButtonType okButton = new ButtonType("OK");
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(okButton, cancelButton);

            alert.showAndWait().ifPresent(response -> {
                if (response == okButton) {
                    // Accept the request (update the status in DB)
                    selectedRequest.setStatus("approved");
                    selectedRequest.updateRequestStatus();
                    loadRequests();  // Reload the table after accepting the request
                }
            });
        } else {
            showError("No request selected", "Please select a request to accept.");
        }
    }

    private void showError(String noRequestSelected, String s) {
    }

    public void declineRequest(ActionEvent event) {
        Request selectedRequest = requestTable.getSelectionModel().getSelectedItem();

        if (selectedRequest != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Decline Request");
            alert.setHeaderText("Are you sure you want to decline this request?");
            alert.setContentText("Book: " + selectedRequest.getBookId() +
                    "\nUser: " + selectedRequest.getUserId() +
                    "\nRequested on: " + selectedRequest.getRequestDate());

            ButtonType okButton = new ButtonType("OK");
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(okButton, cancelButton);

            alert.showAndWait().ifPresent(response -> {
                if (response == okButton) {
                    // Cập nhật trạng thái trong DB
                    selectedRequest.setStatus("rejected");
                    selectedRequest.updateRequestStatus();
                    loadRequests();  // Tải lại bảng sau khi chấp nhận
                }
            });
        } else {
            showError("No request selected", "Please select a request to accept.");
        }
    }
}
