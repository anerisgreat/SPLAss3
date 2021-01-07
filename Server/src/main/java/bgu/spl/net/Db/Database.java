package bgu.spl.net.Db;

import bgu.spl.net.srv.User;
import org.graalvm.compiler.lir.LIRInstruction;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Passive object representing the Database where all courses and users are stored.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add private fields and methods to this class as you see fit.
 */

public class Database {

    private static Database singleton;
    private List<Course> courses; // stores all the courses
    private List<String> admins; //stores all the usernames who have admin access
    private ConcurrentHashMap<String, String> users; //stores all the users who are registered <username, password>
    private ConcurrentHashMap<String, List<Course>> studentCourses; //stores the courses each student is registered to <username, list of courses>

    //to prevent user from creating new Database
    private Database() {
        courses = new LinkedList<>();
        users = new ConcurrentHashMap<>(); //stores
        admins = new LinkedList<>();
        studentCourses = new ConcurrentHashMap<>();
    }

    /**
     * Retrieves the single instance of this class.
     */
    public static Database getInstance() {
        if (singleton == null) {
            singleton = new Database();
        }
        return singleton;
    }

    /**
     * loades the courses from the file path specified
     * into the Database, returns true if successful.
     */
    boolean initialize(String coursesFilePath) {
        try {
            File myObj = new File(coursesFilePath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                courses.add(initCourse(data));
            }
            myReader.close();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return false;
    }

    //reads a single course from the input file
    private Course initCourse(String courseLine) throws Exception {
        int courseNum;
        String courseName;
        List<Integer> kdamCourses = new LinkedList<>();
        int maxCourses;
        String[] curr;
        String[] kdamCurr;
        curr = courseLine.split("\\|");
        courseNum = Integer.parseInt(curr[0]);
        courseName = curr[1];
        kdamCurr = curr[2].split(",");
        for (String s : kdamCurr) {
            if (s.startsWith("[")) {
                s = s.substring(1);
            }
            if (s.endsWith("]")) {
                s = s.substring(0,s.length() - 1);
            }
            kdamCourses.add(Integer.parseInt(s));
        }

        maxCourses = Integer.parseInt(curr[3]);
        if (courseNum < 0 || courseName.length() == 0 || maxCourses <= 5) {
            throw new Exception("illegal course parameters");
        }
        return new Course(courseNum, courseName, kdamCourses, maxCourses);
    }
    //registers an admin to system return false if failed
    public boolean adminReg(String userName, String password) {
        if (users.contains(userName)) {
            return false;
            //does admin have courses???
        }
        else {
            users.put(userName, password);
            admins.add(userName);
            return true;
        }
    }

    //registers a student to the system return false if failed
    public boolean studentReg(String userName, String password) {
        if (users.contains(userName)) {
            return false;
        }
        else {
            users.put(userName, password);
            studentCourses.put(userName, new LinkedList<>());
            return true;
        }
    }

    //tries to login return false if failed
    public boolean Login(String userName, String password) {
        if (!users.contains(userName) || users.get(userName) != password) {
            return false;
        }
        return true;
    }

    /*
    public boolean Logout(String userName) {
        if (!loggedIn.contains(userName)) {
            return false;
        }
        loggedIn.remove(userName);
        return true;
    }

     */

    //register a student to a course
    public boolean CourseReg(int courseNum, String userName) {
        if (!canRegisterToCourse(courseNum, userName)){
            return false;
        }
        else{
            studentCourses.get(userName).add(getCourseByNum(courseNum));
            return true;
        }
    }

    //returns the list of kdam courses for a course
    public String kdamCheck(int courseNum) {
        if(!courses.contains(getCourseByNum(courseNum))){
            return null;
        }
        else{
            return getCourseByNum(courseNum).getKdamCourses().toString();
        }
    }

    //return the stats for a course
    public String courseStat(int courseNum) {
        Course curr = getCourseByNum(courseNum);
        if (!courses.contains(curr)) {
            return null;
        }
        int courseCount = countCourse(courseNum);
        String userMsg = "Course: (" + curr.getCourseNum() + ") " + curr.getCourseName() + "\n";
        userMsg = userMsg + "Seats Available: " + (curr.getMaxCourses() - courseCount) + " / " + courseCount + "\n";
        userMsg = userMsg + "Students Registered: " + getStudents(courseNum);
        return userMsg;
    }

    //returns the stats for a student
    public String studentStat(String userName) {
        String userMsg = "Student: " + userName + "\n";
        userMsg = userMsg + "Courses" + studentCourses.get(userName);
        return userMsg;
    }

    //checks if a student is registered to a course
    public boolean isRegistered(int courseNum, String userName) {
        return studentCourses.get(userName).contains(courseNum);
    }

    //unregisters a student from a course return false if failed
    public boolean unRegister(int courseNum, String userName) {
        if(!studentCourses.get(userName).contains(courseNum)) {
            return false;
        }
        studentCourses.get(userName).remove(courseNum);
        return true;
    }

    //returns all the courses a student attends
    public String myCourses(String userName){
        return studentCourses.get(userName).toString();
    }

    //check if a student can register to a course
    private boolean canRegisterToCourse(int courseNum, String userName) {
        boolean ans = true;
        Course curr = getCourseByNum(courseNum);
        //checks if there is such course
        ans = ans && getCourseByNum(courseNum) != null;
        //checks if student is not already registered
        ans = ans && !studentCourses.get(userName).contains(getCourseByNum(courseNum));
        //check if course has an open seat
        ans = ans && (countCourse(courseNum) < curr.getMaxCourses());
        //check if the student has all the kdam courses
        for (int i : curr.getKdamCourses()) {
            ans = ans && studentCourses.get(userName).contains(i);
        }
        return ans;
    }

    //returns a course with courseNum as his number - null if no such course
    private Course getCourseByNum(int courseNum) {
        for(Course c : courses) {
            if(c.getCourseNum() == courseNum) {
                return c;
            }
        }
        return null;
    }

    //counts the number of students that attend this course
    private int countCourse(int courseNum) {
        int counter = 0;
        for (String user : users.keySet()) {
            if(studentCourses.get(user).contains(courseNum)) {
                counter ++;
            }
        }
        return counter;
    }

    //returns a list of student who are registered to a course
    private List<String> getStudents(int courseNum) {
        List<String> ans = new LinkedList<>();
        for (String user : users.keySet()) {
            if(studentCourses.get(user).contains(courseNum)) {
                ans.add(user);
            }
        }
        Collections.sort(ans);
        return ans;
    }
}
