package be.kuleuven.dbproject.controller;

import be.kuleuven.dbproject.ProjectMain;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProjectMainController {

    @FXML
    private Button btnBeheer;
    @FXML
    private Button btnOverzicht;
    @FXML
    private Button btnLogUit;
    @FXML
    private Button btnRegistreer;
    @FXML
    private Button btnGebruikers;

    public void initialize() {
        btnBeheer.setOnAction(e -> showScherm("scherm1"));
        btnOverzicht.setOnAction(e -> showScherm("scherm2"));
        btnRegistreer.setOnAction(e -> showScherm("registreer"));
        btnGebruikers.setOnAction(e -> showScherm("gebruikers"));
        btnLogUit.setOnAction(e -> gaTerug());
    }

    private void showScherm(String id) {
        var resourceName = id + ".fxml";
        try {
            var stage = new Stage();
            var root = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource(resourceName));
            var scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Admin " + id);
            stage.initOwner(ProjectMain.getRootStage());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();

        } catch (Exception e) {
            throw new RuntimeException("Kan beheerscherm " + resourceName + " niet vinden", e);
        }
    }

    private void gaTerug() {
        var resourceName = "login.fxml";
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
