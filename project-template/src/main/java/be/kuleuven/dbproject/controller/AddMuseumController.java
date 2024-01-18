package be.kuleuven.dbproject.controller;

import be.kuleuven.dbproject.model.Museum;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static be.kuleuven.dbproject.MyUtility.showAlert;

public class AddMuseumController implements AddItemController<Museum>{
    @FXML
    private TextField naamField;
    @FXML
    private TextField inkomprijsField;
    @FXML
    private TextField adresField;
    @FXML
    private Button addMuseum;

    private Museum nieuwMuseum;
    private boolean submitted = false;

    @Override
    public void initialize() {
        addMuseum.setOnAction(e -> handleAddBtn());
    }

    private void handleAddBtn() {
        if (naamField.getText().isBlank() || inkomprijsField.getText().isBlank() || adresField.getText().isBlank()) {
            showAlert("Error", "Vul de velden in, Aub");
            naamField.clear();
            inkomprijsField.clear();
            adresField.clear();
        }
        else {
            String inkomText = inkomprijsField.getText();
            double inkomPrijs = 0.0;
            try {
                inkomPrijs = Double.parseDouble(inkomText);
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Please enter a valid integer.");
            }
            nieuwMuseum = new Museum(naamField.getText(), inkomPrijs, adresField.getText());
            submitted = true;
            closeForm();
        }
    }
    @Override
    public boolean isSubmitted() {
        return submitted;
    }
    @Override
    public Museum getNewItem() {
        return nieuwMuseum;
    }

    private void closeForm() {
        Stage stage = (Stage) addMuseum.getScene().getWindow();
        stage.close();
    }

}
