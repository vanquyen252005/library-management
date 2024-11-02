package org.example.LibraryManagement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea; // Sử dụng JavaFX TextArea

import java.util.ArrayList;
import java.util.List;


public class HelloController {
    @FXML
    private Button addButton;
    @FXML
    private Button searchButton;
    @FXML
    private VBox contentBox;
    @FXML
    private TextField add_buck;
    @FXML
    private TextArea add_printOut;
    @FXML
    private TextField search_buck;
    @FXML
    private TextArea search_printOut;
    @FXML
    private Button OK;
    @FXML
    private Button Delete;

    private final ApiService apiService = new ApiService();

    private final ConnectDB Database = new ConnectDB();

    public void add_function(ActionEvent e){
        System.out.println("press add");
        add_buck.setVisible(true);
        add_printOut.setVisible(true);
    }



    public void search_function(ActionEvent e){
        System.out.println("press search");
        search_buck.setVisible(true);
        search_printOut.setVisible(true);
        OK.setVisible(true);
        Delete.setVisible(true);

    }

    public void Delete_Button(ActionEvent e){
        System.out.println("press delete in search function");
        search_printOut.setText("");
        search_buck.setText("");

    }

    public void clickOKButton(ActionEvent e) {
        String query = search_buck.getText();
        if (!query.isEmpty()) {
            List<Book> BookList = new ArrayList<>();
            BookList = Database.searchDocuments(query);
            String ShowUp ="";
            for(Book x : BookList) {
                ShowUp += x.toString() +'\n';
            }
            search_printOut.setText(ShowUp);
        }
        else {
            System.out.println("query is not valid");
        }
    }
}
