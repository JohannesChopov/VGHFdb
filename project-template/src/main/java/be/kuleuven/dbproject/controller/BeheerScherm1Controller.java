package be.kuleuven.dbproject.controller;

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
    private Button btnDelete;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnClose;
    @FXML
    private TableView<Game> tblConfigsGames;
    @FXML
    private TableView<Platform> tblConfigsPlatforms;
    @FXML
    private TableView<GamePlatform> tblConfigsGamePlatforms;
    @FXML
    private TableView<Museum> tblConfigsMusea;
    @FXML
    private TableView<Warenhuis> tblConfigsWarenhuizen;

    private final Gamejdbi gameJdbi = new Gamejdbi();
    private final Platformjdbi platformjdbi = new Platformjdbi();
    private final GamePlatformjdbi gamePlatformjdbi = new GamePlatformjdbi();
    private final Museumjdbi museumjdbi = new Museumjdbi();
    private final Warenhuisjdbi warenhuisjdbi = new Warenhuisjdbi();

    public void initialize() {
        initTables();
        btnAdd.setOnAction(e -> addNewRow());
        btnModify.setOnAction(e -> {
            verifyOneRowSelected();
            modifyCurrentRow();
        });
        btnDelete.setOnAction(e -> {
            verifyOneRowSelected();
            deleteCurrentRow();
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
        initTableGamePlatform();
        initTableMusea();
        initTableWarenhuizen();
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
        System.out.println(gameJdbi.getTitelById(4));
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

    private void initTableGamePlatform() {
        tblConfigsGamePlatforms.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblConfigsGamePlatforms.getColumns().clear();

        TableColumn<GamePlatform, String> col1 = new TableColumn<>("ID");
        col1.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getGameplatformID()));
        TableColumn<GamePlatform, String> col2 = new TableColumn<>("gameID");
        col2.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getGameID()));
        TableColumn<GamePlatform, String> col3 = new TableColumn<>("platformID");
        col3.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getPlatformID()));

        tblConfigsGamePlatforms.getColumns().addAll(col1,col2,col3);
        tblConfigsGamePlatforms.setItems(FXCollections.observableArrayList(gamePlatformjdbi.getAll()));
    }

    private String getTitel(int gameplatformID) {
        int gameID = gamePlatformjdbi.getGameIdByGamePlatformId(gameplatformID);
        return gameJdbi.getTitelById(gameID);
    }

    private void initTableMusea() {
        tblConfigsMusea.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblConfigsMusea.getColumns().clear();

        TableColumn<Museum, String> col1 = new TableColumn<>("museumID");
        col1.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getID()));
        TableColumn<Museum, String> col2 = new TableColumn<>("naam");
        col2.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getNaam()));

        tblConfigsMusea.getColumns().addAll(col1,col2);
        tblConfigsMusea.setItems(FXCollections.observableArrayList(museumjdbi.getAll()));
    }

    private void initTableWarenhuizen() {
        tblConfigsWarenhuizen.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblConfigsWarenhuizen.getColumns().clear();

        TableColumn<Warenhuis, String> col1 = new TableColumn<>("warenhuisID");
        col1.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getID()));
        TableColumn<Warenhuis, String> col2 = new TableColumn<>("naam");
        col2.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getNaam()));

        tblConfigsWarenhuizen.getColumns().addAll(col1,col2);
        tblConfigsWarenhuizen.setItems(FXCollections.observableArrayList(warenhuisjdbi.getAll()));
    }



    private void addNewRow() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("addGame.fxml"));
            var root = (AnchorPane) loader.load();
            var scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Voeg game toe");
            stage.initModality(Modality.APPLICATION_MODAL);

            // Get the controller of the GameForm
            AddGameController controller = loader.getController();
            controller.initialize();
            // Show the form and wait for it to be closed
            stage.showAndWait();

            // After the form is closed, check if it was submitted
            if (controller.isSubmitted()) {
                // Update the item in the database
                gameJdbi.insert(controller.getNewGame());

                // Refresh the table to reflect changes
                refreshTables();
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Error opening the add form.");
        }
    }

    private void deleteCurrentRow() {
        TableView<Game> selectedTable = tblConfigsGames;
        System.out.println("1");
        if (selectedTable != null) {
            Game selectedGame = selectedTable.getSelectionModel().getSelectedItem();
            System.out.println("2");
            if (selectedGame != null) {
                try {
                    // Delete from the Game table
                    gameJdbi.delete(selectedGame);
                    //selectedTable.getItems().remove(selectedGame);

                    // Refresh other tables
                    refreshTables();
                } catch (Exception e) {
                    showAlert("Error", "Error deleting the selected item.");
                }
            }
        }
    }

    private void refreshTables() {
        try {
            // Update data sources for other tables
            tblConfigsGames.setItems(FXCollections.observableArrayList(gameJdbi.getAll()));
            tblConfigsPlatforms.setItems(FXCollections.observableArrayList(platformjdbi.getAll()));
            tblConfigsGamePlatforms.setItems(FXCollections.observableArrayList(gamePlatformjdbi.getAll()));
            tblConfigsMusea.setItems(FXCollections.observableArrayList(museumjdbi.getAll()));
            tblConfigsWarenhuizen.setItems(FXCollections.observableArrayList(warenhuisjdbi.getAll()));
        } catch (Exception e) {
            e.printStackTrace(); // Print the exception details for debugging
            showAlert("Error", "Error refreshing tables.");
        }
    }

    private void modifyCurrentRow() {
        TableView<Game> selectedTable = tblConfigsGames;
        if (selectedTable != null) {
            Game selectedGame = selectedTable.getSelectionModel().getSelectedItem();
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

                    // Get the controller of the GameForm
                    BewerkGameController controller = loader.getController();

                    // Initialize the form with the selected game
                    controller.initialize(selectedGame);

                    // Show the form and wait for it to be closed
                    stage.showAndWait();

                    // After the form is closed, check if it was submitted
                    if (controller.isSubmitted()) {
                        // Update the item in the database
                        gameJdbi.update(controller.getUpdatedGame(), selectedGame);

                        // Refresh the table to reflect changes
                        refreshTables();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    showAlert("Error", "Error opening the edit form.");
                }
            }
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

                // Get the controller of the GameForm
                BewerkGameController controller = loader.getController();

                // Initialize the form with the selected game
                controller.initialize(selectedGame);

                // Show the form and wait for it to be closed
                stage.showAndWait();

                // After the form is closed, check if it was submitted
                if (controller.isSubmitted()) {
                    // Update the item in the database
                    gameJdbi.update(controller.getUpdatedGame(), selectedGame);

                    // Refresh the table to reflect changes
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

    private void verifyOneRowSelected() {
        if(tblConfigsGames.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Hela!", "Selecteer eerst een gametitel dat je wilt verwijderen");
        }
    }
}
