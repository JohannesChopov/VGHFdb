package be.kuleuven.dbproject.controller;

import be.kuleuven.dbproject.jdbi.GamePlatformjdbi;
import be.kuleuven.dbproject.jdbi.Gamejdbi;
import be.kuleuven.dbproject.model.Game;
import be.kuleuven.dbproject.model.GameCopy;
import be.kuleuven.dbproject.model.Locatie;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class VerplaatsCopyController {
    @FXML
    private Text gameField;
    @FXML
    private Text plaatsField;
    @FXML
    private Button bewerkBtn;
    @FXML
    private ChoiceBox<Locatie> boxLocatie;

    private final GamePlatformjdbi gamePlatformjdbi = new GamePlatformjdbi();
    private final Gamejdbi gameJdbi = new Gamejdbi();

    //private Game updatedGame;
    private boolean submitted = false;

    public void initialize(GameCopy copy) {
        bewerkBtn.setOnAction(e -> handleBewerkBtn());

        // Initialize the form with the details of the selected game
        gameField.setText(gameJdbi.getTitelById(gamePlatformjdbi.getGameIdByGamePlatformId(copy.getGameplatformID())));
        //plaatsField.setText(copy.getPlaatsID());
        // Save the selected game for later reference
        //idText.setText("id: "+ game.getGameID());
        //updatedGame = copy;
    }

    @FXML
    private void handleBewerkBtn() {
        // Update the game details based on the form fields
        //updatedGame.setTitel(gameField.getText());
        //updatedGame.setGenre(plaatsField.getText());

        submitted = true;
        closeForm();
    }

    public boolean isSubmitted() {
        return submitted;
    }

    /*
    public Game getUpdatedGame() {
        return updatedGame;
    }
     */

    private void closeForm() {
        Stage stage = (Stage) bewerkBtn.getScene().getWindow();

        stage.close();
    }
}
