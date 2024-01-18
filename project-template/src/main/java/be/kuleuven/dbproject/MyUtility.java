package be.kuleuven.dbproject;

import javafx.scene.control.Alert;

public class MyUtility {
    public static void showAlert(String title, String content) {
        var alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
