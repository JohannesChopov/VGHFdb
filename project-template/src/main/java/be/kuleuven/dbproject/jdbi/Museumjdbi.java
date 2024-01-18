package be.kuleuven.dbproject.jdbi;


import be.kuleuven.dbproject.model.Locatie;
import be.kuleuven.dbproject.model.Museum;
import be.kuleuven.dbproject.model.GameCopy;
import be.kuleuven.dbproject.model.Warenhuis;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class Museumjdbi implements Interfacejdbi<Museum>{
    private final Jdbi jdbi;

    public Museumjdbi() {
        this.jdbi = JDBIManager.getJdbi();
    }

    public List<Museum> getAll() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM Museum").
                mapToBean(Museum.class).list());
    }
    @Override
    public void insert(Museum museum) {
        jdbi.useHandle(handle -> handle.createUpdate("INSERT INTO Museum (naam, inkomprijs, adres) VALUES (:naam, :inkomprijs, :adres)").bindBean(museum).execute());
    }

    public void update(Museum museumNieuw, Museum museumOud) {
        jdbi.useHandle(handle -> handle.createUpdate("UPDATE Museum SET (naam, inkomprijs, adres) = (:naam, :inkomprijs, :adres) WHERE museumID = :museumIDOud")
                .bindBean(museumNieuw)
                .bind("museumIDOud", museumOud.getID())
                .execute());
    }

    @Override
    public void delete(Museum museum) {
        jdbi.useHandle(handle -> {
            handle.createUpdate("DELETE FROM GameCopy WHERE museumID = :museumID")
                    .bind("museumID", museum.getID())
                    .execute();
            handle.createUpdate("DELETE FROM Donatie WHERE museumID = :museumID")
                    .bind("museumID", museum.getID())
                    .execute();
            handle.createUpdate("DELETE FROM MuseumBezoek WHERE MuseumID = :museumID")
                    .bind("museumID", museum.getID())
                    .execute();
            handle.createUpdate("DELETE FROM Museum WHERE MuseumID = :museumID")
                    .bind("museumID", museum.getID())
                    .execute();
        });
    }

    public int getId(Museum museum) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT id FROM Museum WHERE museumID = :museumID")
                .bind("museumID", museum.getID()).
                mapTo(Integer.class).list().get(0));
    }

    public String getNaam(Museum museum) {
        try {
            return jdbi.withHandle(handle -> handle.createQuery("SELECT naam FROM Museum WHERE museumID = :museumID")
                    .bind("museumID", museum.getID())
                    .mapTo(String.class)
                    .one());
        } catch (IllegalStateException e) {
            return null;
        }
    }

    public String getNameById(int museumId) {
        try {
            return jdbi.withHandle(handle -> handle.createQuery("SELECT naam FROM Museum WHERE museumID = :museumID")
                    .bind("museumID", museumId)
                    .mapTo(String.class)
                    .one());
        } catch (IllegalStateException e) {
            return null;
        }
    }

    public Museum getMuseumById(int museumId) {
        try {
            return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM Museum WHERE museumID = :museumID")
                    .bind("museumID", museumId)
                    .mapToBean(Museum.class)
                    .one());
        } catch (IllegalStateException e) {
            return null;
        }
    }

    public Museum getMuseumByBezoekerId(int bezoekerID) {
        try {
            return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM Museum WHERE museumID IN (SELECT museumID FROM MuseumBezoek WHERE bezoekerID = :bezoekerID)")
                    .bind("bezoekerID", bezoekerID)
                    .mapToBean(Museum.class)
                    .one());
        } catch (IllegalStateException e) {
            return null;
        }
    }

    public int getBezoekersCountByMuseumID(int museumID) {
        String sql = "SELECT COUNT(*) FROM Bezoeker WHERE museumID = :museumID";
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("museumID", museumID)
                        .mapTo(Integer.class)
                        .one());
    }

    public int getDonatiesCountByMuseumID(int museumID) {
        String sql = "SELECT COUNT(*) FROM Donatie WHERE museumID = :museumID";
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("museumID", museumID)
                        .mapTo(Integer.class)
                        .one());
    }

    public double getTotalDonatiesValueByMuseumID(int museumID) {
        String sql = "SELECT COALESCE(SUM(som), 0) FROM Donatie WHERE museumID = :museumID";
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("museumID", museumID)
                        .mapTo(Double.class)
                        .one());
    }

    public int getGameCopyCountByMuseum(Museum museum) {
        String sql = "SELECT COUNT(*) FROM GameCopy WHERE museumID = :museumID";
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("museumID", museum.getID())
                        .mapTo(Integer.class)
                        .one());
    }

    public String getAdresById(Integer museumId) {
        try {
            return jdbi.withHandle(handle -> handle.createQuery("SELECT adres FROM Museum WHERE museumID = :museumID")
                    .bind("museumID", museumId)
                    .mapTo(String.class)
                    .one());
        } catch (IllegalStateException e) {
            return null;
        }
    }
}
