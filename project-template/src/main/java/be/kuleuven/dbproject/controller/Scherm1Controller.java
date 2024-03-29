package be.kuleuven.dbproject.controller;

import be.kuleuven.dbproject.controller.Add.AddItemController;
import be.kuleuven.dbproject.controller.Beheer.BeheerItemController;
import be.kuleuven.dbproject.jdbi.*;
import be.kuleuven.dbproject.model.*;
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

public class Scherm1Controller {

    @FXML
    private Button btnDeleteGame;
    @FXML
    private Button btnAddGame;
    @FXML
    private Button btnAddPlatform;
    @FXML
    private Button btnAddBezoeker;
    @FXML
    private Button btnAddMuseum;
    @FXML
    private Button btnDeleteMuseum;
    @FXML
    private Button btnAddWarenhuis;
    @FXML
    private Button btnDeleteWarenhuis;
    @FXML
    private Button btnDeleteBezoeker;
    @FXML
    private Button btnDeletePlatform;
    @FXML
    private Button btnAddDonatie;
    @FXML
    private Button btnDeleteDonatie;
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
    @FXML
    private TableView<Bezoeker> tblConfigsBezoekers;
    @FXML
    private TableView<Donatie> tblConfigsDonaties;
    @FXML
    private Button btnBeheerScherm2;

    private final Gamejdbi gameJdbi = new Gamejdbi();
    private final GameCopyjdbi gamecopyjdbi = new GameCopyjdbi();
    private final Platformjdbi platformjdbi = new Platformjdbi();
    private final Museumjdbi museumjdbi = new Museumjdbi();
    private final MuseumBezoekjdbi museumbezoekjdbi = new MuseumBezoekjdbi();
    private final Warenhuisjdbi warenhuisjdbi = new Warenhuisjdbi();
    private final Bezoekerjdbi bezoekerjdbi = new Bezoekerjdbi();
    private final Donatiejdbi donatiejdbi = new Donatiejdbi();

    private void showBeheerScherm(String id) {
        var resourceName = id + ".fxml";
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Beheer/" + resourceName));
            var root = (AnchorPane) loader.load();
            var scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Admin " + id);
            stage.initModality(Modality.APPLICATION_MODAL);
            BeheerItemController controller = loader.getController();
            controller.initialize(this);
            stage.showAndWait();
        } catch (Exception e) {
            throw new RuntimeException("Kan beheerscherm " + resourceName + " niet vinden", e);
        }
    }

    public void initialize() {
        initTables();

        btnAddGame.setOnAction(e -> addNewItem("Game", gameJdbi));
        btnAddPlatform.setOnAction(e -> addNewItem("Platform", platformjdbi));
        btnAddDonatie.setOnAction(e -> addNewItem("Donatie", donatiejdbi));
        //btnAddBezoeker.setOnAction(e -> addNewItem("MuseumBezoek", museumbezoekjdbi));
        btnAddMuseum.setOnAction(e -> addNewItem("Museum", museumjdbi));
        btnAddWarenhuis.setOnAction(e -> addNewItem("Warenhuis", warenhuisjdbi));

        btnBeheerScherm2.setOnAction(e -> showBeheerScherm("beheerGameCopy"));
        btnAddBezoeker.setOnAction(e -> showBeheerScherm("beheerBezoeken"));

        btnDeleteGame.setOnAction(e -> {deleteSelectedItem(tblConfigsGames, gameJdbi, "game");});
        btnDeletePlatform.setOnAction(e -> {deleteSelectedItem(tblConfigsPlatforms, platformjdbi, "platform");});
        btnDeleteDonatie.setOnAction(e -> {deleteSelectedItem(tblConfigsDonaties, donatiejdbi, "donatie");});
        btnDeleteBezoeker.setOnAction(e -> {deleteSelectedItem(tblConfigsBezoekers, bezoekerjdbi, "bezoeker");});
        btnDeleteMuseum.setOnAction(e -> {deleteSelectedItem(tblConfigsMusea, museumjdbi, "museum");});
        btnDeleteWarenhuis.setOnAction(e -> {deleteSelectedItem(tblConfigsWarenhuizen, warenhuisjdbi, "warenhuis");});

        btnClose.setOnAction(e -> {
            var stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        });
    }

    private void initTables() {
        initTableGame();
        initTablePlatforms();
        initTableMusea();
        initTableWarenhuizen();
        initTableBezoekers();
        initTableDonaties();
    }

    private void initTableGame() {
        tblConfigsGames.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblConfigsGames.getColumns().clear();

        TableColumn<Game, String> col1 = new TableColumn<>("GameID");
        col1.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getGameID()));
        TableColumn<Game, String> col2 = new TableColumn<>("Titel");
        col2.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getTitel()));
        TableColumn<Game, String> col3 = new TableColumn<>("Aantal copies");
        col3.setCellValueFactory(f -> new ReadOnlyObjectWrapper(gamecopyjdbi.getCountByGameID(f.getValue().getGameID())));


        tblConfigsGames.getColumns().addAll(col1,col2,col3);
        tblConfigsGames.setItems(FXCollections.observableArrayList(gameJdbi.getAll()));
    }

    private void initTablePlatforms() {
        tblConfigsPlatforms.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblConfigsPlatforms.getColumns().clear();

        TableColumn<Platform, String> col1 = new TableColumn<>("PlatformID");
        col1.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getPlatformID()));
        TableColumn<Platform, String> col2 = new TableColumn<>("Naam");
        col2.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getNaam()));

        tblConfigsPlatforms.getColumns().addAll(col1,col2);
        tblConfigsPlatforms.setItems(FXCollections.observableArrayList(platformjdbi.getAll()));
    }

    private void initTableMusea() {
        tblConfigsMusea.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblConfigsMusea.getColumns().clear();

        TableColumn<Museum, String> col1 = new TableColumn<>("MuseumID");
        col1.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getID()));
        TableColumn<Museum, String> col2 = new TableColumn<>("Naam");
        col2.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getNaam()));
        TableColumn<Museum, String> col3 = new TableColumn<>("Inkomprijs");
        col3.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getInkomprijs()));
        TableColumn<Museum, String> col4 = new TableColumn<>("Adres");
        col4.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getAdres()));

        tblConfigsMusea.getColumns().addAll(col1,col2,col3,col4);
        tblConfigsMusea.setItems(FXCollections.observableArrayList(museumjdbi.getAll()));
    }

    private void initTableWarenhuizen() {
        tblConfigsWarenhuizen.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblConfigsWarenhuizen.getColumns().clear();

        TableColumn<Warenhuis, String> col1 = new TableColumn<>("WarenhuisID");
        col1.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getID()));
        TableColumn<Warenhuis, String> col2 = new TableColumn<>("Naam");
        col2.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getNaam()));
        TableColumn<Warenhuis, String> col3 = new TableColumn<>("Adres");
        col3.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getAdres()));

        tblConfigsWarenhuizen.getColumns().addAll(col1,col2,col3);
        tblConfigsWarenhuizen.setItems(FXCollections.observableArrayList(warenhuisjdbi.getAll()));
    }

    private void initTableBezoekers() {
        tblConfigsBezoekers.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblConfigsBezoekers.getColumns().clear();

        TableColumn<Bezoeker, String> col1 = new TableColumn<>("BezoekerID");
        col1.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getBezoekerID()));
        TableColumn<Bezoeker, String> col2 = new TableColumn<>("Naam");
        col2.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getNaam()));
        TableColumn<Bezoeker, String> col3 = new TableColumn<>("Aantal bezoeken");
        col3.setCellValueFactory(f -> new ReadOnlyObjectWrapper(museumbezoekjdbi.countVisitsByBezoeker(f.getValue().getBezoekerID())));

        tblConfigsBezoekers.getColumns().addAll(col1,col2,col3);
        tblConfigsBezoekers.setItems(FXCollections.observableArrayList(bezoekerjdbi.getAll()));
    }

    private void initTableDonaties() {
        tblConfigsDonaties.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblConfigsDonaties.getColumns().clear();

        TableColumn<Donatie, String> col1 = new TableColumn<>("DonatieID");
        col1.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getDonatieID()));
        TableColumn<Donatie, String> col2 = new TableColumn<>("Som");
        col2.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getSom()));
        TableColumn<Donatie, String> col3 = new TableColumn<>("Datum");
        col3.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getDatum()));
        TableColumn<Donatie, String> col4 = new TableColumn<>("MuseumID");
        col4.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getMuseumID()));

        tblConfigsDonaties.getColumns().addAll(col1,col2,col3,col4);
        tblConfigsDonaties.setItems(FXCollections.observableArrayList(donatiejdbi.getAll()));
    }

    public void refreshTables() {
        try {
            tblConfigsGames.setItems(FXCollections.observableArrayList(gameJdbi.getAll()));
            tblConfigsPlatforms.setItems(FXCollections.observableArrayList(platformjdbi.getAll()));
            tblConfigsMusea.setItems(FXCollections.observableArrayList(museumjdbi.getAll()));
            tblConfigsWarenhuizen.setItems(FXCollections.observableArrayList(warenhuisjdbi.getAll()));
            tblConfigsBezoekers.setItems(FXCollections.observableArrayList(bezoekerjdbi.getAll()));
            tblConfigsDonaties.setItems(FXCollections.observableArrayList(donatiejdbi.getAll()));
        } catch (Exception e) {
            e.printStackTrace(); // Print the exception details for debugging
            showAlert("Error", "Error refreshing tables.");
        }
    }

    private <T> void deleteSelectedItem(TableView<T> selectedTable, Interfacejdbi<T> repository, String item) {
        verifyOneRowSelected(selectedTable, item);
        if (selectedTable != null) {
            T selectedItem = selectedTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                try {
                    repository.delete(selectedItem);
                    refreshTables();
                } catch (Exception e) {
                    showAlert("Error", "Error deleting the selected item.");
                }
            }
        }
    }

    private void verifyOneRowSelected(TableView<?> tableView, String item) {
        if (tableView.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Hela!", "Selecteer eerst een " + item + " dat je wilt verwijderen");
        }
    }

    private <T> void addNewItem(String item, Interfacejdbi<T> jdbi) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Add/add" + item + ".fxml"));
            var root = (AnchorPane) loader.load();
            var scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Voeg " + item + " toe");
            stage.initModality(Modality.APPLICATION_MODAL);
            AddItemController<T> controller = loader.getController();
            controller.initialize();
            stage.showAndWait();
            if (controller.isSubmitted()) {
                if (item != "Game") {
                    jdbi.insert(controller.getNewItem());
                }
                refreshTables();
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Error opening the add form.");
        }
    }
}