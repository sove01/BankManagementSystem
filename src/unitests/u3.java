package unitests;

import databaseCON.UserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * Unit tests for the UserDAO's balance update functionality.
 * Focuses on verifying correct balance updates and handling for non-existent users.
 * This test uses a dummy PIN that should be pre-populated or handled in a setup.
 */
public class u3 {

    private UserDAO userDAO;
    private static final String TEST_PIN = "5678"; // Use a specific PIN for testing

    /**
     * Sets up the test environment before each test method.
     * Initializes a new UserDAO.
     * IMPORTANT: For a real test, you'd use a temporary database and
     * insert a user with TEST_PIN and a known initial balance here.
     * For simplicity, this assumes TEST_PIN exists in your userDB.db.
     */
    @BeforeEach
    void setUp() {
        userDAO = new UserDAO();
        // Ideally:
        // 1. Point UserDAO to a test database.
        // 2. Ensure a user with TEST_PIN exists and has a starting balance (e.g., 1000.0)
        //    DELETE FROM users WHERE pin = '5678';
        //    INSERT INTO users (pin, balance, ...) VALUES ('5678', 1000.0, ...);
    }

        /**
         * Tests a successful balance update for an existing user.
     */
    @Test
    @DisplayName("Test successful balance update for existing user")
    void testUpdateBalanceSuccess() {
        // Assume initial balance for TEST_PIN is 1000.0 (or whatever your setup ensures)
        double initialBalance = userDAO.getBalance(TEST_PIN);
        assumeTrue(initialBalance != -1.0, "Pre-condition failed: TEST_PIN user not found or balance error.");

        double newBalance = initialBalance + 500.0;
        assertTrue(userDAO.updateBalance(TEST_PIN, newBalance),
                "Balance update should be successful.");

        // Verify the balance was indeed updated
        assertEquals(newBalance, userDAO.getBalance(TEST_PIN), 0.001,
                "New balance should match the updated value.");
    }

    /**
     * Tests updating the balance for a non-existent user.
     * Expects the update to fail.
     */
    @Test
    @DisplayName("Test balance update for non-existent user")
    void testUpdateBalanceNonExistentUser() {
        assertFalse(userDAO.updateBalance("9999", 500.0),
                "Balance update for a non-existent user should fail.");
    }

    /**
     * Tests updating the balance with a null PIN.
     * Expects the update to fail.
     */
    @Test
    @DisplayName("Test balance update with null PIN")
    void testUpdateBalanceNullPin() {
        assertFalse(userDAO.updateBalance(null, 500.0),
                "Balance update with a null PIN should fail.");
    }
}