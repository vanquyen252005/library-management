module org.example.mainscene {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.net.http;
    requires java.desktop;
    requires org.json;
    requires com.google.zxing;
    requires com.google.zxing.javase;


    opens org.example.mainscene to javafx.fxml;
    exports org.example.mainscene;
}