package bgu.spl.net.Msg;

import bgu.spl.net.srv.User;

public class MyCourses extends absMsg{
    public MyCourses() {
        super((short)11);
    }

    @Override
    public Message process(User user) {
        String ans = getDb().myCourses(user.getUserName());
        if (ans == null) {
            return new Err(getOpCode());
        }
        return new Ack(getOpCode(), ans);
    }
}
