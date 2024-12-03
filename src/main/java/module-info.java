module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires jdk.compiler;
    requires java.net.http;
    requires org.json;
    requires com.google.zxing;
    requires com.google.zxing.javase;
    requires javafx.media;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
    exports com.example.demo.admin;
    opens com.example.demo.admin to javafx.fxml;
    exports com.example.demo.user;
    opens com.example.demo.user to javafx.fxml;
    exports com.example.demo.student;
    opens com.example.demo.student to javafx.fxml;
    opens com.example.demo.book to javafx.fxml;  // Mở gói cho javafx.base
    exports com.example.demo.book;
    exports com.example.demo.DesignPattern.Command;
    opens com.example.demo.DesignPattern.Command to javafx.fxml;
    opens com.example.demo.DesignPattern.Singleton to javafx.base;
    exports com.example.demo.DesignPattern.Singleton;

}