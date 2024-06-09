package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * GeneralDB class is used to manage database connection and to create statements.
 */
public class GeneralDB {

    /**
     * Establishes a connection to the database and creates a Statement object for sending SQL statements.
     *
     * @return Statement object for interacting with the database.
     * @throws ClassNotFoundException if the database driver class is not found.
     * @throws SQLException           if a database access error occurs or the url is null.
     */
    public Connection setConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost:5432/your_database_name";
        String user = "username";
        String password = "password";
        return DriverManager.getConnection(url, user, password);
    }
}
