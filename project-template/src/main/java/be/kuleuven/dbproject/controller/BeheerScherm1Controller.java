package be.kuleuven.dbproject.controller;

import be.kuleuven.dbproject.ProjectMain;
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
import java.util.List;

public class BeheerScherm1Controller {

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
    private final Platformjdbi platformjdbi = new Platformjdbi();
    private final GamePlatformjdbi gamePlatformjdbi = new GamePlatformjdbi();
    private final Museumjdbi museumjdbi = new Museumjdbi();
    private final Warenhuisjdbi warenhuisjdbi = new Warenhuisjdbi();
    private final Bezoekerjdbi bezoekerjdbi = new Bezoekerjdbi();
    private final Donatiejdbi donatiejdbi = new Donatiejdbi();

    private void showBeheerScherm(String id) {
        var resourceName = "beheer" + id + ".fxml";
        try {
            var stage = new Stage();
            var root = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource(resourceName));
            var scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Admin " + id);
            stage.initOwner(ProjectMain.getRootStage());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();

        } catch (Exception e) {
            throw new RuntimeException("Kan beheerscherm " + resourceName + " niet vinden", e);
        }
    }

    public void initialize() {
        initTables();
        btnAddGame.setOnAction(e -> addNewGame());
        btnAddPlatform.setOnAction(e -> addNewPlatform());
        btnAddDonatie.setOnAction(e -> addNewDonatie());
        btnAddBezoeker.setOnAction(e -> addNewBezoeker());
        btnAddMuseum.setOnAction(e -> addNewMuseum());
        btnAddWarenhuis.setOnAction(e -> addNewWarenhuis());

        btnBeheerScherm2.setOnAction(e -> showBeheerScherm("scherm2"));

        btnDeleteGame.setOnAction(e -> {
            deleteSelectedItem(tblConfigsGames, gameJdbi, "game");
        });

        btnDeletePlatform.setOnAction(e -> {
            deleteSelectedItem(tblConfigsPlatforms, platformjdbi, "platform");
        });

        btnDeleteDonatie.setOnAction(e -> {
            deleteSelectedItem(tblConfigsDonaties, donatiejdbi, "donatie");
        });

        btnDeleteBezoeker.setOnAction(e -> {
            deleteSelectedItem(tblConfigsBezoekers, bezoekerjdbi, "bezoeker");
        });

        btnDeleteMuseum.setOnAction(e -> {
            deleteSelectedItem(tblConfigsMusea, museumjdbi, "museum");
        });

        btnDeleteWarenhuis.setOnAction(e -> {
            deleteSelectedItem(tblConfigsWarenhuizen, warenhuisjdbi, "warenhuis");
        });

        btnClose.setOnAction(e -> {
            var stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        });

        tblConfigsGames.setOnMouseClicked(e -> {
            if(e.getClickCount() == 2 && tblConfigsGames.getSelectionModel().getSelectedItem() != null) {
                var selectedRow = tblConfigsGames.getSelectionModel().getSelectedItem();
                modifyGameDoubleClick(selectedRow);
            }
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

        TableColumn<Game, String> col1 = new TableColumn<>("gameID");
        col1.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getGameID()));
        TableColumn<Game, String> col2 = new TableColumn<>("titel");
        col2.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getTitel()));

        tblConfigsGames.getColumns().addAll(col1,col2);
        tblConfigsGames.setItems(FXCollections.observableArrayList(gameJdbi.getAll()));
    }

    private void initTablePlatforms() {
        tblConfigsPlatforms.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblConfigsPlatforms.getColumns().clear();

        TableColumn<Platform, String> col1 = new TableColumn<>("platformID");
        col1.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getPlatformID()));
        TableColumn<Platform, String> col2 = new TableColumn<>("naam");
        col2.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getNaam()));

        tblConfigsPlatforms.getColumns().addAll(col1,col2);
        tblConfigsPlatforms.setItems(FXCollections.observableArrayList(platformjdbi.getAll()));
    }

    private String getTitel(int gameplatformID) {
        int gameID = gamePlatformjdbi.getGameIdByGamePlatformId(gameplatformID);
        return gameJdbi.getTitelById(gameID);
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

        TableColumn<Warenhuis, String> col1 = new TableColumn<>("warenhuisID");
        col1.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getID()));
        TableColumn<Warenhuis, String> col2 = new TableColumn<>("naam");
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
        TableColumn<Bezoeker, String> col2 = new TableColumn<>("naam");
        col2.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getNaam()));
        TableColumn<Bezoeker, String> col3 = new TableColumn<>("museumID");
        col3.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getMuseumID()));

        tblConfigsBezoekers.getColumns().addAll(col1,col2,col3);
        tblConfigsBezoekers.setItems(FXCollections.observableArrayList(bezoekerjdbi.getAll()));
    }

    private void initTableDonaties() {
        tblConfigsDonaties.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblConfigsDonaties.getColumns().clear();

        TableColumn<Donatie, String> col1 = new TableColumn<>("donatieID");
        col1.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getDonatieID()));
        TableColumn<Donatie, String> col2 = new TableColumn<>("som");
        col2.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getSom()));
        TableColumn<Donatie, String> col3 = new TableColumn<>("datum");
        col3.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getDatum()));
        TableColumn<Donatie, String> col4 = new TableColumn<>("museumID");
        col4.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getMuseumID()));

        tblConfigsDonaties.getColumns().addAll(col1,col2,col3,col4);
        tblConfigsDonaties.setItems(FXCollections.observableArrayList(donatiejdbi.getAll()));
    }

    private void addNewPlatform() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("addPlatform.fxml"));
            var root = (AnchorPane) loader.load();
            var scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Voeg platform toe");
            stage.initModality(Modality.APPLICATION_MODAL);
            AddPlatformController controller = loader.getController();
            controller.initialize();
            stage.showAndWait();
            if (controller.isSubmitted()) {
                platformjdbi.insert(controller.getNieuwePlatform());
                refreshTables();
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Error opening the add form.");
        }
    }

    private void addNewDonatie() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("addDonatie.fxml"));
            var root = (AnchorPane) loader.load();
            var scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Voeg donatie toe");
            stage.initModality(Modality.APPLICATION_MODAL);
            AddDonatieController controller = loader.getController();
            controller.initialize();
            stage.showAndWait();
            if (controller.isSubmitted()) {
                donatiejdbi.insert(controller.getNewDonatie());
                refreshTables();
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Error opening the add form.");
        }
    }



    private void addNewBezoeker() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("addBezoeker.fxml"));
            var root = (AnchorPane) loader.load();
            var scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Voeg bezoeker toe");
            stage.initModality(Modality.APPLICATION_MODAL);
            AddBezoekerController controller = loader.getController();
            controller.initialize();
            stage.showAndWait();
            if (controller.isSubmitted()) {
                bezoekerjdbi.insert(controller.getNewBezoeker());
                refreshTables();
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Error opening the add form.");
        }
    }



    private void addNewMuseum() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("addMuseum.fxml"));
            var root = (AnchorPane) loader.load();
            var scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Voeg museum toe");
            stage.initModality(Modality.APPLICATION_MODAL);
            AddMuseumController controller = loader.getController();
            controller.initialize();
            stage.showAndWait();
            if (controller.isSubmitted()) {
                museumjdbi.insert(controller.getNewMuseum());
                refreshTables();
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Error opening the add form.");
        }
    }



    private void addNewWarenhuis() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("addWarenhuis.fxml"));
            var root = (AnchorPane) loader.load();
            var scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Voeg warenhuis toe");
            stage.initModality(Modality.APPLICATION_MODAL);
            AddWarenhuisController controller = loader.getController();
            controller.initialize();
            stage.showAndWait();
            if (controller.isSubmitted()) {
                warenhuisjdbi.insert(controller.getNieuwWarenhuis());
                refreshTables();
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Error opening the add form.");
        }
    }

    private void addNewGame() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("addGame.fxml"));
            var root = (AnchorPane) loader.load();
            var scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Voeg game toe");
            stage.initModality(Modality.APPLICATION_MODAL);
            AddGameController controller = loader.getController();
            controller.initialize();
            stage.showAndWait();
            if (controller.isSubmitted()) {
                refreshTables();
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Error opening the add form.");
        }
    }

    private void refreshTables() {
        try {
            // Update data sources for other tables
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

    private void modifyGameDoubleClick(Game game) {
        Game selectedGame = game;
        if (selectedGame != null) {
            try {
                // Open a form to edit the game
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("bewerkGame.fxml"));
                var root = (AnchorPane) loader.load();
                var scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Bewerk game");
                stage.initModality(Modality.APPLICATION_MODAL);
                BewerkGameController controller = loader.getController();
                controller.initialize(selectedGame);
                stage.showAndWait();
                if (controller.isSubmitted()) {
                    gameJdbi.update(controller.getUpdatedGame(), selectedGame);
                    refreshTables();
                }
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Error opening the edit form.");
            }
        }
    }

    public void showAlert(String title, String content) {
        var alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
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
}