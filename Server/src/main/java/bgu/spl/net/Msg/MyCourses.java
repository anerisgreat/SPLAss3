package bgu.spl.net.Msg;

import bgu.spl.net.srv.User;

public class MyCourses extends absMsg{
    public MyCourses() {
        super((short)11);
    }

    @Override
    public Message process(User user) {
        return new Ack(getOpCode(), getDb().myCourses(user.getUserName()));
    }
}
