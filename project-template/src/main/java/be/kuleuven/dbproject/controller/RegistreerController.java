package be.kuleuven.dbproject.controller;

import be.kuleuven.dbproject.jdbi.Userjdbi;
import be.kuleuven.dbproject.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegistreerController {
    @FXML
    private Button btnRegistreer;
    @FXML
    private Button btnTerug;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField1;
    @FXML
    private PasswordField passwordField2;

    private final Userjdbi userJdbi = new Userjdbi();

    public void initialize() {
        btnRegistreer.setOnAction(e -> registerUser());
        btnTerug.setOnAction(e -> goBack());
    }

    private void registerUser() {
        String username = usernameField.getText();
        String password1 = passwordField1.getText();
        String password2 = passwordField2.getText();

        if (username.isBlank() || password1.isBlank() || password2.isBlank()) {
            showAlert("Error", "Vul alle velden in aub.");
            return;
        }

        if (!password1.equals(password2)) {
            showAlert("Error", "Wachtwoorden komen niet overeen.");
            return;
        }

        if (userJdbi.getUserByUsername(username) != null) {
            showAlert("Error", "Gebruikersnaam is al in gebruik.");
            return;
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password1);

        userJdbi.insert(newUser);

        showAlert("Success", "Registratie succesvol!");
    }

    private void goBack() {
        closeForm();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void closeForm() {
        Stage stage = (Stage) btnTerug.getScene().getWindow();
        stage.close();
    }
}
