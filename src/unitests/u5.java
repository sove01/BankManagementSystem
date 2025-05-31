package unitests;

import databaseCON.DatabaseConnection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the DatabaseConnection class, focusing on connection
 * establishment and closing functionality.
 */
public class u5 {

    /**
     * Tests that a database connection can be successfully established.
     */
    @Test
    @DisplayName("Test database connection establishment")
    void testGetConnection() {
        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection();
            assertNotNull(connection, "Connection object should not be null.");
            assertFalse(connection.isClosed(), "Connection should be open.");
        } catch (SQLException e) {
            fail("SQLException occurred during connection establishment: " + e.getMessage());
        } finally {
            // Ensure connection is closed after the test
            DatabaseConnection.closeConn();
        }
    }

    /**
     * Tests that the database connection can be successfully closed.
     */
    @Test
    @DisplayName("Test database connection closing")
    void testCloseConnection() {
        Connection connection = null;
        try {
            // First, establish a connection
            connection = DatabaseConnection.getConnection();
            assertNotNull(connection, "Pre-condition failed: Connection could not be established.");
            assertFalse(connection.isClosed(), "Pre-condition failed: Connection is closed prematurely.");

            // Then, close it
            DatabaseConnection.closeConn();
            assertTrue(connection.isClosed(), "Connection should be closed after calling closeConn().");
        } catch (SQLException e) {
            fail("SQLException occurred during connection test: " + e.getMessage());
        }
        // Calling closeConn again on a null/already closed connection should not throw an error
        assertDoesNotThrow(() -> DatabaseConnection.closeConn(),
                "Calling closeConn on an already closed/null connection should not throw an exception.");
    }

    /**
     * Tests that calling getConnection multiple times returns the same open connection (singleton behavior).
     */
    @Test
    @DisplayName("Test getConnection returns singleton instance")
    void testGetConnectionSingleton() {
        Connection connection1 = null;
        Connection connection2 = null;
        try {
            connection1 = DatabaseConnection.getConnection();
            connection2 = DatabaseConnection.getConnection(); // Get it again
            assertNotNull(connection1);
            assertNotNull(connection2);
            assertSame(connection1, connection2, "Subsequent calls to getConnection should return the same instance.");
            assertFalse(connection1.isClosed(), "Connection should remain open.");
        } catch (SQLException e) {
            fail("SQLException occurred during singleton test: " + e.getMessage());
        } finally {
            DatabaseConnection.closeConn();
        }
    }
}