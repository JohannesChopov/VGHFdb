package be.kuleuven.dbproject.controller;

import be.kuleuven.dbproject.jdbi.*;
import be.kuleuven.dbproject.model.Bezoeker;
import be.kuleuven.dbproject.model.GameCopy;
import be.kuleuven.dbproject.model.Museum;
import be.kuleuven.dbproject.model.MuseumBezoek;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import static be.kuleuven.dbproject.MyUtility.showAlert;

public class BeheerBezoekenController implements BeheerItemController{

    @FXML
    private Button btnDelete;
    @FXML
    private Button btnClose;
    @FXML
    private TableView<MuseumBezoek> tblBezoeken;
    @FXML
    private TextField naamField;
    @FXML
    private ChoiceBox<Museum> museumField;
    @FXML
    private Button addBezoeker;
    @FXML
    private DatePicker datumField;

    private final GameCopyjdbi gameCopyJdbi = new GameCopyjdbi();
    private final Gamejdbi gameJdbi = new Gamejdbi();
    private final Platformjdbi platformjdbi = new Platformjdbi();
    private final GamePlatformjdbi gamePlatformjdbi = new GamePlatformjdbi();
    private final Museumjdbi museumjdbi = new Museumjdbi();
    private final Warenhuisjdbi warenhuisjdbi = new Warenhuisjdbi();
    private final MuseumBezoekjdbi museumbezoekjdbi = new MuseumBezoekjdbi();
    private Bezoekerjdbi bezoekerjdbi = new Bezoekerjdbi();

    private MuseumBezoek nieuwBezoek;
    private Bezoeker nieuweBezoeker;

    public void initialize(BeheerScherm1Controller beheerScherm1Controller) {
        initTableBezoeken();
        museumField.setItems(FXCollections.observableArrayList(museumjdbi.getAll()));
        addBezoeker.setOnAction(e -> {
            handleAddBtn();
            beheerScherm1Controller.refreshTables();
        });
        btnDelete.setOnAction(e -> {
            verifyOneRowSelected();
            deleteCurrentRow();
            beheerScherm1Controller.refreshTables();
        });

        btnClose.setOnAction(e -> {
            var stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        });
    }

    private void handleAddBtn() {
        if (naamField.getText().isBlank() || datumField.getValue() == null || museumField.getValue() == null) {
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

            int bezoekerID = bezoekerjdbi.getId(nieuweBezoeker);
            Museum selectedMuseum = museumField.getValue();
            int museumID = selectedMuseum.getID();
            String datum = datumField.getValue().toString();

            nieuwBezoek = new MuseumBezoek(museumID,bezoekerID,datum);
            museumbezoekjdbi.insert(nieuwBezoek);
            refreshTables();

            museumField.setValue(null);
            datumField.setValue(null);
            naamField.setText("");
        }
    }

    private void initTableBezoeken() {
        tblBezoeken.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblBezoeken.getColumns().clear();

        TableColumn<MuseumBezoek, String> col1 = new TableColumn<>("BezoekID");
        col1.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getMuseumbezoekID()));
        TableColumn<MuseumBezoek, String> col2 = new TableColumn<>("BezoekerID");
        col2.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getBezoekerID()));
        TableColumn<MuseumBezoek, String> col3 = new TableColumn<>("Bezoeker");
        col3.setCellValueFactory(f -> new ReadOnlyObjectWrapper(museumbezoekjdbi.getBezoekerById(f.getValue().getBezoekerID()).getNaam()));
        TableColumn<MuseumBezoek, String> col4 = new TableColumn<>("Museum");
        col4.setCellValueFactory(f -> new ReadOnlyObjectWrapper(museumbezoekjdbi.getMuseumById(f.getValue().getMuseumID()).getNaam()));
        TableColumn<MuseumBezoek, String> col5 = new TableColumn<>("Datum");
        col5.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getTijdsstip()));

        tblBezoeken.getColumns().addAll(col1,col2,col3,col4,col5);
        tblBezoeken.setItems(FXCollections.observableArrayList(museumbezoekjdbi.getAll()));
    }

    private void refreshTables() {
        try {
            initTableBezoeken();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Error refreshing tables.");
        }

    }

    private void deleteCurrentRow() {
        TableView<MuseumBezoek> selectedTable = tblBezoeken;
        if (selectedTable != null) {
            MuseumBezoek selectedBezoek = selectedTable.getSelectionModel().getSelectedItem();
            if (selectedBezoek!= null) {
                try {
                    museumbezoekjdbi.delete(selectedBezoek);
                    refreshTables();
                } catch (Exception e) {
                    showAlert("Error", "Error deleting the selected item.");
                }
            }
        }
    }

    private void verifyOneRowSelected() {
        if(tblBezoeken.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Hela!", "Eerst een record selecteren.");
        }
    }
}
