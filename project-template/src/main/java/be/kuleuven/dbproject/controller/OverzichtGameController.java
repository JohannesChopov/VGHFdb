package be.kuleuven.dbproject.controller;

import be.kuleuven.dbproject.jdbi.*;
import be.kuleuven.dbproject.model.Game;
import be.kuleuven.dbproject.model.GameCopy;
import be.kuleuven.dbproject.model.GamePlatform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;


public class OverzichtGameController {
    @FXML
    private Button btnClose;
    @FXML
    private TableView<Game> tblConfigsGames;
    @FXML
    private TableView<GameCopy> tblInfo;

    private final Gamejdbi gameJdbi = new Gamejdbi();
    private final GameCopyjdbi gameCopyJdbi = new GameCopyjdbi();
    private final GamePlatformjdbi gamePlatformjdbi = new GamePlatformjdbi();
    private final Platformjdbi platformjdbi = new Platformjdbi();
    private final Museumjdbi museumjdbi = new Museumjdbi();
    private final Warenhuisjdbi warenhuisjdbi = new Warenhuisjdbi();

    public void initialize() {
        initTableGames();
        btnClose.setOnAction(e -> {
            var stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        });

        tblConfigsGames.setOnMouseClicked(e -> {
            if(e.getClickCount() == 1 && tblConfigsGames.getSelectionModel().getSelectedItem() != null) {
                System.out.println(tblConfigsGames.getSelectionModel().getSelectedItem());
                var selectedGame = tblConfigsGames.getSelectionModel().getSelectedItem();
                initInfoTable(selectedGame);
            }
        });
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

    private void initInfoTable(Game selectedGame) {
        tblInfo.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblInfo.getColumns().clear();

        TableColumn<GameCopy, String> col1 = new TableColumn<>("GameCopyID");
        col1.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getGamecopyID()));
        TableColumn<GameCopy, String> col2 = new TableColumn<>("titel");
        col2.setCellValueFactory(f -> new ReadOnlyObjectWrapper(getTitel(f.getValue().getGameplatformID())));
        TableColumn<GameCopy, String> col3 = new TableColumn<>("platform");
        col3.setCellValueFactory(f -> new ReadOnlyObjectWrapper(getPlatform(f.getValue().getGameplatformID())));
        TableColumn<GameCopy, String> col4 = new TableColumn<>("plaats");
        col4.setCellValueFactory(f -> new ReadOnlyObjectWrapper(getPlaats(f.getValue())));
        TableColumn<GameCopy, String> col5 = new TableColumn<>("adres");
        col5.setCellValueFactory(f -> new ReadOnlyObjectWrapper(getAdres(f.getValue())));

        tblInfo.getColumns().addAll(col1,col2,col3,col4,col5);
        tblInfo.setItems(FXCollections.observableArrayList(gameCopyJdbi.getGameCopiesByGameID(selectedGame.getGameID())));
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
    private String getAdres(GameCopy gamecopy) {
        if (gamecopy.getWarenhuisID() == null) {
            return museumjdbi.getAdresById(gamecopy.getMuseumID());
        }
        else return warenhuisjdbi.getAdresById(gamecopy.getWarenhuisID());
    }

    public void showAlert(String title, String content) {
        var alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
