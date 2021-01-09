package bgu.spl.net.Msg;

import bgu.spl.net.srv.MsgProtocol;
import bgu.spl.net.srv.User;

public class MyCourses implements CtoSMessage{
    public MyCourses() {}


    @Override
    public Message visit(MsgProtocol msgProtocol) {
       return msgProtocol.visit(this);
    }
}
