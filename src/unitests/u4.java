package unitests;

import databaseCON.UserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * Unit tests for the UserDAO's debit transaction (withdrawal) functionality.
 * This class tests scenarios like successful withdrawal, insufficient funds,
 * and transactions for non-existent users.
 */
public class u4 {

    private UserDAO userDAO;
    private static final String TEST_PIN = "5678"; // Use a specific PIN for testing

    /**
     * Sets up the test environment before each test method.
     * Initializes a new UserDAO.
     * IMPORTANT: For robust testing, ensure a user with TEST_PIN exists
     * and has a known initial balance (e.g., 1000.0) in your test database before each test.
     */
    @BeforeEach
    void setUp() {
        userDAO = new UserDAO();
        // Ideally, in a real test setup:
        // 1. Point UserDAO to a test database.
        // 2. Reset the balance for TEST_PIN to a known value (e.g., 1000.0)
        //    before each test to ensure consistent initial state.
        //    For example: userDAO.updateBalance(TEST_PIN, 1000.0);
    }

    /**
     * Tests a successful debit transaction (withdrawal).
     */
    @Test
    @DisplayName("Test successful debit transaction")
    void testSuccessfulDebitTransaction() {
        double initialBalance = userDAO.getBalance(TEST_PIN);
        assumeTrue(initialBalance != -1.0, "Pre-condition failed: TEST_PIN user not found or balance error.");
        assumeTrue(initialBalance >= 500.0, "Pre-condition failed: Insufficient initial funds for test.");

        double withdrawalAmount = 500.0;
        assertTrue(userDAO.performDebitTransaction(TEST_PIN, withdrawalAmount, "Test Withdrawal"),
                "Debit transaction should be successful.");

        // Verify the new balance
        assertEquals(initialBalance - withdrawalAmount, userDAO.getBalance(TEST_PIN), 0.001,
                "New balance should reflect the successful withdrawal.");
    }

    /**
     * Tests a debit transaction where the user has insufficient funds.
     */
    @Test
    @DisplayName("Test debit transaction with insufficient funds")
    void testDebitTransactionInsufficientFunds() {
        double initialBalance = userDAO.getBalance(TEST_PIN);
        assumeTrue(initialBalance != -1.0, "Pre-condition failed: TEST_PIN user not found or balance error.");
        assumeTrue(initialBalance < 100000.0, "Pre-condition failed: Initial balance is too high for this test.");


        double withdrawalAmount = 100000.0; // Amount larger than typical balance
        assertFalse(userDAO.performDebitTransaction(TEST_PIN, withdrawalAmount, "Test Insufficient Funds"),
                "Debit transaction with insufficient funds should fail.");

        // Verify balance remains unchanged
        assertEquals(initialBalance, userDAO.getBalance(TEST_PIN), 0.001,
                "Balance should remain unchanged after failed insufficient funds transaction.");
    }

    /**
     * Tests a debit transaction for a non-existent user.
     */
    @Test
    @DisplayName("Test debit transaction for non-existent user")
    void testDebitTransactionNonExistentUser() {
        assertFalse(userDAO.performDebitTransaction("9999", 100.0, "Test Non-Existent User"),
                "Debit transaction for a non-existent user should fail.");
    }

    /**
     * Tests a debit transaction with a negative amount.
     * Assuming performDebitTransaction should handle or prevent negative amounts.
     */
    @Test
    @DisplayName("Test debit transaction with negative amount")
    void testDebitTransactionNegativeAmount() {
        double initialBalance = userDAO.getBalance(TEST_PIN);
        assumeTrue(initialBalance != -1.0, "Pre-condition failed: TEST_PIN user not found or balance error.");

        assertFalse(userDAO.performDebitTransaction(TEST_PIN, -50.0, "Test Negative Amount"),
                "Debit transaction with a negative amount should fail.");

        // Verify balance remains unchanged
        assertEquals(initialBalance, userDAO.getBalance(TEST_PIN), 0.001,
                "Balance should remain unchanged after failed negative amount transaction.");
    }
}