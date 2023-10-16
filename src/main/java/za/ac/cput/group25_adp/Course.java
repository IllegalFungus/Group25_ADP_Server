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

    public Course() {
    }

    public Course(String courseID, String courseTitle, String courseFaculty) {
        this.courseID = courseID;
        this.courseTitle = courseTitle;
        this.courseFaculty = courseFaculty;
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

    @Override
    public String toString() {
        return "Course{" + "courseID=" + courseID + ", courseTitle=" + courseTitle + ", courseFaculty=" + courseFaculty + '}';
    }
    
    
}
