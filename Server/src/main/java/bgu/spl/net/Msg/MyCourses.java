package bgu.spl.net.Msg;

import bgu.spl.net.srv.User;

public class MyCourses extends absMsg{
    public MyCourses() {}

    //TODO: Remove
    @Override
    public Message process(User user) {
        String ans = getDb().myCourses(user.getUserName());
        if (ans == null) {
            return new Err(getOpCode());
        }
        return new Ack(getOpCode(), ans);
    }
}
