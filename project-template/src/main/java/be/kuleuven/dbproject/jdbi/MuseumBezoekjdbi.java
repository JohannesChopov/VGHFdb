package be.kuleuven.dbproject.jdbi;
import be.kuleuven.dbproject.jdbi.JDBIManager;
import be.kuleuven.dbproject.model.Bezoeker;
import be.kuleuven.dbproject.model.Museum;
import be.kuleuven.dbproject.model.MuseumBezoek;

import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class MuseumBezoekjdbi {
    private final Jdbi jdbi;

    public MuseumBezoekjdbi() {
        this.jdbi = JDBIManager.getJdbi();
    }

    public List<MuseumBezoek> getAll() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM MuseumBezoek")
                .mapToBean(MuseumBezoek.class)
                .list());
    }

    public void insert(MuseumBezoek museumBezoek) {
        jdbi.useHandle(handle -> handle.createUpdate("INSERT INTO MuseumBezoek (museumID, bezoekerID, tijdsstip) VALUES (:museumID, :bezoekerID, :tijdsstip)")
                .bindBean(museumBezoek)
                .execute());
    }

    public void update(MuseumBezoek museumBezoekNieuw, MuseumBezoek museumBezoekOud) {
        jdbi.useHandle(handle -> handle.createUpdate("UPDATE MuseumBezoek SET (museumID, bezoekerID, tijdsstip) = (:museumID, :bezoekerID, :tijdsstip) WHERE museumbezoekID = :museumbezoekIDOud")
                .bindBean(museumBezoekNieuw)
                .bind("museumbezoekIDOud", museumBezoekOud.getMuseumbezoekID())
                .execute());
    }

    public void delete(MuseumBezoek museumBezoek) {
        jdbi.useHandle(handle -> handle.createUpdate("DELETE FROM MuseumBezoek WHERE museumbezoekID = :museumbezoekID")
                .bind("museumbezoekID", museumBezoek.getMuseumbezoekID())
                .execute());
    }

    public int getId(MuseumBezoek museumBezoek) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT museumbezoekID FROM MuseumBezoek WHERE museumbezoekID = :museumbezoekID")
                .bind("museumbezoekID", museumBezoek.getMuseumbezoekID())
                .mapTo(Integer.class)
                .list()
                .get(0));
    }

    public Bezoeker getBezoekerById(int bezoekerID) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM Bezoeker WHERE bezoekerID = :bezoekerID")
                .bind("bezoekerID", bezoekerID)
                .mapToBean(Bezoeker.class)
                .findFirst()
                .orElse(null));
    }

    public Museum getMuseumById(int museumID) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM Museum WHERE museumID = :museumID")
                .bind("museumID", museumID)
                .mapToBean(Museum.class)
                .findFirst()
                .orElse(null));
    }
}