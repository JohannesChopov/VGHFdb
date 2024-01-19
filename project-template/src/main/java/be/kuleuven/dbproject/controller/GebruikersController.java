package be.kuleuven.dbproject.controller;

import be.kuleuven.dbproject.jdbi.Userjdbi;
import be.kuleuven.dbproject.model.MuseumBezoek;
import be.kuleuven.dbproject.model.User;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import static be.kuleuven.dbproject.MyUtility.showAlert;

public class GebruikersController {
    @FXML
    private Button btnVerwijder;
    @FXML
    private Button btnTerug;
    @FXML
    private TableView<User> tblGebruikers;

    private final Userjdbi userJdbi = new Userjdbi();

    public void initialize() {
        initTable();
        btnVerwijder.setOnAction(e -> {
            verifyOneRowSelected();
            verwijderUser();
            refreshTable();
        });
        btnTerug.setOnAction(e -> goBack());
    }

    private void refreshTable() {
        tblGebruikers.setItems(FXCollections.observableArrayList(userJdbi.getAll()));
    }

    private void initTable() {
        tblGebruikers.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblGebruikers.getColumns().clear();

        TableColumn<User, String> col1 = new TableColumn<>("ID");
        col1.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getUserID()));
        TableColumn<User, String> col2 = new TableColumn<>("Username");
        col2.setCellValueFactory(f -> new ReadOnlyObjectWrapper(f.getValue().getUsername()));

        tblGebruikers.getColumns().addAll(col1,col2);
        tblGebruikers.setItems(FXCollections.observableArrayList(userJdbi.getAll()));
    }

    private void verwijderUser() {
        TableView<User> selectedTable = tblGebruikers;
        if (selectedTable != null) {
            User user = selectedTable.getSelectionModel().getSelectedItem();
            if (user!= null) {
                try {
                    userJdbi.delete(user);
                } catch (Exception e) {
                    showAlert("Error", "Error deleting the selected item.");
                }
            }
        }
        //ShowAlert("Success", "Registratie succesvol!");
    }

    private void verifyOneRowSelected() {
        if(tblGebruikers.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Hela!", "Eerst een user selecteren.");
        }
    }

    private void goBack() {
        closeForm();
    }

    private void closeForm() {
        Stage stage = (Stage) btnTerug.getScene().getWindow();
        stage.close();
    }
}
