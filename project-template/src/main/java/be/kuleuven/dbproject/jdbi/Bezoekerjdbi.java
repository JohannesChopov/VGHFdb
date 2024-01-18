package be.kuleuven.dbproject.jdbi;


import be.kuleuven.dbproject.model.Bezoeker;
import be.kuleuven.dbproject.model.GameCopy;
import be.kuleuven.dbproject.model.Museum;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class Bezoekerjdbi implements Interfacejdbi<Bezoeker>{
    private final Jdbi jdbi;

    public Bezoekerjdbi() {
        this.jdbi = JDBIManager.getJdbi();
    }

    public List<Bezoeker> getAll() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM Bezoeker").mapToBean(Bezoeker.class).list());
    }
    @Override
    public void insert(Bezoeker bezoeker) {
        jdbi.useHandle(handle -> handle.createUpdate("INSERT INTO Bezoeker (museumID, naam) VALUES (:museumID, :naam)").bindBean(bezoeker).execute());
    }

    public void update(Bezoeker bezoekerNieuw, Bezoeker bezoekerOud) {
        jdbi.useHandle(handle -> handle.createUpdate("UPDATE Bezoeker SET (museumID, naam) = (:museumID, :naam) WHERE bezoekerID = :bezoekerIDOud").bindBean(bezoekerNieuw).bind("bezoekerIDOud", bezoekerOud.getBezoekerID()).execute());
    }

    @Override
    public void delete(Bezoeker bezoeker) {
        jdbi.useHandle(handle -> handle.createUpdate("DELETE FROM Bezoeker WHERE bezoekerID = :bezoekerID").bind("bezoekerID", bezoeker.getBezoekerID()).execute());
    }

    public Bezoeker selectByname(String name) {
        List<Bezoeker> bezoekers = jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM Bezoeker WHERE naam = :naam")
                .bind("naam", name)
                .mapToBean(Bezoeker.class)
                .list());

        return bezoekers.isEmpty() ? null : bezoekers.get(0);
    }


    public int getTotalAantalForMuseum(int museumID) {
        String sql = "SELECT SUM(aantal) FROM Bezoeker WHERE museumID = :museumID";
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("museumID", museumID)
                        .mapTo(Integer.class)
                        .one());
    }

    public List<Bezoeker> getBezoekerByMuseumId(int museumID) {
        String sql = "SELECT * FROM Bezoeker " +
                "WHERE Bezoeker.museumID = :museumID";

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("museumID", museumID)
                        .mapToBean(Bezoeker.class)
                        .list());
    }


    public int getId(Bezoeker bezoeker) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT bezoekerID FROM Bezoeker WHERE naam = :naam").bind("naam", bezoeker.getNaam()).mapTo(Integer.class).list().get(0));
    }
    /*
    Voor als we met login willen werken
    public List<Bezoeker> getBezoekerLogin(String eMail, String wachtwoord) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM Bezoeker WHERE eMail = :eMail AND wachtwoord = :password").bind("eMail", eMail).bind("password", wachtwoord).mapToBean(Loper.class).list());
    }
     */
}
