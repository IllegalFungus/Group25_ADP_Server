package za.ac.cput.group25_adp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database_Connection {
    public static Connection derbyConnection() throws SQLException {
        String DATABASE_URL = "jdbc:derby://localhost:1527/Grp25Database";
        String username = "dbadmin";
        String password = "password";
        
        Connection connection = DriverManager.getConnection(DATABASE_URL, username, password);
        return connection;
    }
}
