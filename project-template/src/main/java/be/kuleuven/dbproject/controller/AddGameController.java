package be.kuleuven.dbproject.controller;

import be.kuleuven.dbproject.jdbi.GamePlatformjdbi;
import be.kuleuven.dbproject.jdbi.Gamejdbi;
import be.kuleuven.dbproject.jdbi.Platformjdbi;
import be.kuleuven.dbproject.model.Game;
import be.kuleuven.dbproject.model.GamePlatform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class AddGameController {

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

    public void initialize() {
        ObservableList<String> platformNames = FXCollections.observableArrayList(platformJdbi.getAllPlatformNames());
        platformListView.setItems(platformNames);
        platformListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        addBtn.setOnAction(e -> handleAddBtn());
    }

    private void handleAddBtn() {
        // Update the game details based on the form fields
        nieuweGame = new Game(titelField.getText(), genreField.getText());
        // Get the selected platforms
        ObservableList<String> selectedPlatforms = platformListView.getSelectionModel().getSelectedItems();

        // Insert the new game into the database
        gameJdbi.insert(nieuweGame);
        int gameId = gameJdbi.getIdByTitel(titelField.getText());

        // Insert selected platforms for the new game
        for (String platformName : selectedPlatforms) {
            int platformId = platformJdbi.getIdByName(platformName);
            GamePlatform gamePlatform = new GamePlatform(gameId,platformId);
            gamePlatformJdbi.insert(gamePlatform);
        }

        submitted = true;
        closeForm();
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public Game getNewGame() {
        return nieuweGame;
    }

    private void closeForm() {
        Stage stage = (Stage) addBtn.getScene().getWindow();
        stage.close();
    }
}
