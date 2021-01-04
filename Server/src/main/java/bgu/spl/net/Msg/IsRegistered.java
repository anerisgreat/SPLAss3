package bgu.spl.net.Msg;

import bgu.spl.net.srv.User;

public class IsRegistered extends absMsg{
    private short courseNum;
    public IsRegistered(short courseNum) {
        super((short)9);
        this.courseNum = courseNum;
    }

    @Override
    public Message process(User user) {
        if(getDb().isRegistered(courseNum, user.getUserName())) {
            return new Ack(getOpCode(), "REGISTERED");
        }
        return new Ack(getOpCode(), "NOT REGISTERED");
    }
}
