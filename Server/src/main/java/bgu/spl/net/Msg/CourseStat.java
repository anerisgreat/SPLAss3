package bgu.spl.net.Msg;

import bgu.spl.net.srv.MsgProtocol;
import bgu.spl.net.srv.User;

public class CourseStat implements CtoSMessage{
    private short courseNum;
    public CourseStat(short courseNum) {
        this.courseNum = courseNum;
    }

    public short getCourseNum(){ return courseNum; }

    @Override
    public Message visit(MsgProtocol msgProtocol) {
        return null;
    }
}
