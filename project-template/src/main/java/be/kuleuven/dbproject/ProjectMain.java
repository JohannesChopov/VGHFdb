package be.kuleuven.dbproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import be.kuleuven.dbproject.jdbi.JDBIManager;
/**
 * DB Taak 2022-2023: De Vrolijke Zweters
 * Zie https://kuleuven-diepenbeek.github.io/db-course/extra/project/ voor opgave details
 *
 * Deze code is slechts een quick-start om je op weg te helpen met de integratie van JavaFX tabellen en data!
 * Zie README.md voor meer informatie.
 */
public class ProjectMain extends Application {

    private static Stage rootStage;

    public static Stage getRootStage() {
        return rootStage;
    }

    @Override
    public void start(Stage stage) throws Exception {
        rootStage = stage;
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));

        Scene scene = new Scene(root);

        stage.setTitle("Administratie hoofdscherm");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        JDBIManager.getJdbi();
        launch();
    }
}
