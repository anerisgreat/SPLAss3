package bgu.spl.net.Db;

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
    private List<Course> courses;
    private ConcurrentHashMap<String, String> users;

    //to prevent user from creating new Database
    private Database() {
        courses = new LinkedList<>();
        users = new ConcurrentHashMap<>();
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
}
