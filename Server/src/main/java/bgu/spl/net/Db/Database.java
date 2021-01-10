package bgu.spl.net.Db;

import bgu.spl.net.srv.User;

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
    private List<DB_User> users; // stores all the users

    private static class SingletonHolder {
        private static Database instance = new Database();
    }



    //to prevent user from creating new Database
    private Database() {
<<<<<<< HEAD
        courses = Collections.synchronizedList(new LinkedList<>());
        users = Collections.synchronizedList(new LinkedList<>());
        initialize("src/main/Courses.txt");
=======
        courses = new LinkedList<>();
        users = new LinkedList<>();
        initialize("Courses.txt");
>>>>>>> aee013f752ec62fd668021dfff3908d0ba7096b1
    }



    /**
     * Retrieves the single instance of this class.
     */
    public static Database getInstance() {
        return SingletonHolder.instance;
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
            if(s.length() > 0) {
                kdamCourses.add(Integer.parseInt(s));
            }

        }

        maxCourses = Integer.parseInt(curr[3]);
        if (courseNum < 0 || courseName.length() == 0 || maxCourses < 5) {
            throw new Exception("illegal course parameters");
        }
        return new Course(courseNum, courseName, kdamCourses, maxCourses);
    }
    //registers an admin to system return false if failed
    public boolean registerAdmin(String userName, String password) {
        synchronized (users) {
            if (getUser(userName) != null) {
                return false;
            }
            else {
                users.add(new DB_User(userName, password, true));
                return true;
            }
        }
    }

    //registers a student to the system return false if failed
    public boolean registerStudent(String userName, String password) {
        synchronized (users) {
            if (getUser(userName) != null) {
                return false;
            }
            else {
                users.add(new DB_User(userName, password, false));
                return true;
            }
        }
    }

    //tries to login return false if failed
    public boolean Login(String userName, String password) {
        DB_User dbu = getUser(userName);
        if (dbu == null || dbu.isLoggedIn() || dbu.getPassWord().compareTo(password) != 0) {
            return false;
        }
        dbu.setLoggedIn(true);
        return true;
    }


    public void logout(String userName) {
        getUser(userName).setLoggedIn(false);
    }


    //register a student to a course
    public boolean registerToCourse(int courseNum, String userName) {
        DB_User dbu = getUser(userName);
        Course course = getCourseByNum(courseNum);
        //if (!dbu.getCourses().contains(courseNum) && course != null && course.registerToCourse(dbu)){
        if (course != null && course.registerToCourse(dbu)){
            return true;
        }
        return false;
    }

    //returns the list of kdam courses for a course
    public List<Integer> getKdam(int courseNum) {
        if(!courses.contains(getCourseByNum(courseNum))){
            return null;
        }
        else{
            return getCourseByNum(courseNum).getKdamCourses();
        }
    }

    //return the stats for a course
    public String getCourseStats(int courseNum) {
        Course curr = getCourseByNum(courseNum);
        if (!courses.contains(curr)) {
            System.out.println("It goes in here.");
            for(Course c : courses) {
                System.out.println(c.getCourseNum());
            }

            return null;
        }
        //$$$not sure if i need to sync here$$$
        List<String> students = curr.getStudents();
        Collections.sort(students);
        String s = students.toString();
        s = s.replaceAll(" ", "");
        int courseCount = curr.countStudents();
        String userMsg = "Course: (" + curr.getCourseNum() + ") " + curr.getCourseName() + "\n";
        userMsg = userMsg + "Seats Available: " + (curr.getMaxCourses() - courseCount) + "/" + curr.getMaxCourses() + "\n";
        userMsg = userMsg + "Students Registered: " + s;
        return userMsg;
    }

    //checks if a student is registered to a course
    public boolean isRegisteredToCourse(int courseNum, String userName) {
        DB_User dbu = getUser(userName);
        return dbu != null && !dbu.isAdmin() && dbu.getCourses().contains(courseNum);
    }

    //unregisters a student from a course return false if failed
    public boolean unRegister(int courseNum, String userName) {
        DB_User dbu = getUser(userName);
        Course course = getCourseByNum(courseNum);
        if (dbu.getCourses().contains(courseNum) && course != null && course.unRegisterFromCourse(dbu)){
            return true;
        }
        return false;
    }

    //returns all the courses a student attends
    public List<Integer> getCourses(String userName){
        DB_User dbu = getUser(userName);
        if(dbu.isAdmin()) {
            return null;
        }
        return dbu.getCourses();
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

    private DB_User getUser(String userName) {
        for (DB_User dbu : users) {
            if (dbu.getUserName().compareTo(userName) == 0) {
                return dbu;
            }
        }
        return null;
    }

    public boolean isAdmin(String userName) {
        return getUser(userName).isAdmin();
    }
}
