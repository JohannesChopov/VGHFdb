package be.kuleuven.dbproject.jdbi;


import be.kuleuven.dbproject.model.GameCopy;
import be.kuleuven.dbproject.model.Warenhuis;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class Warenhuisjdbi {
    private final Jdbi jdbi;

    public Warenhuisjdbi() {
        this.jdbi = JDBIManager.getJdbi();
    }

    public List<Warenhuis> getAll() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM Warenhuis").mapToBean(Warenhuis.class).list());
    }

    public void insert(Warenhuis Warenhuis) {
        jdbi.useHandle(handle -> handle.createUpdate("INSERT INTO Warenhuis (warenhuisID, naam, adres) VALUES (:warenhuisID, :naam, :adres)")
                .bindBean(Warenhuis)
                .execute());
    }

    public void update(Warenhuis warenhuisNieuw, Warenhuis warenhuisOud) {
        jdbi.useHandle(handle -> handle.createUpdate("UPDATE Warenhuis SET (warenhuisID, naam, adres) = (:warenhuisID, :naam, :adres) WHERE warenhuisID = :warenhuisIDOud")
                .bindBean(warenhuisNieuw)
                .bind("warenhuisIDOud", warenhuisOud.getWarenhuisID())
                .execute());
    }

    public void delete(Warenhuis warenhuis) {
        jdbi.useHandle(handle -> handle.createUpdate("DELETE FROM Warenhuis WHERE warenhuisID = :warenhuisID")
                .bind("warenhuisID", warenhuis.getWarenhuisID())
                .execute());
    }

    public int getId(Warenhuis warenhuis) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT id FROM Warenhuis WHERE warenhuisID = :warenhuisID")
                .bind("warenhuisID", warenhuis.getWarenhuisID()).mapTo(Integer.class).list().get(0));
    }

    public String getWarenhuisNameById(int warenhuisId) {
        try {
            return jdbi.withHandle(handle -> handle.createQuery("SELECT naam FROM Warenhuis WHERE warenhuisID = :warenhuisID")
                    .bind("warenhuisID", warenhuisId)
                    .mapTo(String.class)
                    .one());
        } catch (IllegalStateException e) {
            throw new RuntimeException("kaka en kaka", e);
        }
    }
}
