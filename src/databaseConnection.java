import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class databaseConnection {
    private static Connection con;

    public databaseConnection() {

    }

    public static Connection getConnection() throws SQLException {
        if (con == null || con.isClosed()) {
            String dbURL = "jdbc:sqlite:src/userDB.db";
            con = DriverManager.getConnection(dbURL);
            System.out.println("Connected to existing sqlite database successfully");
        }
        return con;
    }

//Closes the connection when shutting down app
    public static void closeConn() {
        if (con != null) {
            try {
                con.close();
                System.out.println("Connection closed successfully");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
