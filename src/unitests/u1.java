package unitests;

import databaseCON.UserDAO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the UserDAO's login validation functionality.
 * Focuses on verifying correct and incorrect login attempts.
 */
public class u1 {

    /**
     * Tests a successful user login with valid credentials.
     * Assumes a user with card number "1234567890123456" and PIN "1234" exists in the database.
     */
    @Test
    @DisplayName("Test valid user login")
    void testValidLogin() {
        UserDAO userDAO = new UserDAO();
        // IMPORTANT: Ensure this card number and PIN exist in your src/userDB.db for this test to pass.
        // Or better yet, use a dedicated test database and pre-populate it.
        assertTrue(userDAO.validateLogin("1234567890123456", "1234"),
                "Login with valid credentials should be successful.");
    }

    /**
     * Tests an unsuccessful user login with an incorrect PIN.
     */
    @Test
    @DisplayName("Test invalid user login - incorrect PIN")
    void testInvalidLoginIncorrectPin() {
        UserDAO userDAO = new UserDAO();
        assertFalse(userDAO.validateLogin("1234567890123456", "9999"),
                "Login with incorrect PIN should fail.");
    }

    /**
     * Tests an unsuccessful user login with a non-existent card number.
     */
    @Test
    @DisplayName("Test invalid user login - non-existent card number")
    void testInvalidLoginNonExistentCard() {
        UserDAO userDAO = new UserDAO();
        assertFalse(userDAO.validateLogin("0000000000000000", "1234"),
                "Login with non-existent card number should fail.");
    }

    /**
     * Tests an unsuccessful user login with empty credentials.
     */
    @Test
    @DisplayName("Test invalid user login - empty credentials")
    void testInvalidLoginEmptyCredentials() {
        UserDAO userDAO = new UserDAO();
        assertFalse(userDAO.validateLogin("", ""),
                "Login with empty credentials should fail.");
    }
}