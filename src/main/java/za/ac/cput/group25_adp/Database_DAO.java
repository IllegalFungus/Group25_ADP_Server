package za.ac.cput.group25_adp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class Database_DAO {
    private Connection con;
    private PreparedStatement pstmt;
    private String userName, userSurname, userPassword;
    private boolean isAdmin;
    private User newUser;
    
    public Database_DAO() {
        try {
            this.con = Database_Connection.derbyConnection();
        } catch(Exception exception) {
            //Error Message
            System.out.println("Error");
        }
    }
    
    public User getUser(User user) {
        try {
            pstmt = this.con.prepareStatement("SELECT * FROM DBADMIN.USERS WHERE userID = ?");
                pstmt.setInt(1, user.getUserID());
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()){
                //Saving the users details.
                userName = resultSet.getString("userName");
                userSurname = resultSet.getString("userSurname");
                userPassword = resultSet.getString("userPassword");
                isAdmin = resultSet.getBoolean("isAdmin");
                
                newUser = new User(user.getUserID(), userPassword, userName, userSurname, isAdmin);
            }
        } catch (SQLException SQLException) {
            //Error
        }
        return newUser;
    }
    
    public User Login(User user){        
        try
        {
           pstmt = this.con.prepareStatement("SELECT * FROM DBADMIN.USERS WHERE userID = ?");
                pstmt.setInt(1, user.getUserID()); 
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()){
                userPassword = resultSet.getString("userPassword");
                userName = resultSet.getString("userName");
                userSurname = resultSet.getString("userSurname");
                isAdmin = resultSet.getBoolean("isAdmin");
                
                if ((user.getPass()).equals(userPassword)) {
                    newUser = new User(user.getUserID(), userPassword, userName, userSurname, isAdmin);
                    return newUser;
                } else {
                    JOptionPane.showMessageDialog(null, "3");
                    return null;
                   
                }
            }
        } catch (SQLException SQLe)
        {
            
        }
        return null;
    }
    
    public void addUser(User user) {
        try
        {   
            pstmt = this.con.prepareStatement("INSERT INTO DBADMIN.USERS (USERID, USERNAME, USERSURNAME, USERPASSWORD, ISADMIN) VALUES (?, ?, ?, ?, ?)");
            pstmt.setInt(1, user.getUserID());
            pstmt.setString(2, user.getUserFName());
            pstmt.setString(3, user.getUserLName());
            pstmt.setString(4, user.getPass());
            pstmt.setBoolean(5, user.isAdmin());
            pstmt.execute();
            con.close();
            JOptionPane.showMessageDialog(null, "User successfully registered!");
        } catch (SQLException SQLe)
        {
            //
        }
    }
}
