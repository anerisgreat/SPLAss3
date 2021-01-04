package bgu.spl.net.Msg;

import bgu.spl.net.srv.User;

public class UnRegister extends absMsg{
    private short courseNum;
    public UnRegister(short courseNum) {
        super((short)10);
        this.courseNum = courseNum;
    }

    @Override
    public Message process(User user) {
        if(getDb().unRegister(courseNum, user.getUserName())){
            return new Ack(getOpCode(), "");
        }
        return new Err(getOpCode());
    }
}
