package database;

import java.sql.Connection;
import java.sql.Statement;

public class DataBaseInitializer {

    public static void initialize() {
        String sql = """
            CREATE TABLE IF NOT EXISTS registro (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                classification TEXT NOT NULL,
                value REAL NOT NULL,
                entryDate DATE NOT NULL,
                registrationDate DATE NOT NULL
            );
        """;

        try (Connection connection = ConnectionManager.getConnection();
             Statement stmt = connection.createStatement()) {
             
            stmt.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}