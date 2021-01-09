package bgu.spl.net.srv;

import bgu.spl.net.Db.DB_User;
import bgu.spl.net.Db.Database;
import bgu.spl.net.Msg.*;
import bgu.spl.net.api.MessagingProtocol;

import java.util.List;

public class MsgProtocol implements MessagingProtocol<Message> {

    private User user; //stores details of the user which has this instance of MsgProtocol in his connection handler
    private Database db;

    public MsgProtocol() {
        user = new User();
        db = Database.getInstance();
    }

    @Override
    public Message process(Message msg) {
        return msg.visit(this);
    }

    @Override
    public boolean shouldTerminate() {
        return user.getShouldTerminate();
    }

    //Admin registration
    public Message visit(AdminReg ar) {
        if (db.registerAdmin(ar.getUserName(), ar.getPassword())) {
            return new Ack((short)1, "");
        }
        return new Err((short)1);
    }

    //Student registration
    public Message visit(StudentReg sr){
        if(db.registerStudent(sr.getUserName(),sr.getPassword())) {
            return new Ack((short)2, "");
        }
        return new Err((short)2);
    }

    //Login
    public Message visit(Login l) {
        if (user.getLoggedIn()) {
            return new Err((short)3);
        }
        if (db.Login(l.getUserName(), l.getPassword())) {
            user.setLoggedIn(true);
            user.setUserName(l.getUserName());
            user.setShouldTerminate(false);
            return new Ack((short)3,"");
        }
        return new Err((short)3);
    }

    //Logout
    public Message visit(Logout l) {
        db.logout(user.getUserName());
        user.setLoggedIn(false);
        user.setUserName(null);
        user.setShouldTerminate(true);
        return new Ack((short)4, "");
    }

    //Course registration
    public Message visit(CourseReg cr) {
        if (db.registerToCourse(cr.getCourseNum(), user.getUserName())){
            return new Ack((short)5, "");
        }
        return new Err((short)5);
    }

    //gets a list of all the kdam courses
    public Message visit(KdamCheck kc) {
        List<Integer> kdams = db.getKdam(kc.getCourseNum());
        if(kdams != null) {
            return new Ack((short)6, kdams.toString());
        }
        return new Err((short)6);
    }

    //Course Stats
    public Message visit(CourseStat cs) {
       String msg = db.getCourseStats(cs.getCourseNum(), user.getUserName());
        if (msg == null) {
            return new Err((short)7);
        }
        return new Ack((short)7, msg);
    }

    //Student Stats
    public Message visit(StudentStat ss){
        if(!db.isAdmin(ss.getStudentName())) {
            return new Err((short)8);
        }
        String userMsg = "Student: " + ss.getStudentName() + "\n";
        userMsg = userMsg + "Courses" + db.getCourses(ss.getStudentName()).toString();
        return new Ack((short)8, userMsg);
    }


    //check if a student is registered to a course
    public Message visit(IsRegistered ir) {
        if(db.isRegisteredToCourse(ir.getCourseNum(), user.getUserName())){
            return new Ack((short)9, "REGISTERED");
        }
        return new Ack((short)9, "NOT REGISTERED");
    }

    //UnRegister from course
    public Message visit(UnRegister ur) {
        if(db.unRegister(ur.getCourseNum(), user.getUserName())){
            return new Ack((short)10, "");
        }
        return new Err((short)10);
    }

    //my Courses
    public Message visit(MyCourses mc) {
        List<Integer> courses = db.getCourses(user.getUserName());
        if (courses == null) {
            return new Err((short)11);
        }
        return new Ack((short)11, courses.toString());
    }

}
