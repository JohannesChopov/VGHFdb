package be.kuleuven.dbproject.controller;

import be.kuleuven.dbproject.model.Game;
import be.kuleuven.dbproject.model.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;



public class AddPlatformController implements AddItemController<Platform>{
    @FXML
    private TextField Platformname;

    @FXML
    private Button btnAddPlatform;

    private Platform nieuwePlatform;
    private boolean submitted = false;
    @Override
    public void initialize() {
        btnAddPlatform.setOnAction(e -> handleAddBtn());
    }

    private void handleAddBtn() {
        // Update the game details based on the form fields
        nieuwePlatform = new Platform(Platformname.getText());
        submitted = true;
        closeForm();
    }
    @Override
    public boolean isSubmitted() {
        return submitted;
    }
    @Override
    public Platform getNewItem() {
        return nieuwePlatform;
    }

    private void closeForm() {
        Stage stage = (Stage) btnAddPlatform.getScene().getWindow();
        stage.close();
    }
}
