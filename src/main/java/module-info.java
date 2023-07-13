module com.acer.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.acer.main to javafx.fxml;
    exports com.acer.main;

    opens com.acer.main.bean to javafx.fxml;
    exports com.acer.main.bean;
    exports com.acer.main.controller;
    opens com.acer.main.controller to javafx.fxml;
}