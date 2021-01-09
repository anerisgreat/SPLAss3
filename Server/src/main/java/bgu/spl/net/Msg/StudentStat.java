package bgu.spl.net.Msg;

import bgu.spl.net.srv.MsgProtocol;

public class StudentStat implements CtoSMessage{
    public String studentName;
    public  StudentStat(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentName(){ return studentName; }

    @Override
    public Message visit(MsgProtocol msgProtocol) {
        return msgProtocol.visit(this);
    }
}
