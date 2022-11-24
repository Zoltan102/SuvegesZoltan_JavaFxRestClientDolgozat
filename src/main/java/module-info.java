module hu.petrik.javafxrestclientdolgozat {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens hu.petrik.javafxrestclientdolgozat to javafx.fxml;
    exports hu.petrik.javafxrestclientdolgozat;
}