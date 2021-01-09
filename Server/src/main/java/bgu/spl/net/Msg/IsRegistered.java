package bgu.spl.net.Msg;

import bgu.spl.net.srv.MsgProtocol;
import bgu.spl.net.srv.User;

public class IsRegistered implements CtoSMessage{
    private short courseNum;
    public IsRegistered(short courseNum) {
        super();
        this.courseNum = courseNum;
    }

    public short getCourseNum(){ return courseNum; }

    @Override
    public Message visit(MsgProtocol msgProtocol) {
        return msgProtocol.visit(this);
    }
}
