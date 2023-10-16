package za.ac.cput.group25_adp;

import java.io.Serializable;

public class UserCourse implements Serializable{
    int UserCourseID;
    int UserID;
    int CourseID;
    String Function;
    
    public UserCourse(int ucID, int uID, int cID, String function) {
        this.UserCourseID = ucID;
        this.UserID = uID;
        this.CourseID = cID;
        this.Function = function;
    }
    
    public int getUserCourseID() {
        return UserCourseID;
    }
    
    public int getUserID() {
        return UserID;
    }
    
    public int getCourseID() {
        return CourseID;
    }
    
    public String getFunction() {
        return Function;
    }
}
