package be.kuleuven.dbproject.controller;

import be.kuleuven.dbproject.ProjectMain;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Scherm2Controller {
    @FXML
    private Button btnGameOverzicht;
    @FXML
    private Button btnPlatformOverzicht;
    @FXML
    private Button btnBezoekerOverzicht;
    @FXML
    private Button btnDonatieOverzicht;
    @FXML
    private Button btnMuseumOverzicht;
    @FXML
    private Button btnWarenhuisOverzicht;
    @FXML
    private Button btnTotaalOverzicht;
    @FXML
    private Button btnClose;


    public void initialize() {
        btnGameOverzicht.setOnAction(e -> showOverzichtScherm("Game"));
        btnPlatformOverzicht.setOnAction(e -> showOverzichtScherm("Platform"));
        btnBezoekerOverzicht.setOnAction(e -> showOverzichtScherm("Bezoeker"));
        btnWarenhuisOverzicht.setOnAction(e -> showOverzichtScherm("Warenhuis"));
        btnMuseumOverzicht.setOnAction(e -> showOverzichtScherm("Museum"));
        btnClose.setOnAction(e -> {
            var stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        });
    }

    private void showOverzichtScherm(String id) {
        var resourceName = "overzicht" + id + ".fxml";
        try {
            var stage = new Stage();
            var root = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("Overzicht/" + resourceName));
            var scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(id + "overzicht");
            stage.initOwner(ProjectMain.getRootStage());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();

        } catch (Exception e) {
            throw new RuntimeException("Kan beheerscherm " + resourceName + " niet vinden", e);
        }
    }

}
