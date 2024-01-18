package be.kuleuven.dbproject.jdbi;
import be.kuleuven.dbproject.controller.AddItemController;
import be.kuleuven.dbproject.jdbi.JDBIManager;
import be.kuleuven.dbproject.model.Bezoeker;
import be.kuleuven.dbproject.model.Museum;
import be.kuleuven.dbproject.model.MuseumBezoek;

import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class MuseumBezoekjdbi implements Interfacejdbi<MuseumBezoek> {
    private final Jdbi jdbi;

    public MuseumBezoekjdbi() {
        this.jdbi = JDBIManager.getJdbi();
    }

    public List<MuseumBezoek> getAll() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM MuseumBezoek")
                .mapToBean(MuseumBezoek.class)
                .list());
    }
    @Override
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
    @Override
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

    public String getBezoekerNaamById(int bezoekerID) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT Naam FROM Bezoeker WHERE bezoekerID = :bezoekerID")
                .bind("bezoekerID", bezoekerID)
                .mapToBean(String.class)
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

    public String getMuseumNaamByMuseumID(int bezoekerID) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT Naam FROM Museum WHERE museumID IN (SELECT museumID FROM MuseumBezoek WHERE bezoekerID = :bezoekerID)")
                .bind("bezoekerID", bezoekerID)
                .mapTo(String.class)
                .findFirst()
                .orElse(null));
    }

    public int countVisitsByBezoeker(int bezoekerID) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT COUNT(*) FROM MuseumBezoek WHERE bezoekerID = :bezoekerID")
                .bind("bezoekerID", bezoekerID)
                .mapTo(Integer.class)
                .one());
    }

    public int countTotalVisits(int museumID) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT COUNT(*) FROM MuseumBezoek WHERE museumID = :museumID")
                .bind("museumID", museumID)
                .mapTo(Integer.class)
                .one());
    }

    public List<MuseumBezoek> getBezoekerByMuseumId(int museumID) {
        String sql = "SELECT * FROM MuseumBezoek " +
                "WHERE MuseumBezoek.museumID = :museumID";

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("museumID", museumID)
                        .mapToBean(MuseumBezoek.class)
                        .list());
    }


    public List<MuseumBezoek> getMuseumByBezoekerId(int bezoekerId) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM Museum WHERE museumID IN (SELECT museumID FROM MuseumBezoek WHERE bezoekerID = :bezoekerID)")
                .bind("bezoekerID", bezoekerId)
                .mapToBean(MuseumBezoek.class)
                .list());
    }
}
