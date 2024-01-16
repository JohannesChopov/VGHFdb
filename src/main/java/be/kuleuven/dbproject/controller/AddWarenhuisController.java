package be.kuleuven.dbproject.controller;

import be.kuleuven.dbproject.model.Warenhuis;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddWarenhuisController {
    @FXML
    private TextField naamField;
    @FXML
    private TextField adresField;
    @FXML
    private Button addWarenhuis;

    private Warenhuis nieuwWarenhuis;
    private boolean submitted = false;

    public void initialize() {
        addWarenhuis.setOnAction(e -> handleAddBtn());
    }

    private void handleAddBtn() {
        // Update the game details based on the form fields
        nieuwWarenhuis = new Warenhuis(naamField.getText(), adresField.getText());
        submitted = true;
        closeForm();
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public Warenhuis getNieuwWarenhuis() {
        return nieuwWarenhuis;
    }

    private void closeForm() {
        Stage stage = (Stage) addWarenhuis.getScene().getWindow();
        stage.close();
    }

}
