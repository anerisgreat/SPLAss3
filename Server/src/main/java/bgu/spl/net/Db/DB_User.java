package bgu.spl.net.Db;

import java.util.LinkedList;
import java.util.List;

public class DB_User {
    private String userName;
    private String passWord;
    private boolean isAdmin;
    private List<Integer> courses;
    private boolean loggedIn;

    public DB_User(String userName, String passWord, boolean isAdmin) {
        this.userName = userName;
        this.passWord = passWord;
        this.isAdmin = isAdmin;
        courses = new LinkedList<>();
        loggedIn = false;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String getUserName() {
        return userName;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public boolean addCourse(int courseNum) {
        if(courses.contains(courseNum)) {
            return false;
        }
        courses.add(courseNum);
        return true;
    }

    public void removeCourse(int courseNum) {
        for (int i = 0; i < courses.size(); i++) {
            if(courses.get(i) == courseNum) {
                courses.remove(i);
            }
        }
    }

    public List<Integer> getCourses() {
        return courses;
    }
}
