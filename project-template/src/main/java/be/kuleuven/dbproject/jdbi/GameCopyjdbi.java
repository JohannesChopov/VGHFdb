package be.kuleuven.dbproject.jdbi;


import be.kuleuven.dbproject.model.Donatie;
import be.kuleuven.dbproject.model.Game;
import be.kuleuven.dbproject.model.GameCopy;
import org.jdbi.v3.core.Jdbi;

import java.util.List;
import java.util.Optional;

public class GameCopyjdbi {
    private final Jdbi jdbi;

    public GameCopyjdbi() {
        this.jdbi = JDBIManager.getJdbi();
    }

    public List<GameCopy> getAll() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM GameCopy")
                .mapToBean(GameCopy.class)
                .list());
    }

    public void insert(GameCopy gamecopy) {
        jdbi.useHandle(handle -> handle.createUpdate("INSERT INTO GameCopy (gameplatformID, museumID, warenhuisID) VALUES (:gameplatformID, :museumID, :warenhuisID)")
                .bindBean(gamecopy)
                .execute());
    }

    public void update(GameCopy gamecopyNieuw, GameCopy gamecopyOud) {
        jdbi.useHandle(handle -> handle.createUpdate("UPDATE GameCopy SET gameplatformID = :gameplatformID, museumID = :museumID, warenhuisID = :warenhuisID WHERE GameCopyID = :GameCopyIDOud")
                .bindBean(gamecopyNieuw)
                .bind("GameCopyIDOud", gamecopyOud.getGamecopyID())
                .execute());
    }

    public void delete(GameCopy gamecopy) {
        jdbi.useHandle(handle -> handle.createUpdate("DELETE FROM GameCopy WHERE GameCopyID = :GameCopyID")
                .bind("GameCopyID", gamecopy.getGamecopyID())
                .execute());
    }


    public int getId(GameCopy gamecopy) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT id FROM GameCopy WHERE GameCopyID = :GameCopyID")
                .bind("GameCopyID", gamecopy.getGamecopyID())
                .mapTo(Integer.class).list().get(0));
    }

    public int getCountByGameID(int gameID) {
        String sql = "SELECT COUNT(*) FROM GameCopy " +
                "JOIN GamePlatform ON GameCopy.gameplatformID = GamePlatform.gameplatformID " +
                "WHERE GamePlatform.gameID = :gameID";
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("gameID", gameID)
                        .mapTo(Integer.class)
                        .one());
    }

    public List<GameCopy> getGameCopiesByGameID(int gameID) {
        String sql = "SELECT GameCopy.*, GamePlatform.gameplatformID AS gpID " +
                "FROM GameCopy " +
                "JOIN GamePlatform ON GameCopy.gameplatformID = GamePlatform.gameplatformID " +
                "WHERE GamePlatform.gameID = :gameID";

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("gameID", gameID)
                        .mapToBean(GameCopy.class)
                        .list());
    }

    public List<GameCopy> getGameCopyByMuseumId(int museumID) {
        String sql = "SELECT * FROM GameCopy " +
                "WHERE GameCopy.museumID = :museumID";

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("museumID", museumID)
                        .mapToBean(GameCopy.class)
                        .list());
    }

    public List<GameCopy> getGameCopyByWarenhuisId(int warenhuisID) {
        String sql = "SELECT * FROM GameCopy " +
                "WHERE GameCopy.warenhuisID = :warenhuisID";

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("warenhuisID", warenhuisID)
                        .mapToBean(GameCopy.class)
                        .list());
    }

    public List<GameCopy> getGameCopiesByPlatformID(int platformID) {
        String sql = "SELECT GameCopy.*, GamePlatform.gameplatformID AS gpID " +
                "FROM GameCopy " +
                "JOIN GamePlatform ON GameCopy.gameplatformID = GamePlatform.gameplatformID " +
                "WHERE GamePlatform.platformID = :platformID";

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("platformID", platformID)
                        .mapToBean(GameCopy.class)
                        .list());
    }

    public int getCountByPlatformID(int id) {
        String sql = "SELECT COUNT(*) FROM GameCopy " +
                "JOIN GamePlatform ON GameCopy.gameplatformID = GamePlatform.gameplatformID " +
                "WHERE GamePlatform.platformID = :platformID";
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("platformID", id)
                        .mapTo(Integer.class)
                        .one());
    }
}
