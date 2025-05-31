package databaseCON;

import javax.swing.*;
import javax.xml.crypto.Data;
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
            pstmt.setString(10, user.getPin()); // security risk
            pstmt.setString(11, user.getHomeAddress());
            pstmt.setString(12, user.getCardNumber());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("SQL Error during user signup: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Validates user login credentials against the 'login' table.
     * IMPORTANT: This method assumes card_number and pin are stored in plain text.
     * For a secure application, PINs should be hashed and salted.
     *
     * @param cardNum The card number provided by the user.
     * @param PIN     The PIN provided by the user.
     * @return true if the credentials are valid, false otherwise.
     */
    public boolean validateLogin(String cardNum, String PIN) {
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
     * Retrieves the current balance for a given card number.
     *
     * @param cardNumber The user's card number.
     * @return The current balance, or -1.0 if an error occurs or user not found.
     */
    public double getBalance(String cardNumber) {
        double balance = -1.0; // Default error value
        String query = "SELECT balance FROM users WHERE cardNumber = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, cardNumber);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    balance = rs.getDouble("balance");
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Error getting balance: " + e.getMessage());
            e.printStackTrace();
        }
        return balance;
    }

    /**
     * Updates the balance for a given card number.
     *
     * @param cardNumber The user's card number.
     * @param newBalance The new balance to set.
     * @return true if the balance was successfully updated, false otherwise.
     */
    public boolean updateBalance(String cardNumber, double newBalance) {
        String query = "UPDATE users SET balance = ? WHERE cardNumber = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setDouble(1, newBalance);
            pstmt.setString(2, cardNumber);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("SQL Error updating balance: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public String getCardNumberByPin(String pin) {
        String cardNumber = null;
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
     * Performs a debit transaction (withdrawal or fast cash).
     * It checks balance, updates the user's balance in the 'users' table,
     * and records the transaction in the 'bank' table.
     *
     * @param pin             The user's PIN (used to find the card number and for bank table).
     * @param amount          The amount to debit.
     * @param transactionType The type of transaction (e.g., "Withdrawal", "Fast Cash").
     * @return true if the debit is successful, false otherwise (e.g., insufficient balance, DB error).
     */
    public boolean performDebitTransaction(String pin, double amount, String transactionType) {
        String cardNumber = getCardNumberByPin(pin);
        if (cardNumber == null) {
            System.err.println("Error: Card number not found for PIN: " + pin);
            return false;
        }

        Connection con = null;
        PreparedStatement pstmtUpdateUsers = null;
        PreparedStatement pstmtInsertBank = null;
        try {
            con = DatabaseConnection.getConnection();
            con.setAutoCommit(false);


            double currentBalance = getBalance(cardNumber);
            if (currentBalance == -1.0) {
                con.rollback();
                return false;
            }

            if (currentBalance < amount) {
                JOptionPane.showMessageDialog(null, "Insufficient Balance.");
                con.rollback(); // Rollback any changes if balance is low
                return false;
            }


            double newBalance = currentBalance - amount;

            String updateUsersQuery = "UPDATE users SET balance = ? WHERE cardNumber = ?";
            pstmtUpdateUsers = con.prepareStatement(updateUsersQuery);
            pstmtUpdateUsers.setDouble(1, newBalance);
            pstmtUpdateUsers.setString(2, cardNumber);
            int usersRowsAffected = pstmtUpdateUsers.executeUpdate();

            if (usersRowsAffected == 0) {
                System.err.println("Failed to update user balance for card: " + cardNumber);
                con.rollback();
                return false;
            }


            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = dateFormat.format(date);

            String insertBankQuery = "INSERT INTO bank (pin, date, type, amount) VALUES (?, ?, ?, ?)";
            pstmtInsertBank = con.prepareStatement(insertBankQuery);
            pstmtInsertBank.setString(1, pin);
            pstmtInsertBank.setString(2, formattedDate);
            pstmtInsertBank.setString(3, transactionType); //fastcash and withdrawl
            pstmtInsertBank.setDouble(4, amount);
            int bankRowsAffected = pstmtInsertBank.executeUpdate();

            if (bankRowsAffected == 0) {
                System.err.println("Failed to record transaction in bank table for card: " + cardNumber);
                con.rollback();
                return false;
            }

            con.commit();
            return true;

        } catch (SQLException e) {
            System.err.println("SQL Error during debit transaction: " + e.getMessage());
            e.printStackTrace();
            try {
                if (con != null) con.rollback(); // rollback balance if error happens
            } catch (SQLException ex) {
                System.err.println("Error rolling back transaction: " + ex.getMessage());
            }
            return false;
        } finally {
            try {
                if (pstmtUpdateUsers != null) pstmtUpdateUsers.close();
                if (pstmtInsertBank != null) pstmtInsertBank.close();
                if (con != null) con.setAutoCommit(true);
                if (con != null) con.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
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

        // This method helps to easily print a Transaction object later
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