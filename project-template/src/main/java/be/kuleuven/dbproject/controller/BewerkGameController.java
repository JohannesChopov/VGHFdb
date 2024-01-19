package be.kuleuven.dbproject.controller;

import be.kuleuven.dbproject.model.Game;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BewerkGameController {

    @FXML
    private TextField nieuweNaamField;
    @FXML
    private TextField nieuwGenreField;
    @FXML
    private Button bewerkBtn;
    @FXML
    private Text idText;

    private Game updatedGame;
    private boolean submitted = false;

    public void initialize(Game game) {
        bewerkBtn.setOnAction(e -> handleBewerkBtn());

        nieuweNaamField.setText(game.getTitel());
        nieuwGenreField.setText(game.getGenre());

        idText.setText("id: "+ game.getGameID());
        updatedGame = game;
    }

    @FXML
    private void handleBewerkBtn() {
        updatedGame.setTitel(nieuweNaamField.getText());
        updatedGame.setGenre(nieuwGenreField.getText());

        submitted = true;
        closeForm();
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public Game getUpdatedGame() {
        return updatedGame;
    }

    private void closeForm() {
        Stage stage = (Stage) bewerkBtn.getScene().getWindow();
        stage.close();
    }
}
