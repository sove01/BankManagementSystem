package databaseCON;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        // Ensure the column names here match your actual database table schema.
        String query = "INSERT INTO users (" +
                "firstName, lastName, nationality, region, city, " +
                "phoneNumber, email, gender, maritalStatus, pin, homeAddress, cardNumber" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; // 11 placeholders for 11 fields

        // Using try-with-resources to ensure Connection and PreparedStatement are closed automatically
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
     * @param PIN The PIN provided by the user.
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
}