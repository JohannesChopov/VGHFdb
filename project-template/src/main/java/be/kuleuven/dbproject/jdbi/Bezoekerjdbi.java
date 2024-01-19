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
        jdbi.useHandle(handle -> handle.createUpdate("INSERT INTO Bezoeker (naam) VALUES (:naam)").bindBean(bezoeker).execute());
    }

    public void update(Bezoeker bezoekerNieuw, Bezoeker bezoekerOud) {
        jdbi.useHandle(handle -> handle.createUpdate("UPDATE Bezoeker SET (museumID, naam) = (:museumID, :naam) WHERE bezoekerID = :bezoekerIDOud").bindBean(bezoekerNieuw).bind("bezoekerIDOud", bezoekerOud.getBezoekerID()).execute());
    }

    @Override
    public void delete(Bezoeker bezoeker) {
        jdbi.useTransaction(handle -> {
            handle.createUpdate("DELETE FROM MuseumBezoek WHERE bezoekerID = :bezoekerID")
                    .bind("bezoekerID", bezoeker.getBezoekerID())
                    .execute();
            handle.createUpdate("DELETE FROM Bezoeker WHERE bezoekerID = :bezoekerID")
                    .bind("bezoekerID", bezoeker.getBezoekerID())
                    .execute();
        });
    }

    public Bezoeker selectByname(String name) {
        List<Bezoeker> bezoekers = jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM Bezoeker WHERE naam = :naam")
                .bind("naam", name)
                .mapToBean(Bezoeker.class)
                .list());

        return bezoekers.isEmpty() ? null : bezoekers.get(0);
    }

    public int getId(Bezoeker bezoeker) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT bezoekerID FROM Bezoeker WHERE naam = :naam").bind("naam", bezoeker.getNaam()).mapTo(Integer.class).list().get(0));
    }
}
