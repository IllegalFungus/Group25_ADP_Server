package za.ac.cput.group25_adp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;

public class ClientServer_Server {
    private static ObjectOutputStream out;
    private static ObjectInputStream in;
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static Object receivedObject;
    User Login;
    Database_DAO dao;
    
    public ClientServer_Server()
    {
        int port = 12345;
        try
        {
            serverSocket = new ServerSocket(port);
            System.out.println("Server is listening on port " + port);
            
            clientSocket = serverSocket.accept();
            System.out.println("Client Connected: " + clientSocket.getInetAddress().getHostAddress());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        
        dao= new Database_DAO();
    }
    
    public void getStreams()
    {   
        try
        {
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public void processUser() {
        //All code needed for processing
//        ArrayList<User> users = new ArrayList<>();
            while(true)
            {
                try
                {
                    receivedObject = in.readObject();
                    //Will need to find a way to not get functionality off the object
                    //cause will be recieving object for more than just the login,
                    //will need to differentiate somehow. (Maybe have it in the object itself.)
                    //eg. the object is sent as ID, Pass, and Function.
                    if (receivedObject instanceof User)
                    {
                        //Code for if the Server recieved a User's information.
                        User newUser = (User) receivedObject;
                        
                        Login = dao.Login(newUser);
                        if (Login != null)
                        {
                            JOptionPane.showMessageDialog(null, "User, " + Login.getUserFName()
                                + " " + Login.getUserLName() + " is successfully logged in!");
                            try
                            {
                                out.writeObject(Login);
                                out.flush();
                            }
                            catch (IOException ioe)
                            {
                                System.out.println("IO Exception: " + ioe.getMessage());
                            }
                        }
                    } else if (receivedObject instanceof String && ((String) receivedObject).equals("exit"))
                    {
                        closeConnection();
                    }
                    // To Delete: Testing Sign Up:
                    else if (receivedObject instanceof String && ((String) receivedObject).equals("signup"))
                    {
                            User testUser = new User(123456, "password", "Zak", "Marley", false);
                            dao.addUser(testUser);
                                                
                    }
                    //
                } catch (ClassNotFoundException cnfe)
                {
                    cnfe.printStackTrace();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
    }
    
    private static void closeConnection() {
        try
        {
            out.writeObject("exit");
            out.flush();
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
            System.out.println("Server Closing Connection");
            System.exit(0);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        ClientServer_Server server = new ClientServer_Server();
        server.getStreams();
        server.processUser();
    }
}
