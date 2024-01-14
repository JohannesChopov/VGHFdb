package be.kuleuven.dbproject.jdbi;


import be.kuleuven.dbproject.model.GameCopy;
import be.kuleuven.dbproject.model.Platform;
import org.jdbi.v3.core.Jdbi;

import java.util.List;
import java.util.Optional;

public class Platformjdbi {
    private final Jdbi jdbi;

    public Platformjdbi() {
        this.jdbi = JDBIManager.getJdbi();
    }

    public List<Platform> getAll() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM Platform").mapToBean(Platform.class).list());
    }

    public void insert(Platform platform) {
        jdbi.useHandle(handle -> handle.createUpdate("INSERT INTO Platform (platformID, naam) VALUES (:platformID, :naam)")
                .bindBean(platform)
                .execute());
    }

    public void update(Platform platformNieuw, Platform platformOud) {
        jdbi.useHandle(handle -> handle.createUpdate("UPDATE Platform SET (platformID, naam) = (:platformID, :naam) WHERE platformID = :platformIDOud")
                .bindBean(platformNieuw)
                .bind("platformIDOud", platformOud.getPlatformID())
                .execute());
    }

    public void delete(Platform platform) {
        jdbi.useHandle(handle -> handle.createUpdate("DELETE FROM Platform WHERE platformID = :platformID")
                .bind("platformID", platform.getPlatformID())
                .execute());
    }

    public int getId(Platform platform) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT id FROM Platform WHERE platformID = :platformID")
                .bind("platformID", platform.getPlatformID())
                .mapTo(Integer.class).list().get(0));
    }

    public String getNameById(int id) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT naam FROM Platform WHERE platformID = :platformID")
                .bind("platformID", id)
                .mapTo(String.class)
                .one());
    }


    public List<String> getAllPlatformNames() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT naam FROM Platform").mapTo(String.class).list());
    }

    public int getIdByName(String platformName) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT platformID FROM Platform WHERE naam = :platformName")
                .bind("platformName", platformName)
                .mapTo(Integer.class)
                .one());
    }
}


