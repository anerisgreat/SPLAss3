package bgu.spl.net.Msg;

import bgu.spl.net.srv.User;

public class KdamCheck extends absMsg{
    private short courseNum;
    public  KdamCheck(short courseNum) {
        super((short)6);
        this.courseNum = courseNum;
    }

    @Override
    public Message process(User user) {
        String msg = getDb().kdamCheck(courseNum);
        if(msg != null){
            return new Ack(getOpCode(), msg);
        }
        return new Err(getOpCode());
    }
}
