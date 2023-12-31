package be.kuleuven.dbproject.jdbi;


import be.kuleuven.dbproject.model.Donatie;
import org.jdbi.v3.core.Jdbi;

import java.util.List;
public class Donatiejdbi {
    private final Jdbi jdbi;

    public Donatiejdbi() {
        this.jdbi = JDBIManager.getJdbi();
    }

    public List<Donatie> getAll() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM Donatie").mapToBean(Donatie.class).list());
    }

    public void insert(Donatie donatie) {
        jdbi.useHandle(handle -> handle.createUpdate("INSERT INTO Donatie (donatieID, museumID, som, datum) VALUES (:donatieID, :museumID, :som, :datum)").bindBean(donatie).execute());
    }

    public void update(Donatie donatieNieuw, Donatie donatieOud) {
        jdbi.useHandle(handle -> handle.createUpdate("UPDATE Donatie SET (donatieID, museumID, som, datum) = (:donatieID, :museumID, :som, :datum) WHERE donatieID = :donatieIDOud")
                .bindBean(donatieNieuw)
                .bind("donatieIDOud", donatieOud.getDonatieID())
                .execute());
    }

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

}
