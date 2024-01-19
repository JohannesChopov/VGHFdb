package be.kuleuven.dbproject.jdbi;

import be.kuleuven.dbproject.model.User;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class Userjdbi {

    private final Jdbi jdbi;

    public Userjdbi() {
        this.jdbi = JDBIManager.getJdbi();
    }

    public List<User> getAll() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM User").mapToBean(User.class).list());
    }

    public void insert(User user) {
        jdbi.useHandle(handle -> handle.createUpdate("INSERT INTO User (username, password) VALUES (:username, :password)")
                .bindBean(user)
                .execute());
    }

    public void update(User userNieuw, User userOud) {
        jdbi.useHandle(handle -> handle.createUpdate("UPDATE User SET username = :username, password = :password WHERE userID = :userIDOud")
                .bindBean(userNieuw)
                .bind("userIDOud", userOud.getUserID())
                .execute());
    }

    public void delete(User user) {
        jdbi.useHandle(handle -> handle.createUpdate("DELETE FROM User WHERE userID = :userID")
                .bind("userID", user.getUserID())
                .execute());
    }

    public User getUserByUsername(String username) {
        try {
            return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM User WHERE username = :username")
                    .bind("username", username)
                    .mapToBean(User.class)
                    .findFirst()
                    .orElse(null));
        } catch (Exception e) {
            // Handle exceptions (log or throw, depending on your requirements)
            e.printStackTrace();
            return null;
        }
    }

}

