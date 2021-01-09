package bgu.spl.net.Msg;

import bgu.spl.net.srv.User;

public class KdamCheck extends absMsg{
    private short courseNum;
    public  KdamCheck(short courseNum) {
        this.courseNum = courseNum;
    }

    public short getCourseNum(){ return courseNum; }

    //TODO: Remove
    @Override
    public Message process(User user) {
        String msg = getDb().kdamCheck(courseNum);
        if(msg != null){
            return new Ack(getOpCode(), msg);
        }
        return new Err(getOpCode());
    }
}
