package be.kuleuven.dbproject.jdbi;

import org.jdbi.v3.core.Jdbi;

public class JDBIManager {
    private static Jdbi jdbi;

    private JDBIManager() {
        // private constructor to prevent instantiation
    }

    public static void init(String connectionString) {
        jdbi = Jdbi.create(connectionString);
    }

    public static Jdbi getJdbi() {
        if (jdbi == null) {
            init("jdbc:sqlite:ProjectLaurenceJohannes.db");
        }
        return jdbi;
    }
}