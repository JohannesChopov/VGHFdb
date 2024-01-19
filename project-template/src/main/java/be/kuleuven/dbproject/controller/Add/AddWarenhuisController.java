package be.kuleuven.dbproject.controller.Add;

import be.kuleuven.dbproject.jdbi.Museumjdbi;
import be.kuleuven.dbproject.jdbi.Warenhuisjdbi;
import be.kuleuven.dbproject.model.Warenhuis;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static be.kuleuven.dbproject.MyUtility.showAlert;

public class AddWarenhuisController implements AddItemController<Warenhuis>{
    @FXML
    private TextField naamField;
    @FXML
    private TextField adresField;
    @FXML
    private Button addWarenhuis;

    private Warenhuis nieuwWarenhuis;
    private boolean submitted = false;

    private final Warenhuisjdbi warenhuisjdbi = new Warenhuisjdbi();

    @Override
    public void initialize() {
        addWarenhuis.setOnAction(e -> handleAddBtn());
    }

    private void handleAddBtn() {
        if (naamField.getText().isBlank() || adresField.getText().isBlank() || adresField.getText().isBlank()) {
            showAlert("Error", "Vul de velden in, Aub");
            naamField.clear();
            adresField.clear();
            return;
        }
        if (warenhuisjdbi.getWarenhuisByName(naamField.getText()) != null) {
            showAlert("Error", "Naam al in gebruik.");
            return;
        }
        else {
            nieuwWarenhuis = new Warenhuis(naamField.getText(), adresField.getText());
            submitted = true;
            closeForm();
        }
    }
    @Override
    public boolean isSubmitted() {
        return submitted;
    }
    @Override
    public Warenhuis getNewItem() {
        return nieuwWarenhuis;
    }

    private void closeForm() {
        Stage stage = (Stage) addWarenhuis.getScene().getWindow();
        stage.close();
    }

}
