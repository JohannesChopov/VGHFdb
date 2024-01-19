package be.kuleuven.dbproject.controller.Overzicht;

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

public class OverzichtWarenhuisController {

    @FXML
    private Button btnClose;
    @FXML
    private TableView<Warenhuis> tblConfigWarenhuis;
    @FXML
    private TableView<GameCopy> tblConfigsGameCopies;

    private final Gamejdbi gameJdbi = new Gamejdbi();
    private final Platformjdbi platformjdbi = new Platformjdbi();
    private final GamePlatformjdbi gamePlatformjdbi = new GamePlatformjdbi();
    private final Warenhuisjdbi Warenhuisjdbi = new Warenhuisjdbi();
    private final GameCopyjdbi gameCopyJdbi = new GameCopyjdbi();
    private final Warenhuisjdbi warenhuisjdbi = new Warenhuisjdbi();

    public void initialize() {
        initTableMusea();
        btnClose.setOnAction(e -> {
            var stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        });

        tblConfigWarenhuis.setOnMouseClicked(e -> {
            if(e.getClickCount() == 1 && tblConfigWarenhuis.getSelectionModel().getSelectedItem() != null) {
                var selectedWarenhuis = tblConfigWarenhuis.getSelectionModel().getSelectedItem();
                initInfoTable1(selectedWarenhuis);
            }
        });
    }

    private void initTableMusea() {
        tblConfigWarenhuis.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblConfigWarenhuis.getColumns().clear();

        TableColumn<Warenhuis, String> col1 = new TableColumn<>("Naam");
        col1.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getNaam()));
        TableColumn<Warenhuis, String> col2 = new TableColumn<>("Adres");
        col2.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getAdres()));
        TableColumn<Warenhuis, String> col3 = new TableColumn<>("Aantal Copies");
        col3.setCellValueFactory(f -> new ReadOnlyObjectWrapper(getAantalCopies(f.getValue())));

        tblConfigWarenhuis.getColumns().addAll(col1,col2,col3);
        tblConfigWarenhuis.setItems(FXCollections.observableArrayList(warenhuisjdbi.getAll()));
    }

    private void initInfoTable1(Warenhuis selectedWarenhuis) {
        tblConfigsGameCopies.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblConfigsGameCopies.getColumns().clear();

        TableColumn<GameCopy, String> col1 = new TableColumn<>("GameCopyID");
        col1.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getGamecopyID()));
        TableColumn<GameCopy, String> col2 = new TableColumn<>("Game title");
        col2.setCellValueFactory(f -> new ReadOnlyObjectWrapper(gameJdbi.getTitelById(gamePlatformjdbi.getGameIdByGamePlatformId(f.getValue().getGameplatformID()))));
        TableColumn<GameCopy, String> col3 = new TableColumn<>("Platform");
        col3.setCellValueFactory(f -> new ReadOnlyObjectWrapper(getPlatform(f.getValue().getGameplatformID())));

        tblConfigsGameCopies.getColumns().addAll(col1,col2,col3);
        tblConfigsGameCopies.setItems(FXCollections.observableArrayList(gameCopyJdbi.getGameCopyByWarenhuisId(selectedWarenhuis.getID())));
    }


    private int getAantalCopies(Warenhuis Warenhuis) {
        return Warenhuisjdbi.getGameCopyCountByWarenhuis(Warenhuis);
    }


    private String getPlatform(int gameplatformID) {
        int platformID = gamePlatformjdbi.getPlatformIdByGamePlatformId(gameplatformID);
        return platformjdbi.getNameById(platformID);
    }
}
