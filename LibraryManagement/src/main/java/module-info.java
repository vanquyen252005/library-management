module org.example.LibraryManagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.google.gson;
    requires java.desktop;
    requires java.sql;


    opens org.example.LibraryManagement to javafx.fxml;
    exports org.example.LibraryManagement;
}
