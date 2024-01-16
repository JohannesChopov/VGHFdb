package be.kuleuven.dbproject.jdbi;


import be.kuleuven.dbproject.model.Game;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class Gamejdbi {
    private final Jdbi jdbi;

    public Gamejdbi() {
        this.jdbi = JDBIManager.getJdbi();
    }

    public List<Game> getAll() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM Game")
                .mapToBean(Game.class).list());
    }

    public void insert(Game game) {
        jdbi.useHandle(handle -> handle.createUpdate("INSERT INTO Game (titel, genre) VALUES (:titel, :genre)")
                .bindBean(game)
                .execute());
    }

    public void update(Game gameNieuw, Game gameOud) {
        jdbi.useHandle(handle -> handle.createUpdate("UPDATE Game SET gameID = :gameID, titel = :titel, genre = :genre WHERE gameID = :gameIDOud")
                .bindBean(gameNieuw)
                .bind("gameIDOud", gameOud.getGameID())
                .execute());
    }

    public void delete(Game game) {
        jdbi.useTransaction(handle -> {
            // Delete from GameCopy first
            handle.createUpdate("DELETE FROM GameCopy WHERE gameplatformID IN (SELECT gameplatformID FROM GamePlatform WHERE gameID = :gameID)")
                    .bind("gameID", game.getGameID())
                    .execute();

            // Delete from GamePlatform
            handle.createUpdate("DELETE FROM GamePlatform WHERE gameID = :gameID")
                    .bind("gameID", game.getGameID())
                    .execute();

            // Delete from Game
            handle.createUpdate("DELETE FROM Game WHERE gameID = :gameID")
                    .bind("gameID", game.getGameID())
                    .execute();
        });
    }



    public int getId(Game game) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT gameID FROM Game WHERE gameID = :gameID")
                .bind("gameID", game.getGameID())
                .mapTo(Integer.class).list().get(0));
    }

    public String getTitelById(int id) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT titel FROM Game WHERE gameID = :id")
                .bind("id", id)
                .mapTo(String.class)
                .one());
    }

    public int getIdByTitel(String titel) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT gameID FROM Game WHERE titel = :titel")
                .bind("titel", titel)
                .mapTo(Integer.class)
                .one());
    }

}
