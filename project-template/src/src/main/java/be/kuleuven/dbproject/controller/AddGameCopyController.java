package be.kuleuven.dbproject.controller;

import be.kuleuven.dbproject.jdbi.*;
import be.kuleuven.dbproject.model.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;

public class AddGameCopyController {
    @FXML
    private ChoiceBox<Game> boxGame;

    @FXML
    private ChoiceBox<Platform> boxPlatform;

    @FXML
    private ChoiceBox<Locatie> boxLocatie;

    @FXML
    private Button addBtn;

    private final Gamejdbi gameJdbi = new Gamejdbi();
    private final Platformjdbi platformJdbi = new Platformjdbi();
    private final GamePlatformjdbi gamePlatformjdbi = new GamePlatformjdbi();
    private final Warenhuisjdbi warenhuisjdbi = new Warenhuisjdbi();
    private final Museumjdbi museumjdbi = new Museumjdbi();

    //private final GameCopyjdbi gameCopyjdbi = new GameCopyjdbi();

    private GameCopy nieuweKopie;
    private boolean submitted = false;

    public void initialize() {
        boxGame.setItems(FXCollections.observableArrayList(gameJdbi.getAll()));

        // Add a listener to boxGame's selection
        boxGame.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // If a game is selected, update the platform ChoiceBox with the possible platforms
                updatePlatformChoiceBox(newValue);
            }
        });

        //boxPlatform.setItems(FXCollections.observableArrayList(platformJdbi.getAll()));

        boxLocatie.setItems(FXCollections.observableArrayList(warenhuisjdbi.getAll()));
        boxLocatie.getItems().addAll(museumjdbi.getAll());

        // Handle the button click
        addBtn.setOnAction(e -> handleAddBtn());
    }

    private void updatePlatformChoiceBox(Game selectedGame) {
        // Get the possible platforms for the selected game
        List<Platform> possiblePlatforms = gamePlatformjdbi.getPlatformsForGame(selectedGame.getGameID());

        // Update the platform ChoiceBox with the possible platforms
        boxPlatform.setItems(FXCollections.observableArrayList(possiblePlatforms));
    }

    private void handleAddBtn() {
        // Retrieve selected values from ChoiceBoxes
        Game selectedGame = boxGame.getValue();
        Platform selectedPlatform = boxPlatform.getValue();
        Locatie selectedLocatie = boxLocatie.getValue();

        // Perform validation
        if (selectedGame == null || selectedPlatform == null || selectedLocatie == null) {
            // Handle validation error, e.g., show an alert to the user
            return;
        }

        // Create a new GameCopy instance
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
        submitted = true;
        closeForm();
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public GameCopy getNewCopy() {
        return nieuweKopie;
    }

    private void closeForm() {
        Stage stage = (Stage) addBtn.getScene().getWindow();
        stage.close();
    }
}
