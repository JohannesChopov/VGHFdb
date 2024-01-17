package be.kuleuven.dbproject.controller;

import be.kuleuven.dbproject.jdbi.Donatiejdbi;
import be.kuleuven.dbproject.jdbi.Museumjdbi;
import be.kuleuven.dbproject.model.Donatie;
import be.kuleuven.dbproject.model.Locatie;
import be.kuleuven.dbproject.model.Museum;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AddDonatieController implements AddItemController<Donatie>{
    @FXML
    private TextField somField;
    @FXML
    private DatePicker datumField;
    @FXML
    private ChoiceBox<Museum> museumIDField;
    @FXML
    private Button addDonatie;

    private Donatie nieuweDonatie;
    private boolean submitted = false;
    private final Museumjdbi museumjdbi = new Museumjdbi();

    @Override
    public void initialize() {
        addDonatie.setOnAction(e -> handleAddBtn());
        museumIDField.setItems(FXCollections.observableArrayList(museumjdbi.getAll()));
    }

    private void handleAddBtn() {
        Locatie selectedLocatie = museumIDField.getValue();
        int locatieID = selectedLocatie.getID();
        String somText = somField.getText();
        double som = 0.0;
        try {
            som = Double.parseDouble(somText);
        } catch (NumberFormatException e) {
            System.err.println("Invalid input. Please enter a valid integer.");
        }
        nieuweDonatie = new Donatie(locatieID, som, datumField.getValue().toString());
        submitted = true;
        closeForm();
    }
    @Override
    public boolean isSubmitted() {
        return submitted;
    }
    @Override
    public Donatie getNewItem() {
        return nieuweDonatie;
    }

    private void closeForm() {
        Stage stage = (Stage) addDonatie.getScene().getWindow();
        stage.close();
    }
}
