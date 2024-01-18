package be.kuleuven.dbproject.jdbi;

import be.kuleuven.dbproject.model.Donatie;
import be.kuleuven.dbproject.model.User;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class Userjdbi {

    private final Jdbi jdbi;

    public Userjdbi() {
        this.jdbi = JDBIManager.getJdbi();
    }

    public List<Donatie> getAll() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM Donatie").mapToBean(Donatie.class).list());
    }
    public void insert(Donatie donatie) {
        jdbi.useHandle(handle -> handle.createUpdate("INSERT INTO Donatie (museumID, som, datum) VALUES (:museumID, :som, :datum)").bindBean(donatie).execute());
    }
    public void update(Donatie donatieNieuw, Donatie donatieOud) {
        jdbi.useHandle(handle -> handle.createUpdate("UPDATE Donatie SET (museumID, som, datum) = (:museumID, :som, :datum) WHERE donatieID = :donatieIDOud")
                .bindBean(donatieNieuw)
                .bind("donatieIDOud", donatieOud.getDonatieID())
                .execute());
    }
    public void delete(Donatie donatie) {
        jdbi.useHandle(handle -> handle.createUpdate("DELETE FROM Donatie WHERE donatieID = :donatieID")
                .bind("donatieID", donatie.getDonatieID())
                .execute());
    }
}
