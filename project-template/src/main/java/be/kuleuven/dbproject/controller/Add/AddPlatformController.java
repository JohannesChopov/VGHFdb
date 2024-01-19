package be.kuleuven.dbproject.controller.Add;

import be.kuleuven.dbproject.jdbi.Platformjdbi;
import be.kuleuven.dbproject.model.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static be.kuleuven.dbproject.MyUtility.showAlert;


public class AddPlatformController implements AddItemController<Platform>{
    @FXML
    private TextField naamField;

    @FXML
    private Button btnAddPlatform;

    private Platform nieuwePlatform;
    private boolean submitted = false;

    private final Platformjdbi platformJdbi = new Platformjdbi();

    @Override
    public void initialize() {
        btnAddPlatform.setOnAction(e -> handleAddBtn());
    }

    private void handleAddBtn() {
        if (naamField.getText().isBlank()) {
            showAlert("Error", "Vul de velden in, Aub");
            naamField.clear();
            return;
        }
        if (platformJdbi.getPlatformByName(naamField.getText()) != null) {
            showAlert("Error", "Platform is reeds in database.");
            return;
        }
        else {
            nieuwePlatform = new Platform(naamField.getText());
            submitted = true;
            closeForm();
        }
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
