package be.kuleuven.dbproject.jdbi;


import be.kuleuven.dbproject.model.Donatie;
import be.kuleuven.dbproject.model.MuseumBezoek;
import org.jdbi.v3.core.Jdbi;

import java.util.List;
public class Donatiejdbi implements Interfacejdbi<Donatie> {
    private final Jdbi jdbi;

    public Donatiejdbi() {
        this.jdbi = JDBIManager.getJdbi();
    }

    public List<Donatie> getAll() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM Donatie").mapToBean(Donatie.class).list());
    }
    @Override
    public void insert(Donatie donatie) {
        jdbi.useHandle(handle -> handle.createUpdate("INSERT INTO Donatie (museumID, som, datum) VALUES (:museumID, :som, :datum)").bindBean(donatie).execute());
    }

    public void update(Donatie donatieNieuw, Donatie donatieOud) {
        jdbi.useHandle(handle -> handle.createUpdate("UPDATE Donatie SET (museumID, som, datum) = (:museumID, :som, :datum) WHERE donatieID = :donatieIDOud")
                .bindBean(donatieNieuw)
                .bind("donatieIDOud", donatieOud.getDonatieID())
                .execute());
    }

    @Override
    public void delete(Donatie donatie) {
        jdbi.useHandle(handle -> handle.createUpdate("DELETE FROM Donatie WHERE donatieID = :donatieID")
                .bind("donatieID", donatie.getDonatieID())
                .execute());
    }

    public Donatie selectBySom(double som) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM Donatie WHERE som = :som")
                .bind("som", som)
                .mapToBean(Donatie.class).list().get(0));
    }

    public int getId(Donatie donatie) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT id FROM Donatie WHERE donatieID = :donatieID")
                .bind("donatieID", donatie.getDonatieID())
                .mapTo(Integer.class).list().get(0));
    }

    public List<Donatie> getDonatieByMuseumId(int museumID) {
        String sql = "SELECT * FROM Donatie " +
                "WHERE Donatie.museumID = :museumID";

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("museumID", museumID)
                        .mapToBean(Donatie.class)
                        .list());
    }

}
