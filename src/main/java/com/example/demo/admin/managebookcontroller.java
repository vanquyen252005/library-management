package com.example.demo.admin;

import com.example.demo.book.Book;
import com.example.demo.book.ConnectDB;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.List;

public class managebookcontroller {

    @FXML
    private Button addButton, searchButton, OK, Delete;
    @FXML
    private VBox contentBox;
    @FXML
    private TextField Title_field, Author_field, PublishYear_field, Publisher_field, ISBN_field;
    @FXML
    private TextArea Show;

    private static int func_route;
    private final ConnectDB BookDatabase = new ConnectDB();

    public void ClickDeleteButton(ActionEvent e) {
        clearFields();
        System.out.println("press delete");
    }

    public void clickOKButton(ActionEvent e) {
        System.out.println("you clicked on OK button");
        String ISBN = ISBN_field.getText();
        String title = Title_field.getText();
        String author = Author_field.getText();
        String publishYear = PublishYear_field.getText();
        String publisher = Publisher_field.getText();

        switch (func_route) {
            case 1 ->
            {
                System.out.println("process when clicking OK button on Route:Add Document");
                BookDatabase.addDocument(title, author, publishYear, publisher);
                Show.setText("Document added successfully.");
            }
            case 2 -> searchDocument(title);
            case 3 ->
            {
                System.out.println("processing to edit document with ISBN:" + ISBN);
                BookDatabase.editDocument(ISBN,title,author,publisher,publishYear);
                System.out.println("Managing to edit the document");
            }
            case 4 -> BookDatabase.deleteDocument(ISBN);
            default -> Show.setText("Invalid operation selected.");
        }
    }

    private void searchDocument(String title) {
        System.out.println("process when clicking OK button on Route:Search Document");
        if (!title.isEmpty()) {
            System.out.println("Querying the database for title: " + title);
            Task<List<Book>> task = new Task<>() {
                @Override
                protected List<Book> call() throws Exception {
                    return BookDatabase.searchDocuments(title);
                }
            };

            task.setOnSucceeded(event -> {
                System.out.println("Database query completed.");
                List<Book> bookList = task.getValue();
                if (bookList != null && !bookList.isEmpty()) {
                    StringBuilder result = new StringBuilder();
                    for (Book book : bookList) {
                        result.append(book).append(System.lineSeparator());
                    }
                    System.out.println("Preparing results to display.");
                    Show.setText(result.toString());
                } else {
                    Show.setText("No books found with the specified title.");
                }
            });

            task.setOnFailed(event -> {
                Show.setText("Error occurred while searching the database.");
                task.getException().printStackTrace();
            });

            new Thread(task).start();
        } else {
            Show.setText("Query not valid");
        }
    }

    private void clearFields() {
        ISBN_field.setText("");
        Title_field.setText("");
        Author_field.setText("");
        Publisher_field.setText("");
        PublishYear_field.setText("");
        Show.setText("");
    }

    public void add_function(ActionEvent e) {
        func_route = 1;
        System.out.println(func_route);
    }

    public void search_function(ActionEvent e) {
        func_route = 2;
        System.out.println(func_route);
    }

    public void alert_function(ActionEvent e) {
        func_route = 3;
        System.out.println(func_route);
    }

    public void dump_function(ActionEvent e) {
        func_route = 4;
        System.out.println(func_route);
    }
}
