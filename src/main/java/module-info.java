module com.example.project2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
//    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
//    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires java.management;

    opens com.example.project2 to javafx.fxml;
    exports com.example.project2;
    exports com.example.project2.entities;
    opens com.example.project2.entities to javafx.fxml;
    exports com.example.project2.function;
    opens com.example.project2.function to javafx.fxml;
    exports com.example.project2.menu;
    opens com.example.project2.menu to javafx.fxml;
}