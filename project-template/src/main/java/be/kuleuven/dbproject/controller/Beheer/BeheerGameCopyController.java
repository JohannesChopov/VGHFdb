package be.kuleuven.dbproject.controller.Beheer;

import be.kuleuven.dbproject.controller.Scherm1Controller;
import be.kuleuven.dbproject.controller.VerplaatsCopyController;
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

import static be.kuleuven.dbproject.MyUtility.showAlert;

public class BeheerGameCopyController implements BeheerItemController {

    @FXML
    private Button btnDelete;
    @FXML
    private Button btnClose;
    @FXML
    private TableView<GameCopy> tblConfigs;
    @FXML
    private ChoiceBox<Game> boxGame;
    @FXML
    private ChoiceBox<Platform> boxPlatform;
    @FXML
    private ChoiceBox<Locatie> boxLocatie;
    @FXML
    private Button addBtn;

    private final GameCopyjdbi gameCopyJdbi = new GameCopyjdbi();
    private final Gamejdbi gameJdbi = new Gamejdbi();
    private final Platformjdbi platformjdbi = new Platformjdbi();
    private final GamePlatformjdbi gamePlatformjdbi = new GamePlatformjdbi();
    private final Museumjdbi museumjdbi = new Museumjdbi();
    private final Warenhuisjdbi warenhuisjdbi = new Warenhuisjdbi();

    private GameCopy nieuweKopie;

    @Override
    public void initialize(Scherm1Controller scherm1Controller) {
        initTable();

        boxGame.setItems(FXCollections.observableArrayList(gameJdbi.getAll()));
        boxGame.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                updatePlatformChoiceBox(newValue);
            }
        });
        boxLocatie.setItems(FXCollections.observableArrayList(warenhuisjdbi.getAll()));
        boxLocatie.getItems().addAll(museumjdbi.getAll());

        addBtn.setOnAction(e -> {
            handleAddBtn();
            scherm1Controller.refreshTables();
        });

        btnDelete.setOnAction(e -> {
            verifyOneRowSelected();
            deleteCurrentRow();
            scherm1Controller.refreshTables();
        });

        btnClose.setOnAction(e -> {
            var stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        });

        tblConfigs.setOnMouseClicked(e -> {
            if(e.getClickCount() == 2 && tblConfigs.getSelectionModel().getSelectedItem() != null) {
                var selectedRow = tblConfigs.getSelectionModel().getSelectedItem();
                modifyCopyDoubleClick(selectedRow);
            }
        });
    }

    private void initTable() {
        tblConfigs.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblConfigs.getColumns().clear();

        TableColumn<GameCopy, String> col1 = new TableColumn<>("ID");
        col1.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getGamecopyID()));

        TableColumn<GameCopy, String> col2 = new TableColumn<>("titel");
        col2.setCellValueFactory(f -> new ReadOnlyObjectWrapper(getTitel(f.getValue().getGameplatformID())));

        TableColumn<GameCopy, String> col3 = new TableColumn<>("platform");
        col3.setCellValueFactory(f -> new ReadOnlyObjectWrapper(getPlatform(f.getValue().getGameplatformID())));

        TableColumn<GameCopy, String> col4 = new TableColumn<>("plaats");
        col4.setCellValueFactory(f -> new ReadOnlyObjectWrapper(getPlaats(f.getValue())));

        tblConfigs.getColumns().addAll(col1,col2,col3,col4);
        tblConfigs.setItems(FXCollections.observableArrayList(gameCopyJdbi.getAll()));
    }

    private void handleAddBtn() {
        if (boxGame.getValue() == null || boxPlatform.getValue() == null || boxLocatie.getValue() == null) {
            showAlert("Error", "Vul de velden in, Aub");
            return;
        }

        Game selectedGame = boxGame.getValue();
        Platform selectedPlatform = boxPlatform.getValue();
        Locatie selectedLocatie = boxLocatie.getValue();



        int gameID = selectedGame.getGameID();
        int platformID = selectedPlatform.getPlatformID();
        int gameplatformID = gamePlatformjdbi.getGamePlatformId(gameID,platformID);
        int locatieID = selectedLocatie.getID();

        if (selectedLocatie instanceof Warenhuis) {
            nieuweKopie = new GameCopy(gameplatformID,null,locatieID);
        }
        else {
            nieuweKopie = new GameCopy(gameplatformID,locatieID,null);
        }

        gameCopyJdbi.insert(nieuweKopie);

        refreshTables();

        boxGame.setValue(null);
        boxPlatform.setValue(null);
        boxLocatie.setValue(null);
    }

    private String getTitel(int gameplatformID) {
        int gameID = gamePlatformjdbi.getGameIdByGamePlatformId(gameplatformID);
        return gameJdbi.getTitelById(gameID);
    }
    private String getPlatform(int gameplatformID) {
        int platformID = gamePlatformjdbi.getPlatformIdByGamePlatformId(gameplatformID);
        return platformjdbi.getNameById(platformID);
    }
    private String getPlaats(GameCopy gamecopy) {
        if (gamecopy.getWarenhuisID() == null) {
            return museumjdbi.getNameById(gamecopy.getMuseumID());
        }
        else return warenhuisjdbi.getNameById(gamecopy.getWarenhuisID());
    }

    private void updatePlatformChoiceBox(Game selectedGame) {
        List<Platform> possiblePlatforms = gamePlatformjdbi.getPlatformsForGame(selectedGame.getGameID());
        boxPlatform.setItems(FXCollections.observableArrayList(possiblePlatforms));
    }

    private void modifyCopyDoubleClick(GameCopy selectedRow) {
        GameCopy selected = selectedRow;
        if (selected != null) {
            try {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("verplaatsCopy.fxml"));
                var root = (AnchorPane) loader.load();
                var scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Verplaats gamecopy");
                stage.initModality(Modality.APPLICATION_MODAL);
                VerplaatsCopyController controller = loader.getController();
                controller.initialize(selected);
                stage.showAndWait();
                if (controller.isSubmitted()) {
                    gameCopyJdbi.update(controller.getUpdatedCopy(), selected);
                    refreshTables();
                }
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Error opening the edit form.");
            }
        }
    }

    private void refreshTables() {
        try {
            initTable();
        } catch (Exception e) {
            e.printStackTrace(); // Print the exception details for debugging
            showAlert("Error", "Error refreshing tables.");
        }
    }

    private void deleteCurrentRow() {
        TableView<GameCopy> selectedTable = tblConfigs;
        if (selectedTable != null) {
            GameCopy selectedGameCopy = selectedTable.getSelectionModel().getSelectedItem();
            if (selectedGameCopy!= null) {
                try {
                    gameCopyJdbi.delete(selectedGameCopy);
                    refreshTables();
                } catch (Exception e) {
                    showAlert("Error", "Error deleting the selected item.");
                }
            }
        }
    }

    private void verifyOneRowSelected() {
        if(tblConfigs.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Hela!", "Eerst een record selecteren.");
        }
    }
}
