package be.kuleuven.dbproject.controller;

import be.kuleuven.dbproject.jdbi.*;
import be.kuleuven.dbproject.model.*;
import be.kuleuven.dbproject.model.Bezoeker;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class OverzichtBezoekerController {

    @FXML
    private Button btnClose;
    @FXML
    private TableView<MuseumBezoek> tblConfigsMusea;
    @FXML
    private TableView<Bezoeker> tblConfigBezoeker;
    @FXML
    private TableView<MuseumBezoek> tblConfigMuseumBezoek;
    @FXML
    private TableView<Museum> tblConfigsGameCopies;

    private final Gamejdbi gameJdbi = new Gamejdbi();
    private final Platformjdbi platformjdbi = new Platformjdbi();
    private final GamePlatformjdbi gamePlatformjdbi = new GamePlatformjdbi();
    private final be.kuleuven.dbproject.jdbi.Bezoekerjdbi Bezoekerjdbi = new Bezoekerjdbi();
    private final Donatiejdbi donatiejdbi = new Donatiejdbi();
    private final Museumjdbi museumjdbi = new Museumjdbi();
    private final MuseumBezoekjdbi museumBezoekjdbi = new MuseumBezoekjdbi();
    private final Bezoekerjdbi bezoekerjdbi = new Bezoekerjdbi();

    public void initialize() {
        initTableMusea();
        btnClose.setOnAction(e -> {
            var stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        });

        tblConfigBezoeker.setOnMouseClicked(e -> {
            if(e.getClickCount() == 1 && tblConfigBezoeker.getSelectionModel().getSelectedItem() != null) {
                System.out.println(tblConfigBezoeker.getSelectionModel().getSelectedItem());
                var selectedBezoeker = tblConfigBezoeker.getSelectionModel().getSelectedItem();
                initInfoTable1(selectedBezoeker);
            }
        });
    }

    private void initTableMusea() {
        tblConfigBezoeker.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblConfigBezoeker.getColumns().clear();

        TableColumn<Bezoeker, String> col1 = new TableColumn<>("BezoekerID");
        col1.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getBezoekerID()));
        TableColumn<Bezoeker, String> col2 = new TableColumn<>("naam");
        col2.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getNaam()));
        TableColumn<Bezoeker, String> col3 = new TableColumn<>("Aantal Bezoeken");
        col3.setCellValueFactory(f -> new ReadOnlyObjectWrapper(getAantalBezoeken(f.getValue().getBezoekerID())));

        tblConfigBezoeker.getColumns().addAll(col1,col2,col3);
        tblConfigBezoeker.setItems(FXCollections.observableArrayList(bezoekerjdbi.getAll()));
    }

    private void initInfoTable1(Bezoeker selectedBezoeker) {
        tblConfigsMusea.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblConfigsMusea.getColumns().clear();

        TableColumn<MuseumBezoek, String> col1 = new TableColumn<>("museumbezoekID");
        col1.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getMuseumbezoekID()));
        TableColumn<MuseumBezoek, String> col2 = new TableColumn<>("museum");
        col2.setCellValueFactory(f -> new ReadOnlyObjectWrapper(museumBezoekjdbi.getMuseumByMuseumBezoekId(f.getValue().getMuseumbezoekID()).getNaam()));

        TableColumn<MuseumBezoek, String> col3 = new TableColumn<>("datum");
        col3.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getTijdsstip()));



        tblConfigsMusea.getColumns().addAll(col1,col2,col3);
        tblConfigsMusea.setItems(FXCollections.observableArrayList(museumBezoekjdbi.getMuseumBezoekByBezoekerId(selectedBezoeker.getBezoekerID())));
    }

    private String getNaamByBezoekerID(int bezoekerID) {
        return museumBezoekjdbi.getMuseumNaamByMuseumID(bezoekerID);
    }

    private int getAantalBezoeken(int id) {
        return museumBezoekjdbi.countVisitsByBezoeker(id);
    }
}
