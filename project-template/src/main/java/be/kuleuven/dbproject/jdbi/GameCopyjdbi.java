package be.kuleuven.dbproject.jdbi;


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
        jdbi.useHandle(handle -> handle.createUpdate("UPDATE GameCopy SET gameplatformID = :gameplatformID, museumID = :museumID, warenhuisID = :warenhuisID) WHERE GameCopyID = :GameCopyIDOud")
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

    public Optional<String> getPlatformNameByGameCopyID(int gamecopyID) {
        String sql = "SELECT Platform.naam AS platformName " +
                "FROM GameCopy " +
                "JOIN GamePlatform ON GameCopy.gameplatformID = GamePlatform.gameplatformID " +
                "JOIN Platform ON GamePlatform.platformID = Platform.platformID " +
                "WHERE GameCopy.gamecopyID = :gamecopyID";
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("gamecopyID", gamecopyID)
                        .mapTo(String.class)
                        .findFirst());
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
}
