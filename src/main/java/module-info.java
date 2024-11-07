module org.example.banque {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    opens org.example.banque.classes to com.google.gson;


    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires com.google.gson;
    requires java.sql;

    opens org.example.banque to javafx.fxml;
    exports org.example.banque;
    exports org.example.banque.controllerclient;
    opens org.example.banque.controllerclient to javafx.fxml;
    exports org.example.banque.classes;
}