package bgu.spl.net.Db;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Course {
    private int courseNum;
    private String courseName;
    private List<Integer> kdamCourses;
    private List<String> studentsAttend;
    private int maxCourses;


    public Course(int courseNum, String courseName, List<Integer> kdamCourses, int maxCourses) {
        this.courseNum = courseNum;
        this.courseName = courseName;
        this.kdamCourses = kdamCourses;
        this.maxCourses = maxCourses;
        studentsAttend = Collections.synchronizedList(new LinkedList<>());
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

    public boolean registerToCourse(DB_User dbu) {
        synchronized (studentsAttend){
            if (studentsAttend.size() < maxCourses && !studentsAttend.contains(dbu.getUserName())) {
                if (dbu.getCourses().containsAll(kdamCourses)) {
                    studentsAttend.add(dbu.getUserName());
                    dbu.addCourse(courseNum);
                    return true;
                }
            }
        }
        return false;
    }
    public boolean unRegisterFromCourse(DB_User dbu) {
        synchronized (studentsAttend) {
            if(studentsAttend.contains(dbu.getUserName())) {
                studentsAttend.remove(dbu.getUserName());
                dbu.removeCourse(courseNum);
                return true;
            }
        }
        return false;
    }

    public int countStudents() {
        return studentsAttend.size();
    }

    public List<String> getStudents() {
        return studentsAttend;
    }
}
