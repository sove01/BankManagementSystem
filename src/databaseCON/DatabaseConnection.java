package databaseCON;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

/**
 * The DatabaseConnection class manages the singleton database connection to an SQLite database.
 * It provides methods to establish and close the connection, ensuring only one connection
 * is active at a time.
 */
public class DatabaseConnection {
    private static Connection con; // The single static Connection instance

    /**
     * Private constructor to prevent direct instantiation, enforcing the singleton pattern.
     */
    private DatabaseConnection() {
        // Private constructor to prevent instantiation
    }

    /**
     * Retrieves the singleton database connection. If the connection does not exist
     * or is closed, it establishes a new connection to the SQLite database.
     *
     * @return A live {@link Connection} object to the database.
     * @throws SQLException If a database access error occurs or the URL is null.
     */
    public static Connection getConnection() throws SQLException {
        // Check if the connection is null or already closed
        if (con == null || con.isClosed()) {
            String dbURL = "jdbc:sqlite:src/userDB.db"; // Path
            con = DriverManager.getConnection(dbURL);
            System.out.println("Connected to existing sqlite database successfully");
        }
        return con;
    }

    /**
     * Closes the active database connection. If no connection is open, this method does nothing.
     * It sets the connection instance to null after closing to allow for a new connection
     * to be established if needed later.
     *
     * @throws RuntimeException If a SQLException occurs during the closing of the connection.
     */
    public static void closeConn() {
        if (con != null) {
            try {
                con.close();
                con = null; // Set to null to indicate no active connection
                System.out.println("Connection closed successfully");
            } catch (SQLException e) {
                System.out.println("Connection could not be closed");
                throw new RuntimeException(e);
            }
        }
    }
}