package be.kuleuven.dbproject.controller;

import be.kuleuven.dbproject.jdbi.GamePlatformjdbi;
import be.kuleuven.dbproject.jdbi.Gamejdbi;
import be.kuleuven.dbproject.jdbi.Museumjdbi;
import be.kuleuven.dbproject.jdbi.Warenhuisjdbi;
import be.kuleuven.dbproject.model.GameCopy;
import be.kuleuven.dbproject.model.Locatie;
import be.kuleuven.dbproject.model.Museum;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class VerplaatsCopyController {
    @FXML
    private Text gameField;
    @FXML
    private Button bewerkBtn;
    @FXML
    private ChoiceBox<Locatie> boxLocatie;

    private final GamePlatformjdbi gamePlatformjdbi = new GamePlatformjdbi();
    private final Gamejdbi gameJdbi = new Gamejdbi();
    private final Museumjdbi museumjdbi = new Museumjdbi();
    private final Warenhuisjdbi warenhuisjdbi = new Warenhuisjdbi();

    private Locatie nieuweLocatie;
    private GameCopy updatedCopy;
    private boolean submitted = false;

    public void initialize(GameCopy copy) {
        bewerkBtn.setOnAction(e -> handleBewerkBtn());

        gameField.setText(gameJdbi.getTitelById(gamePlatformjdbi.getGameIdByGamePlatformId(copy.getGameplatformID())));
        boxLocatie.setValue(getPlaats(copy));

        boxLocatie.setItems(FXCollections.observableArrayList(warenhuisjdbi.getAll()));
        boxLocatie.getItems().addAll(museumjdbi.getAll());

        updatedCopy = copy;
    }

    private Locatie getPlaats(GameCopy gamecopy) {
        if (gamecopy.getWarenhuisID() == null) {
            return museumjdbi.getMuseumById(gamecopy.getMuseumID());
        }
        else return warenhuisjdbi.getWarenhuisById(gamecopy.getWarenhuisID());
    }

    @FXML
    private void handleBewerkBtn() {
        nieuweLocatie = boxLocatie.getValue();
        int id = nieuweLocatie.getID();

        if (nieuweLocatie instanceof Museum) {
            updatedCopy.setMuseumID(id);
            updatedCopy.setWarenhuisID(null);
        } else {
            updatedCopy.setMuseumID(null);
            updatedCopy.setWarenhuisID(id);
        }

        submitted = true;
        closeForm();
    }

    public boolean isSubmitted() {
        return submitted;
    }


    public GameCopy getUpdatedCopy() {
        return updatedCopy;
    }

    private void closeForm() {
        Stage stage = (Stage) bewerkBtn.getScene().getWindow();
        stage.close();
    }
}
