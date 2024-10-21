module org.example.LibraryManagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.google.gson;
    requires java.desktop;


    opens org.example.LibraryManagement to javafx.fxml;
    exports org.example.LibraryManagement;
}
