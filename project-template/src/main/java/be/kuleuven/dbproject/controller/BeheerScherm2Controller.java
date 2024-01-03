package be.kuleuven.dbproject.controller;

import be.kuleuven.dbproject.jdbi.*;
import be.kuleuven.dbproject.model.Game;
import be.kuleuven.dbproject.model.GameCopy;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.util.List;

public class BeheerScherm2Controller {

    @FXML
    private Button btnDelete;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnClose;
    @FXML
    private TableView<GameCopy> tblConfigs;

    private final GameCopyjdbi gameCopyJdbi = new GameCopyjdbi();
    private final Gamejdbi gameJdbi = new Gamejdbi();
    private final Platformjdbi platformjdbi = new Platformjdbi();
    private final GamePlatformjdbi gamePlatformjdbi = new GamePlatformjdbi();
    private final Museumjdbi museumjdbi = new Museumjdbi();
    private final Warenhuisjdbi warenhuisjdbi = new Warenhuisjdbi();


    public void initialize() {
        initTable();
        //loadTableData();
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

        TableColumn<GameCopy, String> col4 = new TableColumn<>("status");
        col4.setCellValueFactory(f -> new ReadOnlyObjectWrapper(getStatus(f.getValue().getWarenhuisID())));


        /*
        TableColumn<GameCopy, String> col5 = new TableColumn<>("plaats");
        col5.setCellValueFactory(f -> new ReadOnlyObjectWrapper(getPlaats(f.getValue().getWarenhuisID())));
         */

        tblConfigs.getColumns().addAll(col1,col2,col3,col4);

        tblConfigs.setItems(FXCollections.observableArrayList(gameCopyJdbi.getAll()));

        /*
        // TODO verwijderen en "echte data" toevoegen!
        int colIndex = 0;
        for(var colName : new String[]{"GameCopyID", "GamePlatformID", "MuseumID", "WarenhuisID"}) {
            TableColumn<GameCopy, String> col = new TableColumn<>(colName);
            final int finalColIndex = colIndex;
            col.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getGamecopyID(finalColIndex);
            tblConfigs.getColumns().add(col);
            colIndex++;
        }
        //tblConfigs.getItems().add(FXCollections.observableArrayList());
        for(int i = 0; i < 10; i++) {
            tblConfigs.getItems().add(FXCollections.observableArrayList("ding " + i, "categorie 1", i*10 + "", i * 33 + ""));
        }
         */
    }

    private String getTitel(int gameplatformID) {
        int gameID = gamePlatformjdbi.getGameIdByGamePlatformId(gameplatformID);
        return gameJdbi.getTitelById(gameID);
    }
    private String getPlatform(int gameplatformID) {
        int platformID = gamePlatformjdbi.getPlatformIdByGamePlatformId(gameplatformID);
        return platformjdbi.getNameById(platformID);
    }
    private String getPlaats(int id) {
        if (id == 0) {
            return museumjdbi.getMuseumNameById(id);
        }
        else return warenhuisjdbi.getWarenhuisNameById(id);
    }
    private String getStatus(int id) {
        if (id == 0) {
            return "MUSEUM";
        }
        else return "WARENHUIS";
    }


    private void addNewRow() {

    }

    private void deleteCurrentRow() {

    }

    private void modifyCurrentRow() {

    }

    public void showAlert(String title, String content) {
        var alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void verifyOneRowSelected() {
        if(tblConfigs.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Hela!", "Eerst een record selecteren hé.");
        }
    }
}
