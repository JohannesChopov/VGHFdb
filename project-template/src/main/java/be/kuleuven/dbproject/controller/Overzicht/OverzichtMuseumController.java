package be.kuleuven.dbproject.controller.Overzicht;

import be.kuleuven.dbproject.jdbi.*;
import be.kuleuven.dbproject.model.*;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class OverzichtMuseumController {
    @FXML
    private Button btnClose;
    @FXML
    private TableView<Museum> tblConfigsMusea, tblInfo;
    @FXML
    private TableView<MuseumBezoek> tblConfigsBezoeken;
    @FXML
    private TableView<Donatie> tblConfigsDonaties;
    @FXML
    private TableView<GameCopy> tblConfigsGameCopies;

    private final Gamejdbi gameJdbi = new Gamejdbi();
    private final Platformjdbi platformjdbi = new Platformjdbi();
    private final GamePlatformjdbi gamePlatformjdbi = new GamePlatformjdbi();
    private final Museumjdbi museumjdbi = new Museumjdbi();
    private final Donatiejdbi donatiejdbi = new Donatiejdbi();
    private final GameCopyjdbi gameCopyJdbi = new GameCopyjdbi();
    private final MuseumBezoekjdbi museumbezoekjdbi = new MuseumBezoekjdbi();

    public void initialize() {
        initTableMusea();
        btnClose.setOnAction(e -> {
            var stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        });

        tblConfigsMusea.setOnMouseClicked(e -> {
            if(e.getClickCount() == 1 && tblConfigsMusea.getSelectionModel().getSelectedItem() != null) {
                var selectedMusea = tblConfigsMusea.getSelectionModel().getSelectedItem();
                initInfoTable(selectedMusea);
                initInfoTable1(selectedMusea);
                initInfoTable2(selectedMusea);
                initInfoTable3(selectedMusea);
            }
        });
    }

    private void initTableMusea() {
        tblConfigsMusea.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblConfigsMusea.getColumns().clear();

        TableColumn<Museum, String> col1 = new TableColumn<>("Naam");
        col1.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getNaam()));
        TableColumn<Museum, String> col2 = new TableColumn<>("Inkomprijs");
        col2.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getInkomprijs()));
        TableColumn<Museum, String> col3 = new TableColumn<>("Adres");
        col3.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getAdres()));

        tblConfigsMusea.getColumns().addAll(col1,col2,col3);
        tblConfigsMusea.setItems(FXCollections.observableArrayList(museumjdbi.getAll()));
    }

    private void initInfoTable(Museum selectedMuseum) {
        tblInfo.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblInfo.getColumns().clear();

        TableColumn<Museum, String> col1 = new TableColumn<>("Naam");
        col1.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getNaam()));
        TableColumn<Museum, String> col2 = new TableColumn<>("Aantal Copies");
        col2.setCellValueFactory(f -> new ReadOnlyObjectWrapper(getAantalCopies(f.getValue())));
        TableColumn<Museum, String> col3 = new TableColumn<>("Aantal bezoekers");
        col3.setCellValueFactory(f -> new ReadOnlyObjectWrapper(getTotaalAantalBezoeken(f.getValue().getID())));
        TableColumn<Museum, String> col4 = new TableColumn<>("Aantal donaties");
        col4.setCellValueFactory(f -> new ReadOnlyObjectWrapper(getAantalDonaties(f.getValue().getID())));
        TableColumn<Museum, String> col5 = new TableColumn<>("Opbrengst");
        col5.setCellValueFactory(f -> new ReadOnlyObjectWrapper(berekenOpbrengst(f.getValue())));

        tblInfo.getColumns().addAll(col1,col2,col3,col4,col5);
        tblInfo.setItems(FXCollections.observableArrayList(selectedMuseum));
    }

    private void initInfoTable1(Museum selectedMuseum) {
        tblConfigsBezoeken.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblConfigsBezoeken.getColumns().clear();

        TableColumn<MuseumBezoek, String> col1 = new TableColumn<>("BezoekerID");
        col1.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getBezoekerID()));
        TableColumn<MuseumBezoek, String> col2 = new TableColumn<>("Bezoeker");
        col2.setCellValueFactory(f -> new ReadOnlyObjectWrapper(museumbezoekjdbi.getBezoekerById(f.getValue().getBezoekerID()).getNaam()));
        TableColumn<MuseumBezoek, String> col3 = new TableColumn<>("Datum");
        col3.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getDatum()));

        tblConfigsBezoeken.getColumns().addAll(col1,col2,col3);
        tblConfigsBezoeken.setItems(FXCollections.observableArrayList(museumbezoekjdbi.getBezoekerByMuseumId(selectedMuseum.getID())));
    }

    private void initInfoTable2(Museum selectedMuseum) {
        tblConfigsDonaties.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblConfigsDonaties.getColumns().clear();

        TableColumn<Donatie, String> col1 = new TableColumn<>("DonatieID");
        col1.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getDonatieID()));
        TableColumn<Donatie, String> col2 = new TableColumn<>("Som");
        col2.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getSom()));
        TableColumn<Donatie, String> col3 = new TableColumn<>("Datum");
        col3.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getDatum()));

        tblConfigsDonaties.getColumns().addAll(col1,col2,col3);
        tblConfigsDonaties.setItems(FXCollections.observableArrayList(donatiejdbi.getDonatieByMuseumId(selectedMuseum.getID())));
    }

    private void initInfoTable3(Museum selectedMuseum) {
        tblConfigsGameCopies.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblConfigsGameCopies.getColumns().clear();

        TableColumn<GameCopy, String> col1 = new TableColumn<>("GameCopyID");
        col1.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getGamecopyID()));
        TableColumn<GameCopy, String> col2 = new TableColumn<>("Game title");
        col2.setCellValueFactory(f -> new ReadOnlyObjectWrapper(gameJdbi.getTitelById(gamePlatformjdbi.getGameIdByGamePlatformId(f.getValue().getGameplatformID()))));
        TableColumn<GameCopy, String> col3 = new TableColumn<>("Platform");
        col3.setCellValueFactory(f -> new ReadOnlyObjectWrapper(getPlatform(f.getValue().getGameplatformID())));

        tblConfigsGameCopies.getColumns().addAll(col1,col2,col3);
        tblConfigsGameCopies.setItems(FXCollections.observableArrayList(gameCopyJdbi.getGameCopyByMuseumId(selectedMuseum.getID())));
    }

    private int getAantalCopies(Museum museum) {
        return museumjdbi.getGameCopyCountByMuseum(museum);
    }



    private double berekenOpbrengst(Museum museum) {
        double inkom = museum.getInkomprijs();
        int bezoekerAantal = getTotaalAantalBezoeken(museum.getID());
        double donatieSom = museumjdbi.getTotalDonatiesValueByMuseumID(museum.getID());
        double totaal = inkom * bezoekerAantal + donatieSom;

        return totaal;
    }

    private Object getAantalDonaties(int id) {
        return museumjdbi.getDonatiesCountByMuseumID(id);
    }


    private int getTotaalAantalBezoeken(int id) {
        return museumbezoekjdbi.countTotalVisits(id);
    }

    private String getPlatform(int gameplatformID) {
        int platformID = gamePlatformjdbi.getPlatformIdByGamePlatformId(gameplatformID);
        return platformjdbi.getNameById(platformID);
    }
}