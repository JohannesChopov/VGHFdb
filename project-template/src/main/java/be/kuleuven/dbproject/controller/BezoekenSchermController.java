package be.kuleuven.dbproject.controller;

import be.kuleuven.dbproject.jdbi.*;
import be.kuleuven.dbproject.model.Bezoeker;
import be.kuleuven.dbproject.model.Game;
import be.kuleuven.dbproject.model.GameCopy;
import be.kuleuven.dbproject.model.MuseumBezoek;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class BezoekenSchermController {

    @FXML
    private Button btnDelete;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnClose;
    @FXML
    private TableView<MuseumBezoek> tblBezoeken;

    private final GameCopyjdbi gameCopyJdbi = new GameCopyjdbi();
    private final Gamejdbi gameJdbi = new Gamejdbi();
    private final Platformjdbi platformjdbi = new Platformjdbi();
    private final GamePlatformjdbi gamePlatformjdbi = new GamePlatformjdbi();
    private final Museumjdbi museumjdbi = new Museumjdbi();
    private final Warenhuisjdbi warenhuisjdbi = new Warenhuisjdbi();
    private final MuseumBezoekjdbi museumbezoekjdbi = new MuseumBezoekjdbi();


    public void initialize() {
        initTableBezoeken();
        btnAdd.setOnAction(e -> addNewRow());

        /*
        btnDelete.setOnAction(e -> {
            verifyOneRowSelected();
            deleteCurrentRow();
        });

         */

        btnClose.setOnAction(e -> {
            var stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        });
        /*
        tblBezoeken.setOnMouseClicked(e -> {
            if(e.getClickCount() == 2 && tblBezoeken.getSelectionModel().getSelectedItem() != null) {
                var selectedRow = tblBezoeken.getSelectionModel().getSelectedItem();
                modifyCopyDoubleClick(selectedRow);
            }
        });
         */
    }

    private void initTableBezoeken() {
        tblBezoeken.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblBezoeken.getColumns().clear();

        TableColumn<MuseumBezoek, String> col1 = new TableColumn<>("BezoekID");
        col1.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getMuseumbezoekID()));
        TableColumn<MuseumBezoek, String> col2 = new TableColumn<>("BezoekerID");
        col2.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getBezoekerID()));
        TableColumn<MuseumBezoek, String> col3 = new TableColumn<>("Bezoeker");
        col3.setCellValueFactory(f -> new ReadOnlyObjectWrapper(museumbezoekjdbi.getBezoekerById(f.getValue().getBezoekerID()).getNaam()));
        TableColumn<MuseumBezoek, String> col4 = new TableColumn<>("Museum");
        col4.setCellValueFactory(f -> new ReadOnlyObjectWrapper(museumbezoekjdbi.getMuseumById(f.getValue().getMuseumID()).getNaam()));
        TableColumn<MuseumBezoek, String> col5 = new TableColumn<>("Datum");
        col5.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getTijdsstip()));

        tblBezoeken.getColumns().addAll(col1,col2,col3,col4,col5);
        tblBezoeken.setItems(FXCollections.observableArrayList(museumbezoekjdbi.getAll()));
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
    private String getStatus(int id) {
        if (id == 0) {
            return "MUSEUM";
        }
        else return "WARENHUIS";
    }

    private void addNewRow() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("addMuseumBezoek.fxml"));
            var root = (AnchorPane) loader.load();
            var scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Voeg bezoek toe");
            stage.initModality(Modality.APPLICATION_MODAL);

            AddMuseumBezoekController controller = loader.getController();
            controller.initialize();
            // Show the form and wait for it to be closed
            stage.showAndWait();

            // After the form is closed, check if it was submitted
            if (controller.isSubmitted()) {
                museumbezoekjdbi.insert(controller.getNewItem());
                refreshTables();
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Error opening the add form.");
        }
    }
    /*
    private void modifyDoubleClick(MuseumBezoek selectedRow) {
        MuseumBezoek selected = selectedRow;
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
                    // Update the item in the database
                    //gameJdbi.update(controller.getUpdatedGame(), selected);

                    // Refresh the table to reflect changes
                    refreshTables();
                }
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Error opening the edit form.");
            }
        }
    }
     */

    private void refreshTables() {
        try {
            initTableBezoeken();
        } catch (Exception e) {
            e.printStackTrace(); // Print the exception details for debugging
            showAlert("Error", "Error refreshing tables.");
        }

    }

    /*
    private void deleteCurrentRow() {
        TableView<GameCopy> selectedTable = tblConfigs;
        System.out.println("1");
        if (selectedTable != null) {
            GameCopy selectedGameCopy = selectedTable.getSelectionModel().getSelectedItem();
            System.out.println("2");
            if (selectedGameCopy!= null) {
                try {
                    // Delete from the Game table
                    gameCopyJdbi.delete(selectedGameCopy);
                    //selectedTable.getItems().remove(selectedGame);

                    // Refresh other tables
                    refreshTables();
                } catch (Exception e) {
                    showAlert("Error", "Error deleting the selected item.");
                }
            }
        }
    }
     */


    public void showAlert(String title, String content) {
        var alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void verifyOneRowSelected() {
        if(tblBezoeken.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Hela!", "Eerst een record selecteren hé.");
        }
    }
}
