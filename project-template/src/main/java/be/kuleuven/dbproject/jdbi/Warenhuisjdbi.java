package be.kuleuven.dbproject.jdbi;


import be.kuleuven.dbproject.model.Locatie;
import be.kuleuven.dbproject.model.Museum;
import be.kuleuven.dbproject.model.Warenhuis;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class Warenhuisjdbi implements Interfacejdbi<Warenhuis>{
    private final Jdbi jdbi;

    public Warenhuisjdbi() {
        this.jdbi = JDBIManager.getJdbi();
    }

    public List<Warenhuis> getAll() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM Warenhuis")
                .mapToBean(Warenhuis.class).list());
    }
    @Override
    public void insert(Warenhuis warenhuis) {
        jdbi.useHandle(handle -> handle.createUpdate("INSERT INTO Warenhuis (naam, adres) VALUES (:naam, :adres)").bindBean(warenhuis).execute());
    }

    public void update(Warenhuis warenhuisNieuw, Warenhuis warenhuisOud) {
        jdbi.useHandle(handle -> handle.createUpdate("UPDATE Warenhuis SET (naam, adres) = (:naam, :adres) WHERE warenhuisID = :warenhuisIDOud")
                .bindBean(warenhuisNieuw)
                .bind("warenhuisIDOud", warenhuisOud.getID())
                .execute());
    }

    @Override
    public void delete(Warenhuis warenhuis) {
        jdbi.useHandle(handle -> {
            handle.createUpdate("DELETE FROM GameCopy WHERE warenhuisID = :warenhuisID")
                    .bind("warenhuisID", warenhuis.getID())
                    .execute();
            handle.createUpdate("DELETE FROM Warenhuis WHERE warenhuisID = :warenhuisID")
                    .bind("warenhuisID", warenhuis.getID())
                    .execute();
        });
    }

    public int getId(Warenhuis warenhuis) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT id FROM Warenhuis WHERE warenhuisID = :warenhuisID")
                .bind("warenhuisID", warenhuis.getID()).mapTo(Integer.class).list().get(0));
    }

    public String getNameById(int warenhuisId) {
        try {
            return jdbi.withHandle(handle -> handle.createQuery("SELECT naam FROM Warenhuis WHERE warenhuisID = :warenhuisID")
                    .bind("warenhuisID", warenhuisId)
                    .mapTo(String.class)
                    .one());
        } catch (IllegalStateException e) {
            return null;
        }
    }

    public int getGameCopyCountByWarenhuis(Warenhuis warenhuis) {
        String sql = "SELECT COUNT(*) FROM GameCopy WHERE warenhuisID = :warenhuisID";
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("warenhuisID", warenhuis.getID())
                        .mapTo(Integer.class)
                        .one());
    }

    public String getAdresById(int warenhuisId) {
        try {
            return jdbi.withHandle(handle -> handle.createQuery("SELECT adres FROM Warenhuis WHERE warenhuisID = :warenhuisID")
                    .bind("warenhuisID", warenhuisId)
                    .mapTo(String.class)
                    .one());
        } catch (IllegalStateException e) {
            return null;
        }
    }

    public Warenhuis getWarenhuisById(int warenhuisID) {
        try {
            return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM Warenhuis WHERE warenhuisID = :warenhuisID")
                    .bind("warenhuisID", warenhuisID)
                    .mapToBean(Warenhuis.class)
                    .one());
        } catch (IllegalStateException e) {
            return null;
        }
    }

    public Warenhuis getWarenhuisByName(String naam) {
        try {
            return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM Warenhuis WHERE naam = :naam")
                    .bind("naam", naam)
                    .mapToBean(Warenhuis.class)
                    .one());
        } catch (IllegalStateException e) {
            return null;
        }
    }
}
