package za.ac.cput.group25_adp;

import java.io.Serializable;

/**
 *
 * @author imaan
 */
public class Course implements Serializable{
    String courseID;
    String courseTitle;
    String courseFaculty;
    String function;

    public Course() {
    }

    public Course(String courseID, String courseTitle, String courseFaculty, String function) {
        this.courseID = courseID;
        this.courseTitle = courseTitle;
        this.courseFaculty = courseFaculty;
        this.function = function;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseFaculty() {
        return courseFaculty;
    }

    public void setCourseFaculty(String courseFaculty) {
        this.courseFaculty = courseFaculty;
    }
    
    public String getFunction() {
        return function;
    }

    public void setFunction(String courseFaculty) {
        this.function = function;
    }
    
    

    @Override
    public String toString() {
        return "Course{" + "courseID=" + courseID + ", courseTitle=" + courseTitle + ", courseFaculty=" + courseFaculty + '}';
    }
    
    
}
