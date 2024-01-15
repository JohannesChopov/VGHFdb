package be.kuleuven.dbproject.controller;

import be.kuleuven.dbproject.jdbi.Donatiejdbi;
import be.kuleuven.dbproject.model.Donatie;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddDonatieController {
    @FXML
    private TextField somField;
    @FXML
    private TextField datumField;
    @FXML
    private TextField museumIDField;
    @FXML
    private Button addBtn;

    private Donatie nieuweDonatie;
    private boolean submitted = false;

    public void initialize() {
        addBtn.setOnAction(e -> handleAddBtn());
    }

    private void handleAddBtn() {
        // Update the game details based on the form fields

        //nieuweDonatie = new Donatie(museumIDField.getText().to, somField.getText(), datumField.getText());
        submitted = true;
        closeForm();
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public Donatie getNewDonatie() {
        return nieuweDonatie;
    }

    private void closeForm() {
        Stage stage = (Stage) addBtn.getScene().getWindow();
        stage.close();
    }
}
