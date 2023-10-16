package za.ac.cput.group25_adp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ClientServer_Server {
    private static ObjectOutputStream out;
    private static ObjectInputStream in;
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static Object receivedObject;
    User Login, SingleUser;
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
    
    public void processClient() {
        //All code needed for processing
//        ArrayList<User> users = new ArrayList<>();
            while(true)
            {
                try
                {
                    //Receiving the object from the client. From here we differentiate each function using the function attribute in both Course and User class.
                    receivedObject = in.readObject();
                    //All of the following methods revolve around the User worker class.
                    if (receivedObject instanceof User)
                    {
                        //Parse the objecct through as a User.
                        User newUser = (User) receivedObject;
                        //First Function: Login.
                        if (newUser.getFunction().equals("Login")) {
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
                        //Second Function: Add User.
                        } else if (newUser.getFunction().equals("addUser")) {
                            //Send user object to database to add.
                            dao.addUser(newUser);
                            //Send confirmation to the client that user has been added.
                            try
                            {
                                out.writeObject("User Added: " + newUser.getUserID());
                                out.flush();
                            } catch (IOException ioe)
                            {
                                System.out.println("IO Exception in addUser() server: " + ioe.getMessage());
                            }   
                        //Third Function: Delete User.
                        } else if (newUser.getFunction().equals("Delete")) {
                            //Send user object to database to delete that user from the database.
                            dao.deleteUser(newUser); //Error because haven't gotten the dao yet.
                            //Send confirmation to the client that user has been deleted.
                            try
                            {
                                out.writeObject("User Deleted: " + newUser.getUserID());
                                out.flush();
                            } catch (IOException ioe)
                            {
                                System.out.println("IO Exception in deleteUser() server: " + ioe.getMessage());
                            }
                        //Fourth Function: Get Single User.
                        } else if(newUser.getFunction().equals("getUser")) {
                            //Send basic user object to database to retreive the full user object from the database.
                            SingleUser = dao.getUser(newUser); //Error because haven't gotten the dao yet. Save the retreived user to send
                            //Send new object to client.
                            try
                            {
                                out.writeObject(SingleUser);
                                out.flush();
                            } catch (IOException ioe)
                            {
                                System.out.println("IO Exception in retreive singleUser() server: " + ioe.getMessage());
                            } 
                        } //End of sole user processes.
                    } else if (receivedObject instanceof Course)
                    {
                        //Parse received object through as a course.
                        Course newCourse = (Course) receivedObject;
                        //Fifth Function: Add Course.
                        if (newCourse.getFunction().equals("addCourse")) {
                            //Send course object to database to add.
                            dao.addCourse(newCourse);
                            //Send confirmation to the client that course has been added.
                            try
                            {
                                out.writeObject("User Added: " + newCourse.getCourseID());
                                out.flush();
                            } catch (IOException ioe)
                            {
                                System.out.println("IO Exception in addCourse() server: " + ioe.getMessage());
                            }  
                        //Sixth Function: Retreive Single Course.
                        } else if(newCourse.getFunction().equals("getCourse")) {
                            //Send basic course object to database to retreive the full course object from the database.
                            SingleCourse = dao.getCourse(newCourse); //Error because haven't gotten the dao yet. Save the retreived user to send
                            //Send new course to client.
                            try
                            {
                                out.writeObject(SingleCourse);
                                out.flush();
                            } catch (IOException ioe)
                            {
                                System.out.println("IO Exception in retreive singleCourse() server: " + ioe.getMessage());
                            } 
                        //Seventh Function: Delete Course.    
                        } else if(newCourse.getFunction().equals("deleteCourse")) {
                            //Send course to dao to delete it.
                            dao.deleteCourse(newCourse); //Error because haven't gotten the dao yet.
                            //Send delete confirmation to client.
                            try
                            {
                                out.writeObject("Course Deleted: " + newCourse.getCourseID());
                                out.flush();
                            } catch (IOException ioe)
                            {
                                System.out.println("IO Exception in deleteCourse() server: " + ioe.getMessage());
                            } 
                        } //End of sole course processes.
                    //Start of the string based differentiated methods.
                    //Eigth Function: Get All Courses.
                    } else if (receivedObject instanceof String && ((String) receivedObject).equals("allCourses")) {
                        ArrayList <Course> arrListCourses = new ArrayList<>();
                        arrListCourses = dao.getAllCourses();
                        try
                        {
                            out.writeObject(arrListCourses);
                            out.flush();
                        } catch (IOException ioe)
                        {
                            System.out.println("IO Exception in getAllCourses server: " + ioe.getMessage());
                        }   
                    //Ninth Function: Get All Users.
                    } else if (receivedObject instanceof String && ((String) receivedObject).equals("allUsers")) {
                        ArrayList <User> arrListUsers = new ArrayList<>();
                        arrListUsers = dao.getAllUsers();
                        try
                        {
                            out.writeObject(arrListUsers);
                            out.flush();
                        } catch (IOException ioe)
                        {
                            System.out.println("IO Exception in getAllUsers server: " + ioe.getMessage());
                        } 
                    //Tenth Function: Exit.
                    } else if (receivedObject instanceof String && ((String) receivedObject).equals("exit")) {
                        closeConnection();
                    } //End of String based processes.   
                    // Start of Bridging table processes (Enrolling and Unenrolling)
                    else if (receivedObject instanceof UserCourse) {
                        UserCourse newUC = (UserCourse) receivedObject;
                        //Eleventh Function: Enroll User.
                        if (newUC.getFunction().equals("Enroll")) {
                            //Enroll user into the database.
                            dao.enrollUser(newUC);
                            //Send confirmation.
                            try
                            {
                                out.writeObject("User: " + newUC.getUserID() + " has been enrolled into: " + newUC.getCourseID());
                                out.flush();
                            } catch (IOException ioe)
                            {
                                System.out.println("IO Exception in enroll user server: " + ioe.getMessage());
                            } 
                        } else if (newUC.getFunction().equals("Unenroll")) {
                            //Enroll user into the database.
                            dao.unenrollUser(newUC);
                            //Send confirmation.
                            try
                            {
                                out.writeObject("User: " + newUC.getUserID() + " has been unenrolled from: " + newUC.getCourseID());
                                out.flush();
                            } catch (IOException ioe)
                            {
                                System.out.println("IO Exception in enroll user server: " + ioe.getMessage());
                            } 
                        } //End of UserCourse processes.    
                    }
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
        server.processClient();
    }
}
