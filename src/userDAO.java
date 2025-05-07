import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class userDAO {
    public boolean validateLogin(String cardNum, String PIN) {
        String query = "SELECT * FROM login WHERE card_number = ? AND pin = ?";
        try (Connection con = databaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, cardNum);
            pstmt.setString(2, PIN);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;

        }

    }
}
