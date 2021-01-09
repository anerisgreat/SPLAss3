package bgu.spl.net.Msg;

import bgu.spl.net.srv.User;

public class IsRegistered extends absMsg{
    private short courseNum;
    public IsRegistered(short courseNum) {
        this.courseNum = courseNum;
    }

    public short getCourseNum(){ return courseNum; }

    //TODO: Remove
    @Override
    public Message process(User user) {
        if(getDb().isRegistered(courseNum, user.getUserName())) {
            return new Ack(getOpCode(), "REGISTERED");
        }
        return new Ack(getOpCode(), "NOT REGISTERED");
    }
}
