package databaseCON;

import javax.swing.*;
import javax.xml.crypto.Data; // This import seems unused, can be removed
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDAO {

    /**
     * Inserts a new user record into the 'users' table in the database.
     * This method expects a fully populated User object containing all
     * necessary signup information.
     *
     * @param user The User object containing all data for the new user.
     * @return true if the user was successfully inserted, false otherwise.
     */
    public boolean insertNewUserFromSignUp(User user) {
        // SQL query to insert all user details into the 'users' table.
        // NOTE: If 'cardNumber' is not in your 'users' table, you must remove it from here too.
        // Assuming your 'users' table has 'firstName, ..., pin, homeAddress, cardNumber'
        String query = "INSERT INTO users (" +
                "firstName, lastName, nationality, region, city, " +
                "phoneNumber, email, gender, maritalStatus, pin, homeAddress, cardNumber" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; // 11 placeholders for 11 fields

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            // Set each parameter in the PreparedStatement using data from the User object
            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getNationality());
            pstmt.setString(4, user.getRegion());
            pstmt.setString(5, user.getCity());
            pstmt.setString(6, user.getPhoneNumber());
            pstmt.setString(7, user.getEmail());
            pstmt.setString(8, user.getGender());
            pstmt.setString(9, user.getMaritalStatus());
            pstmt.setString(10, user.getPin());
            pstmt.setString(11, user.getHomeAddress());
            pstmt.setString(12, user.getCardNumber()); // Make sure 'cardNumber' exists in 'users' table if used here

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("SQL Error during user signup: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Validates user login credentials against the 'users' table.
     * IMPORTANT: This method assumes card_number and pin are stored in plain text.
     * For a secure application, PINs should be hashed and salted.
     *
     * @param cardNum The card number provided by the user.
     * @param PIN     The PIN provided by the user.
     * @return true if the credentials are valid, false otherwise.
     */
    public boolean validateLogin(String cardNum, String PIN) {
        // Ensure 'cardNumber' column exists in 'users' table or change to 'pin' if appropriate for login
        String query = "SELECT 1 FROM users WHERE cardNumber = ? AND pin = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, cardNum);
            pstmt.setString(2, PIN);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("SQL Error during login validation: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves the current balance for a given PIN.
     * @param pin The user's PIN.
     * @return The current balance, or -1.0 if an error occurs or PIN not found.
     */
    public double getBalance(String pin) {
        double balance = -1.0;
        // Query to get balance based on PIN
        String query = "SELECT balance FROM users WHERE pin = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, pin);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    balance = rs.getDouble("balance");
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Error in getBalance: " + e.getMessage());
            e.printStackTrace();
        }
        return balance;
    }

    /**
     * Updates the balance for a given PIN.
     * This method now uses the PIN to identify the user for balance update.
     *
     * @param pin The user's PIN.
     * @param newBalance The new balance to set.
     * @return true if the balance was successfully updated, false otherwise.
     */
    public boolean updateBalance(String pin, double newBalance) { // CHANGE: Parameter changed from cardNumber to pin
        String query = "UPDATE users SET balance = ? WHERE pin = ?"; // CHANGE: WHERE clause now uses pin
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setDouble(1, newBalance);
            pstmt.setString(2, pin); // CHANGE: Use pin here
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("SQL Error updating balance: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // REMOVED: public String getCardNumberByPin(String pin) { ... }
    // This method is no longer needed if you're using PIN as the primary identifier for balances.

    /**
     * Checks if a given email address is already registered in the 'users' table.
     *
     * @param email The email address to check.
     * @return true if the email is already registered, false otherwise.
     */
    public boolean isEmailRegistered(String email) {
        String query = "SELECT 1 FROM users WHERE email = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("SQL Error during email check: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Checks if a given phone number is already registered in the 'users' table.
     *
     * @param phoneNumber The phone number to check.
     * @return true if the phone number is already registered, false otherwise.
     */
    public boolean isPhoneNumberRegistered(String phoneNumber) {
        // Query to check phone number
        String query = "SELECT 1 FROM users WHERE phoneNumber = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, phoneNumber);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("SQL Error during phone number check: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updates the PIN for a specific user identified by their current PIN.
     * This simplified version assumes the provided 'oldPin' is unique enough to find the user.
     *
     * @param oldPin The user's current (old) PIN.
     * @param newPin The new PIN to set.
     * @return true if the PIN was successfully updated, false otherwise.
     */
    public boolean updatePin(String oldPin, String newPin) {
        // Query to update the PIN for a user identified by their old PIN
        String query = "UPDATE users SET pin = ? WHERE pin = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, newPin);
            pstmt.setString(2, oldPin);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("SQL Error updating PIN: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Performs a debit transaction (withdrawal or fast cash) for a user.
     * This method now includes the balance check and is transactional.
     *
     * @param pin The user's PIN.
     * @param amount The amount to debit.
     * @param type The type of transaction (e.g., "Withdrawal", "Fast Cash").
     * @return true if the transaction was successful, false otherwise (e.g., insufficient funds, database error).
     */
    public boolean performDebitTransaction(String pin, double amount, String type) {
        double currentBalance = getBalance(pin);
        if (currentBalance == -1.0) {
            System.err.println("Error: Could not retrieve current balance for PIN: " + pin);
            return false;
        }

        if (currentBalance < amount) {
            System.out.println("Insufficient funds for withdrawal. Current balance: " + currentBalance + ", requested: " + amount);
            return false;
        }

        double newBalance = currentBalance - amount;

        Connection con = null;
        try {
            con = DatabaseConnection.getConnection();
            con.setAutoCommit(false);


            String updateBalanceQuery = "UPDATE users SET balance = ? WHERE pin = ?";
            try (PreparedStatement pstmt1 = con.prepareStatement(updateBalanceQuery)) {
                pstmt1.setDouble(1, newBalance);
                pstmt1.setString(2, pin);
                int rowsAffected = pstmt1.executeUpdate();
                if (rowsAffected == 0) {
                    con.rollback();
                    return false;
                }
            }

            String insertTransactionQuery = "INSERT INTO bank (pin, date, type, amount) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt2 = con.prepareStatement(insertTransactionQuery)) {
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = dateFormat.format(date);

                pstmt2.setString(1, pin);
                pstmt2.setString(2, formattedDate);
                pstmt2.setString(3, type);
                pstmt2.setDouble(4, amount);
                pstmt2.executeUpdate();
            }

            con.commit();
            return true;

        } catch (SQLException e) {
            System.err.println("SQL Error during debit transaction: " + e.getMessage());
            e.printStackTrace();
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    System.err.println("Error during rollback: " + ex.getMessage());
                }
            }
            return false;
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }
    }

    public String getCardNumberByPin(String pin) {
        String cardNumber = null;
        // Ensure 'users' table has a 'cardNumber' column
        String query = "SELECT cardNumber FROM users WHERE pin = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, pin);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    cardNumber = rs.getString("cardNumber");
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Error getting card number by PIN: " + e.getMessage());
            e.printStackTrace();
        }
        return cardNumber;
    }
    /**
     * Validates the format of a phone number using a regular expression.
     *
     * @param phone The phone number string to validate.
     * @return true if the phone number matches the expected format, false otherwise.
     */
    public boolean validatePhone(String phone) {
        // phone regex
        return phone.matches("^\\+420?[0-9]{7,15}$");
    }

    /**
     * Validates the format of an email address using a regular expression.
     *
     * @param email The email address string to validate.
     * @return true if the email matches a basic email format, false otherwise.
     */
    public boolean validateEmail(String email) {
        // email regex
        return email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

    public static class Transaction {
        public String date;
        public String type;
        public double amount;

        public Transaction(String date, String type, double amount) {
            this.date = date;
            this.type = type;
            this.amount = amount;
        }

        @Override
        public String toString() {
            return String.format("%s %-15s %.2f CZK", date, type, amount);
        }
    }

    public List<Transaction> getTransactionHistory(String pin) {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT date, type, amount FROM bank WHERE pin = ? ORDER BY date DESC";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, pin);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // Extract data for the current transaction from the ResultSet
                    String date = rs.getString("date");
                    String type = rs.getString("type");
                    double amount = rs.getDouble("amount"); // Retrieve as double

                    // Create a new Transaction object with the retrieved data and adds to the list
                    Transaction tx = new Transaction(date, type, amount);
                    transactions.add(tx);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Error retrieving transaction history: " + e.getMessage());
            e.printStackTrace();
        }
        return transactions;
    }
}