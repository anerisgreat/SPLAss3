package bgu.spl.net.Msg;

import bgu.spl.net.srv.User;

public class StudentStat extends absMsg{
    private String studentName;
    public  StudentStat(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentName(){ return studentName; }

    //TODO: Remove
    @Override
    public Message process(User user) {
        String ans = getDb().studentStat(studentName, user.getUserName());
        if (ans == null) {
            return new Err(getOpCode());
        }
        return new Ack(getOpCode(), ans);
    }
}
