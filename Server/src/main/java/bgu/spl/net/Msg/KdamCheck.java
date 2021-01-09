package bgu.spl.net.Msg;

import bgu.spl.net.srv.MsgProtocol;
import bgu.spl.net.srv.User;

public class KdamCheck implements Message{
    private short courseNum;
    public  KdamCheck(short courseNum) {
        this.courseNum = courseNum;
    }

    public short getCourseNum(){ return courseNum; }

    @Override
    public Message visit(MsgProtocol msgProtocol) {
        return null;
    }
}
