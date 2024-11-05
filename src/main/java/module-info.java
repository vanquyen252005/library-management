module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires jdk.compiler;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
    exports com.example.demo.admin;
    opens com.example.demo.admin to javafx.fxml;
    exports com.example.demo.user;
    opens com.example.demo.user to javafx.fxml;
    exports com.example.demo.student;
    opens com.example.demo.student to javafx.fxml;
}