package bgu.spl.net.Db;

import java.util.List;

public class Course {
    private int courseNum;
    private String courseName;
    private List<Integer> kdamCourses;
    private int maxCourses;

    public Course(int courseNum, String courseName, List<Integer> kdamCourses, int maxCourses) {
        this.courseNum = courseNum;
        this.courseName = courseName;
        this.kdamCourses = kdamCourses;
        this.maxCourses = maxCourses;
    }

    public int getCourseNum() {
        return courseNum;
    }

    public int getMaxCourses() {
        return maxCourses;
    }

    public List<Integer> getKdamCourses() {
        return kdamCourses;
    }
    public String getCourseName() {
        return courseName;
    }
}
