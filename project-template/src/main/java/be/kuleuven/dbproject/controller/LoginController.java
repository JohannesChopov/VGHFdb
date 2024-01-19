package be.kuleuven.dbproject.controller;

import be.kuleuven.dbproject.ProjectMain;
import be.kuleuven.dbproject.jdbi.Userjdbi;
import be.kuleuven.dbproject.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import static be.kuleuven.dbproject.MyUtility.showAlert;

public class LoginController {
    @FXML
    private Button btnLogin;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private final Userjdbi userJdbi = new Userjdbi();

    public void initialize() {
        btnLogin.setOnAction(e -> {
            if (usernameField.getText().isBlank() || passwordField.getText().isBlank()) {
                showAlert("Error", "Vul velden in aub");
            }
            else {
                login(usernameField.getText(),passwordField.getText());
            }
        });
    }

    private void login(String username, String password) {
        System.out.println(username + password);
        if (username.equals("admin") && password.equals("admin")) {
            gaVerder();
        }
        else {
            User user = userJdbi.getUserByUsername(username);

            if ((user != null && user.getPassword().equals(password))) {
                gaVerder();
            } else {
                showAlert("Error", "Ongeldige gebruikersnaam of wachtwoord");
            }
        }
    }

    private void gaVerder() {
        var resourceName = "main.fxml";
        try {
            var loader = new FXMLLoader(getClass().getClassLoader().getResource(resourceName));
            var newRoot = (AnchorPane) loader.load();
            var rootStage = ProjectMain.getRootStage();
            rootStage.setScene(new Scene(newRoot));
            rootStage.setTitle("Admin");

        } catch (Exception e) {
            throw new RuntimeException("Kan beheerscherm " + resourceName + " niet vinden", e);
        }
    }

}
