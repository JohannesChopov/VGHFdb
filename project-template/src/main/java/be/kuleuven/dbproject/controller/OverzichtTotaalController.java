package be.kuleuven.dbproject.controller;

import be.kuleuven.dbproject.jdbi.*;
import be.kuleuven.dbproject.model.*;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class TotaalOverzichtController {

    @FXML
    private Button btnClose;
    @FXML
    private TableView<Game> tblConfigsGames;
    @FXML
    private TableView<Platform> tblConfigsPlatforms;
    @FXML
    private TableView<Museum> tblConfigsMusea;
    @FXML
    private TableView<Warenhuis> tblConfigsWarenhuizen;

    private final Gamejdbi gameJdbi = new Gamejdbi();
    private final Platformjdbi platformjdbi = new Platformjdbi();
    private final GamePlatformjdbi gamePlatformjdbi = new GamePlatformjdbi();
    private final Museumjdbi museumjdbi = new Museumjdbi();
    private final Warenhuisjdbi warenhuisjdbi = new Warenhuisjdbi();
    private final Bezoekerjdbi bezoekerjdbi = new Bezoekerjdbi();
    private final Donatiejdbi donatiejdbi = new Donatiejdbi();
    private final GameCopyjdbi gameCopyJdbi = new GameCopyjdbi();

    public void initialize() {
        initTables();
        btnClose.setOnAction(e -> {
            var stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        });
    }

    private void initTables() {
        initTableGames();
        initTablePlatforms();
        initTableMusea();
        initTableWarenhuizen();
    }

    private void initTableGames() {
        tblConfigsGames.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblConfigsGames.getColumns().clear();

        TableColumn<Game, String> col1 = new TableColumn<>("GameID");
        col1.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getGameID()));
        TableColumn<Game, String> col2 = new TableColumn<>("Titel");
        col2.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getTitel()));
        TableColumn<Game, String> col3 = new TableColumn<>("Aantal");
        col3.setCellValueFactory(f -> new ReadOnlyObjectWrapper(getAantalCopys(f.getValue().getGameID())));

        tblConfigsGames.getColumns().addAll(col1,col2,col3);
        tblConfigsGames.setItems(FXCollections.observableArrayList(gameJdbi.getAll()));
    }

    private int getAantalCopys(int id) {
        return gameCopyJdbi.getCountByGameID(id);
    }

    private void initTablePlatforms() {
        tblConfigsPlatforms.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblConfigsPlatforms.getColumns().clear();

        TableColumn<Platform, String> col1 = new TableColumn<>("PlatformID");
        col1.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getPlatformID()));
        TableColumn<Platform, String> col2 = new TableColumn<>("Naam");
        col2.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getNaam()));
        TableColumn<Platform, String> col3 = new TableColumn<>("Aantal gametitels");
        col3.setCellValueFactory(f -> new ReadOnlyObjectWrapper(getAantalGames(f.getValue().getPlatformID())));
        TableColumn<Platform, String> col4 = new TableColumn<>("Aantal gamecopies");
        col4.setCellValueFactory(f -> new ReadOnlyObjectWrapper(getAantalCopies(f.getValue().getPlatformID())));

        tblConfigsPlatforms.getColumns().addAll(col1,col2,col3,col4);
        tblConfigsPlatforms.setItems(FXCollections.observableArrayList(platformjdbi.getAll()));
    }

    private int getAantalGames(int id) {
        return gamePlatformjdbi.getGameCountByPlatformID(id);
    }
    private int getAantalCopies(int id) {
        return gamePlatformjdbi.getGameCopyCountByPlatformID(id);
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
        TableColumn<Museum, String> col4 = new TableColumn<>("Aantal Copies");
        col4.setCellValueFactory(f -> new ReadOnlyObjectWrapper(getAantalCopies(f.getValue())));
        TableColumn<Museum, String> col5 = new TableColumn<>("Aantal bezoekers");
        col5.setCellValueFactory(f -> new ReadOnlyObjectWrapper(getAantalBezoekers(f.getValue().getID())));
        TableColumn<Museum, String> col6 = new TableColumn<>("Aantal donaties");
        col6.setCellValueFactory(f -> new ReadOnlyObjectWrapper(getAantalDonaties(f.getValue().getID())));
        TableColumn<Museum, String> col7 = new TableColumn<>("Opbrengst");
        col7.setCellValueFactory(f -> new ReadOnlyObjectWrapper(berekenOpbrengst(f.getValue())));

        tblConfigsMusea.getColumns().addAll(col1,col2,col3,col4,col5,col6,col7);
        tblConfigsMusea.setItems(FXCollections.observableArrayList(museumjdbi.getAll()));
    }

    private int getAantalCopies(Museum museum) {
        return museumjdbi.getGameCopyCountByMuseum(museum);
    }

    private double berekenOpbrengst(Museum museum) {
        double inkom = museum.getInkomprijs();
        int bezoekerAantal = getAantalBezoekers(museum.getID());
        double donatieSom = museumjdbi.getTotalDonatiesValueByMuseumID(museum.getID());
        double totaal = inkom * bezoekerAantal + donatieSom;

        return totaal;
    }

    private Object getAantalDonaties(int id) {
        return museumjdbi.getDonatiesCountByMuseumID(id);
    }

    private int getAantalBezoekers(int id) {
        return museumjdbi.getBezoekersCountByMuseumID(id);
    }

    private void initTableWarenhuizen() {
        tblConfigsWarenhuizen.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblConfigsWarenhuizen.getColumns().clear();

        TableColumn<Warenhuis, String> col1 = new TableColumn<>("Naam");
        col1.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getNaam()));
        TableColumn<Warenhuis, String> col2 = new TableColumn<>("Adres");
        col2.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getAdres()));
        TableColumn<Warenhuis, String> col3 = new TableColumn<>("Aantal Copies");
        col3.setCellValueFactory(f -> new ReadOnlyObjectWrapper(getAantalCopies(f.getValue())));

        tblConfigsWarenhuizen.getColumns().addAll(col1,col2,col3);
        tblConfigsWarenhuizen.setItems(FXCollections.observableArrayList(warenhuisjdbi.getAll()));
    }

    private int getAantalCopies(Warenhuis warenhuis) {
        return warenhuisjdbi.getGameCopyCountByWarenhuis(warenhuis);
    }
}
