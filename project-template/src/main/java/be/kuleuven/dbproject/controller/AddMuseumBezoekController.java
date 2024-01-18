package be.kuleuven.dbproject.controller;

import be.kuleuven.dbproject.jdbi.Bezoekerjdbi;
import be.kuleuven.dbproject.jdbi.MuseumBezoekjdbi;
import be.kuleuven.dbproject.jdbi.Museumjdbi;
import be.kuleuven.dbproject.model.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static be.kuleuven.dbproject.MyUtility.showAlert;

public class AddMuseumBezoekController implements AddItemController<MuseumBezoek> {
    @FXML
    private TextField naamField;
    @FXML
    private ChoiceBox<Museum> museumIDField;
    @FXML
    private Button addBezoeker;
    @FXML
    private DatePicker datumField;

    private Bezoekerjdbi bezoekerjdbi = new Bezoekerjdbi();
    private MuseumBezoek nieuwBezoek;
    private Bezoeker nieuweBezoeker;
    private boolean submitted = false;
    private final Museumjdbi museumjdbi = new Museumjdbi();

    @Override
    public void initialize() {
        museumIDField.setItems(FXCollections.observableArrayList(museumjdbi.getAll()));
        addBezoeker.setOnAction(e -> handleAddBtn());
    }

    private void handleAddBtn() {
        if (naamField.getText().isBlank()) {
            showAlert("Error", "Vul velden in aub");
        }
        else {
            String naam = naamField.getText();
            Bezoeker bezoeker = bezoekerjdbi.selectByname(naam);

            if (bezoeker == null) {
                nieuweBezoeker = new Bezoeker(naam);
                bezoekerjdbi.insert(nieuweBezoeker);
            } else {
                nieuweBezoeker = bezoeker;
            }

            System.out.println(bezoekerjdbi.getId(nieuweBezoeker));

            int bezoekerID = bezoekerjdbi.getId(nieuweBezoeker);

            Museum selectedMuseum = museumIDField.getValue();
            int museumID = selectedMuseum.getID();

            String datum = datumField.getValue().toString();

            nieuwBezoek = new MuseumBezoek(museumID,bezoekerID,datum);
            System.out.println(bezoekerID);
            submitted = true;
            closeForm();
        }
    }
    @Override
    public boolean isSubmitted() {
        return submitted;
    }

    @Override
    public MuseumBezoek getNewItem() {
        return nieuwBezoek;
    }

    private void closeForm() {
        Stage stage = (Stage) addBezoeker.getScene().getWindow();
        stage.close();
    }
}
