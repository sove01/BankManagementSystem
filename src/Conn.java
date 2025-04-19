import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;

public class Conn {
    public Connection con;

    public Conn() {
        try {
            String dbURL = "jdbc:sqlite:src/userDB.db";
            con = DriverManager.getConnection(dbURL);
            System.out.println("Connected to existing sqlite database successfully");

        } catch (SQLException e) {
            throw new RuntimeException(e);
//            e.printStackTrace();
        }
    }
}
