package be.kuleuven.dbproject.jdbi;


import be.kuleuven.dbproject.model.Bezoeker;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class Bezoekerjdbi {
    private final Jdbi jdbi;

    public Bezoekerjdbi() {
        this.jdbi = JDBIManager.getJdbi();
    }

    public List<Bezoeker> getAll() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM Bezoeker").mapToBean(Bezoeker.class).list());
    }

    public void insert(Bezoeker bezoeker) {
        jdbi.useHandle(handle -> handle.createUpdate("INSERT INTO Bezoeker (bezoekerID, museumID, naam) VALUES (:bezoekerID, :museumID, :naam)").bindBean(bezoeker).execute());
    }

    public void update(Bezoeker bezoekerNieuw, Bezoeker bezoekerOud) {
        jdbi.useHandle(handle -> handle.createUpdate("UPDATE Bezoeker SET (bezoekerID,museumID, naam) = (:bezoekerID, :museumID, :naam) WHERE bezoekerID = :bezoekerIDOud").bindBean(bezoekerNieuw).bind("bezoekerIDOud", bezoekerOud.getBezoekerID()).execute());
    }

    public void delete(Bezoeker bezoeker) {
        jdbi.useHandle(handle -> handle.createUpdate("DELETE FROM Bezoker WHERE bezoekerID = :bezoekerID").bind("bezoekerID", bezoeker.getBezoekerID()).execute());
    }

    public Bezoeker selectByname(String naam) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM Bezoeker WHERE naam = :naam").bind("naam", naam).mapToBean(Bezoeker.class).list().get(0));
    }

    public int getId(Bezoeker bezoeker) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT id FROM Bezoeker WHERE naam = :naam").bind("naam", bezoeker.getNaam()).mapTo(Integer.class).list().get(0));
    }
    /*
    Voor als we met login willen werken
    public List<Bezoeker> getBezoekerLogin(String eMail, String wachtwoord) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM Bezoeker WHERE eMail = :eMail AND wachtwoord = :password").bind("eMail", eMail).bind("password", wachtwoord).mapToBean(Loper.class).list());
    }
     */
}
