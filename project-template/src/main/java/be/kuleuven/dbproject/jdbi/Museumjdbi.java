package be.kuleuven.dbproject.jdbi;


import be.kuleuven.dbproject.model.Museum;
import be.kuleuven.dbproject.model.GameCopy;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class Museumjdbi {
    private final Jdbi jdbi;

    public Museumjdbi() {
        this.jdbi = JDBIManager.getJdbi();
    }

    public List<Museum> getAll() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM Museum").
                mapToBean(Museum.class).list());
    }

    public void insert(Museum museum) {
        jdbi.useHandle(handle -> handle.createUpdate("INSERT INTO Museum (museumID, naam, inkomprijs, adres) VALUES (:museumID, :naam, :inkomprijs, :adres)").bindBean(museum).execute());
    }

    public void update(Museum museumNieuw, Museum museumOud) {
        jdbi.useHandle(handle -> handle.createUpdate("UPDATE Museum SET (museumID, naam, inkomprijs, adres) = (:museumID, :naam, :inkomprijs, :adres) WHERE museumID = :museumIDOud")
                .bindBean(museumNieuw)
                .bind("museumIDOud", museumOud.getMuseumID())
                .execute());
    }

    public void delete(Museum museum) {
        jdbi.useHandle(handle -> handle.createUpdate("DELETE FROM Museum WHERE MuseumID = :museumID")
                .bind("museumID",
                        museum.getMuseumID()).execute());
    }

    public int getId(Museum museum) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT id FROM Museum WHERE museumID = :museumID")
                .bind("museumID", museum.getMuseumID()).
                mapTo(Integer.class).list().get(0));
    }

    public String getMuseumNameById(int museumId) {
        try {
            return jdbi.withHandle(handle -> handle.createQuery("SELECT naam FROM Museum WHERE museumID = :museumID")
                    .bind("museumID", museumId)
                    .mapTo(String.class)
                    .one());
        } catch (IllegalStateException e) {
            throw new RuntimeException("pipi kaka", e);
        }
    }

}
