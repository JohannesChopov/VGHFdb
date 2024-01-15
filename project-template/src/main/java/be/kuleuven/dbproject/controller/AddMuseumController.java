package be.kuleuven.dbproject.controller;

import be.kuleuven.dbproject.jdbi.Museumjdbi;
import be.kuleuven.dbproject.model.Bezoeker;
import be.kuleuven.dbproject.model.Locatie;
import be.kuleuven.dbproject.model.Museum;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddMuseumController {
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

    public void initialize() {
        addMuseum.setOnAction(e -> handleAddBtn());
    }

    private void handleAddBtn() {
        String inkomText = inkomprijsField.getText();
        double inkomPrijs = 0.0;
        try {
            // Convert the text to an int
            inkomPrijs = Double.parseDouble(inkomText);

            // You can use the 'museumID' variable as needed in your code
        } catch (NumberFormatException e) {
            // Handle the case where the input is not a valid integer
            System.err.println("Invalid input. Please enter a valid integer.");
        }
        // Update the game details based on the form fields
        nieuwMuseum = new Museum(naamField.getText(), inkomPrijs, adresField.getText());
        submitted = true;
        closeForm();
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public Museum getNewMuseum() {
        return nieuwMuseum;
    }

    private void closeForm() {
        Stage stage = (Stage) addMuseum.getScene().getWindow();
        stage.close();
    }

}
