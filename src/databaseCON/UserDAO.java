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
                "phoneNumber, email, gender, maritalStatus, pin, homeAddress" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; // 11 placeholders for 11 fields

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
            // IMPORTANT: In a real application, you would HASH THE PIN here
            // For example: pstmt.setString(10, PasswordHasher.hash(user.getPin()));
            pstmt.setString(10, user.getPin()); // Storing plain text PIN for now (SECURITY RISK!)
            pstmt.setString(11, user.getHomeAddress());

            int rowsAffected = pstmt.executeUpdate(); // Execute the insert statement
            return rowsAffected > 0; // Return true if at least one row was inserted

        } catch (SQLException e) {
            System.err.println("SQL Error during user signup: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
            return false; // Return false if an SQL exception occurs
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
        // Query to check for matching card number and PIN
        String query = "SELECT * FROM login WHERE card_number = ? AND pin = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, cardNum);
            pstmt.setString(2, PIN);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // Returns true if a matching row is found
            }
        } catch (SQLException e) {
            System.err.println("SQL Error during login validation: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Checks if a given email address is already registered in the 'users' table.
     *
     * @param email The email address to check.
     * @return true if the email is already registered, false otherwise.
     */
    public boolean isEmailRegistered(String email) {
        // Query to check for the existence of an email
        String query = "SELECT 1 FROM users WHERE email = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // Returns true if a row is found (email exists)
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
        // Query to check for the existence of a phone number
        String query = "SELECT 1 FROM users WHERE phoneNumber = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, phoneNumber);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // Returns true if a row is found (phone number exists)
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
        // Matches phone numbers starting with +420 (optional) followed by 7 to 15 digits.
        return phone.matches("^\\+420?[0-9]{7,15}$");
    }

    /**
     * Validates the format of an email address using a regular expression.
     *
     * @param email The email address string to validate.
     * @return true if the email matches a basic email format, false otherwise.
     */
    public boolean validateEmail(String email) {
        // Basic regex for email validation. Consider more comprehensive patterns for production.
        return email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }
}