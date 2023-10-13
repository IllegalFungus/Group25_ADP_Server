package za.ac.cput.group25_adp;

import java.io.Serializable;

public class Users {
    
    public class User implements Serializable
    {
        private String fstName;
        private String lstName;
        private String Password;
        private int ID;
        private boolean isAdmin;
    
    public User(int ID, String password)
    {
        this.ID = ID;
        this.Password = password;
    }
    
    public User(int ID, String pass, String name, String surname, boolean isAdmin)
    {
        this.ID = ID;
        this.Password = pass;
        this.fstName = name;
        this.lstName = surname;
        this.isAdmin = isAdmin;
    }
    
    public String getUserFName()
    {
        return fstName;
    }
    
    public void setUserFName()
    {
        this.fstName = fstName;
    }
    
    public String getUserLName()
    {
        return lstName;
    }
    
    public void setUserLName()
    {
        this.lstName = lstName;
    }
    
    public int getUserID()
    {
        return ID;
    }
    
    public void setUserID()
    {
        this.ID = ID;
    }
    
    public String getPass()
    {
        return Password;
    }
    
    public void setPass()
    {
        this.Password = Password;
    }
    
    public Boolean isAdmin()
    {
        return isAdmin;
    }
    
    public String toString()
    {
        //Temporary to string method.
        return ID + ", " + fstName + ", " + lstName;
    }
    }
    
}
