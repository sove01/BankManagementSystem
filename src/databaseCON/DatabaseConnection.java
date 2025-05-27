package databaseCON;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class DatabaseConnection {
    private static Connection con;

    public DatabaseConnection() {

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
                con = null;
                System.out.println("Connection closed successfully");
            } catch (SQLException e) {
                System.out.println("Connection could not be closed" );
                throw new RuntimeException(e);
            }
        }
    }
}
