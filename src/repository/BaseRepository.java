package repository;

import java.sql.Connection;
import java.sql.SQLException;
import util.DatabaseConnection;

public abstract class BaseRepository {

    protected Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }
}
