package be.kuleuven.dbproject.controller;

import be.kuleuven.dbproject.jdbi.Museumjdbi;
import be.kuleuven.dbproject.model.Bezoeker;
import be.kuleuven.dbproject.model.Donatie;
import be.kuleuven.dbproject.model.Locatie;
import be.kuleuven.dbproject.model.Museum;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddBezoekerController implements AddItemController<Bezoeker>{
    @FXML
    private TextField naamField;
    @FXML
    private ChoiceBox<Museum> museumIDField;
    @FXML
    private Button addBezoeker;

    private Bezoeker nieuweBezoeker;
    private boolean submitted = false;
    private final Museumjdbi museumjdbi = new Museumjdbi();

    @Override
    public void initialize() {
        addBezoeker.setOnAction(e -> handleAddBtn());
        museumIDField.setItems(FXCollections.observableArrayList(museumjdbi.getAll()));

    }

    private void handleAddBtn() {
        Locatie selectedLocatie = museumIDField.getValue();
        int locatieID = selectedLocatie.getID();

        // Update the game details based on the form fields
        nieuweBezoeker = new Bezoeker(locatieID, naamField.getText());
        submitted = true;
        closeForm();
    }
    @Override
    public boolean isSubmitted() {
        return submitted;
    }

    @Override
    public Bezoeker getNewItem() {
        return nieuweBezoeker;
    }

    private void closeForm() {
        Stage stage = (Stage) addBezoeker.getScene().getWindow();
        stage.close();
    }
}
