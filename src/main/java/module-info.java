module com.example.projectf {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.projectf to javafx.fxml;
    exports com.example.projectf;
    exports com.example.projectf.controller;
    opens com.example.projectf.controller to javafx.fxml;
}