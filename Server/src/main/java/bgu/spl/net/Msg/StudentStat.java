package bgu.spl.net.Msg;

import bgu.spl.net.srv.User;

public class StudentStat extends absMsg{
    private String studentName;
    public  StudentStat(String studentName) {
        super((short)8);
        this.studentName = studentName;
    }

    @Override
    public Message process(User user) {
        return new Ack(getOpCode(), getDb().studentStat(studentName));
    }
}
