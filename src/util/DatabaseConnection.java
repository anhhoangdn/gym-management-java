package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static DatabaseConnection instance;
    private Connection connection;

    private static final String URL = "jdbc:mysql://localhost:3306/gymmanagement";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    private DatabaseConnection() {
        // TODO: establish JDBC connection using URL, USER, PASSWORD
    }

    public static synchronized DatabaseConnection getInstance() {
        // TODO: return the existing instance or create a new one (thread-safe Singleton)
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        // TODO: return the active database connection
        return connection;
    }

    public void closeConnection() {
        // TODO: close the database connection if open
    }
}
