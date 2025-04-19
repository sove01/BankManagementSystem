import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;

public class Conn {
    Connection con;
    Statement st;

    public Conn() {
        try {
            String dbURL = "jdbc:sqlite:src/userDB.db";
            con = DriverManager.getConnection(dbURL);
            st = con.createStatement();

            String createTableQuery = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL, " +
                    "balance REAL DEFAULT 0.0)";
            st.executeUpdate(createTableQuery);

            System.out.println("Connected to existing sqlite database successfully");

        } catch (SQLException e) {
            throw new RuntimeException(e);
//            e.printStackTrace();
        }
    }
}
