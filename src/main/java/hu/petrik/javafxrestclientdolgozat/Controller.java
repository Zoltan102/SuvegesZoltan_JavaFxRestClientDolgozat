package hu.petrik.javafxrestclientdolgozat;

import javafx.scene.control.Alert;

public class Controller {
    protected void error(String headerText) {
        error(headerText, "");
    }

    protected void error(String headerText, String contentText) {
        alert(Alert.AlertType.ERROR, headerText, contentText);
    }

    protected void warning(String headerText) {
        alert(Alert.AlertType.WARNING, headerText, "");
    }

    protected void alert(Alert.AlertType alertType, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
