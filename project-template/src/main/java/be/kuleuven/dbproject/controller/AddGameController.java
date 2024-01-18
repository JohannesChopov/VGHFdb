package be.kuleuven.dbproject.controller;

import be.kuleuven.dbproject.jdbi.GamePlatformjdbi;
import be.kuleuven.dbproject.jdbi.Gamejdbi;
import be.kuleuven.dbproject.jdbi.Platformjdbi;
import be.kuleuven.dbproject.model.Game;
import be.kuleuven.dbproject.model.GamePlatform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import static be.kuleuven.dbproject.MyUtility.showAlert;

public class AddGameController implements AddItemController<Game>{

    @FXML
    private TextField titelField;
    @FXML
    private TextField genreField;
    @FXML
    private Button addBtn;
    @FXML
    private ListView<String> platformListView;

    private final Platformjdbi platformJdbi = new Platformjdbi();
    private final GamePlatformjdbi gamePlatformJdbi = new GamePlatformjdbi();
    private final Gamejdbi gameJdbi = new Gamejdbi();

    private Game nieuweGame;
    private boolean submitted = false;

    @Override
    public void initialize() {
        ObservableList<String> platformNames = FXCollections.observableArrayList(platformJdbi.getAllPlatformNames());
        platformListView.setItems(platformNames);
        platformListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        addBtn.setOnAction(e -> {
            handleAddBtn();
        });
    }

    private void handleAddBtn() {
        if (titelField.getText().isBlank() || genreField.getText().isBlank() ||platformListView.getSelectionModel().isEmpty()) {
            showAlert("Error", "Vul velden in aub");
            titelField.clear();
            genreField.clear();
        }
        else {
            nieuweGame = new Game(titelField.getText(), genreField.getText());
            ObservableList<String> selectedPlatforms = platformListView.getSelectionModel().getSelectedItems();

            gameJdbi.insert(nieuweGame);
            int gameId = gameJdbi.getIdByTitel(titelField.getText());

            for (String platformName : selectedPlatforms) {
                int platformId = platformJdbi.getIdByName(platformName);
                GamePlatform gamePlatform = new GamePlatform(gameId,platformId);
                gamePlatformJdbi.insert(gamePlatform);
            }
            submitted = true;
            closeForm();
        }
    }
    @Override
    public boolean isSubmitted() {
        return submitted;
    }
    @Override
    public Game getNewItem() {
        return nieuweGame;
    }

    private void closeForm() {
        Stage stage = (Stage) addBtn.getScene().getWindow();
        stage.close();
    }
}
