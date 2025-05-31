package unitests;

import databaseCON.UserDAO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the UserDAO's balance retrieval functionality.
 * Focuses on verifying correct balance fetching for existing and non-existent users.
 */
public class u2 {

    /**
     * Tests retrieving the balance for an existing user.
     * Assumes a user with PIN "1234" exists in the database.
     * This test will pass if the balance is retrieved, but you might want to assert
     * a specific expected balance if your test setup pre-populates it.
     */
    @Test
    @DisplayName("Test getting balance for existing user")
    void testGetBalanceExistingUser() {
        UserDAO userDAO = new UserDAO();
        // IMPORTANT: Ensure a user with this PIN exists and has a balance.
        // For a more robust test, you'd insert a user with a known balance in a @BeforeEach method.
        double balance = userDAO.getBalance("1234");
        assertNotEquals(-1.0, balance, "Balance should be retrieved successfully for an existing user.");
        assertTrue(balance >= 0, "Balance should be a non-negative value.");
    }

    /**
     * Tests retrieving the balance for a non-existent user.
     * Expects a return value indicating an error (e.g., -1.0).
     */
    @Test
    @DisplayName("Test getting balance for non-existent user")
    void testGetBalanceNonExistentUser() {
        UserDAO userDAO = new UserDAO();
        double balance = userDAO.getBalance("9999"); // A PIN that should not exist
        assertEquals(-1.0, balance, "Balance for a non-existent user should return -1.0.");
    }

    /**
     * Tests retrieving the balance with a null PIN.
     */
    @Test
    @DisplayName("Test getting balance with null PIN")
    void testGetBalanceNullPin() {
        UserDAO userDAO = new UserDAO();
        double balance = userDAO.getBalance(null);
        assertEquals(-1.0, balance, "Balance with a null PIN should return -1.0.");
    }
}