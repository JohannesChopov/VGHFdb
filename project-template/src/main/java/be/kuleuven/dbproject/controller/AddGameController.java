package be.kuleuven.dbproject.controller;

import be.kuleuven.dbproject.model.Game;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddGameController {

    @FXML
    private TextField titelField;
    @FXML
    private TextField genreField;
    @FXML
    private Button addBtn;

    private Game nieuweGame;
    private boolean submitted = false;

    public void initialize() {
        addBtn.setOnAction(e -> handleAddBtn());
    }

    private void handleAddBtn() {
        // Update the game details based on the form fields
        nieuweGame = new Game(titelField.getText(), genreField.getText());
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
