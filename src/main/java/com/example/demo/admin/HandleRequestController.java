package com.example.demo.admin;

import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.List;

import static java.lang.Integer.parseInt;

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
        {home.getStyleClass().remove("selected");
        manageStudent.getStyleClass().remove("select");
        manageBook.getStyleClass().remove("selected");
        handleRequest.getStyleClass().remove("selected");

        home.getStyleClass().remove("pre");
        manageStudent.getStyleClass().remove("pre");
        manageBook.getStyleClass().remove("pre");
        handleRequest.getStyleClass().remove("pre");

        home.getStyleClass().remove("after");
        manageStudent.getStyleClass().remove("after");
        manageBook.getStyleClass().remove("after");
        handleRequest.getStyleClass().remove("after");

        manageBook.getStyleClass().add("pre");
        handleRequest.getStyleClass().add("selected");}
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
        TableColumn<Request, String> column6 =
                new TableColumn<>("Type");

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
        column6.setCellValueFactory(
                new PropertyValueFactory<>("type"));
//        List<Request> requests = user.getRequestBook();
//         Hiển thị các yêu cầu trong table view
        requestTable.getColumns().addAll(column1, column2, column3, column4, column5, column6);
        loadRequests();
    }
    private void loadRequests() {
        List<Request> requests = Request.getRequest();
            // Cập nhật dữ liệu vào TableView
            requestTable.setItems(FXCollections.observableArrayList(requests));
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

            TextField day = new TextField();
            Label label = new Label("Return this book after 30 days or");
            TextFlow textFlow = new TextFlow(label, day);
            alert.getDialogPane().setContent(textFlow);
            ButtonType okButton = new ButtonType("OK");
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(okButton, cancelButton);
            alert.showAndWait().ifPresent(response -> {
                if (response == okButton) {
                    // Accept the request (update the status in DB)
                    selectedRequest.setStatus("approved");
                    String date = "30";
                    if (day.getText().trim().matches("\\d+")) {
                        date = day.getText();
                    }
                    selectedRequest.setReturnDate(date);
                    selectedRequest.updateRequestStatus();
                    loadRequests();  // Reload the table after accepting the request
                }
            });
        } else {
            showError("No request selected", "Please select a request to accept.");
        }
    }

    private void showError(String error, String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText("Đã xảy ra lỗi!");
        alert.setContentText(error);

        // Hiển thị Alert và chờ người dùng đóng
        alert.showAndWait();
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
