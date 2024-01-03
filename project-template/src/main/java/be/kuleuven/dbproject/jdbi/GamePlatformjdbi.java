package be.kuleuven.dbproject.jdbi;


import be.kuleuven.dbproject.model.GamePlatform;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class GamePlatformjdbi {
    private final Jdbi jdbi;

    public GamePlatformjdbi() {
        this.jdbi = JDBIManager.getJdbi();
    }

    public List<GamePlatform> getAll() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM GamePlatform").mapToBean(GamePlatform.class).list());
    }

    public void insert(GamePlatform gamePlatform) {
        jdbi.useHandle(handle -> handle.createUpdate("INSERT INTO GamePlatform (gameplatformID, gameID, platformID) VALUES (:gameplatformID, :gameID, :platformID)")
                .bindBean(gamePlatform)
                .execute());
    }

    public void update(GamePlatform gamePlatformNieuw, GamePlatform gamePlatformOud) {
        jdbi.useHandle(handle -> handle.createUpdate("UPDATE GamePlatform SET (gameplatformID, gameID, platformID) = (:gameplatformID, :gameID, :platformID) WHERE gameplatformID = :GamePlatformIDOud")
                .bindBean(gamePlatformNieuw)
                .bind("GamePlatformIDOud", gamePlatformOud.getGameplatformID())
                .execute());
    }

    public void delete(GamePlatform GamePlatform) {
        jdbi.useHandle(handle -> handle.createUpdate("DELETE FROM GamePlatform WHERE GamePlatformID = :GamePlatformID")
                .bind("GamePlatformID", GamePlatform.getGameplatformID())
                .execute());
    }


    public int getId(GamePlatform GamePlatform) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT id FROM GamePlatform WHERE GamePlatformID = :GamePlatformID")
                .bind("GamePlatformID", GamePlatform.getGameplatformID())
                .mapTo(Integer.class).list().get(0));
    }

    public int getGameIdByGamePlatformId(int gamePlatformId) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT gameID FROM GamePlatform WHERE gameplatformID = :gamePlatformID")
                .bind("gamePlatformID", gamePlatformId)
                .mapTo(Integer.class)
                .one());

    }

    public int getPlatformIdByGamePlatformId(int gamePlatformId) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT platformID FROM GamePlatform WHERE gameplatformID = :gamePlatformID")
                .bind("gamePlatformID", gamePlatformId)
                .mapTo(Integer.class)
                .one());

    }

}
