package bgu.spl.net.Msg;

import bgu.spl.net.srv.User;

public class CourseStat extends absMsg{
    private short courseNum;
    public CourseStat(short courseNum) {
        super((short)7);
        this.courseNum = courseNum;
    }

    @Override
    public Message process(User user) {
        String msg = getDb().courseStat(courseNum);
        if (msg == null) {
            return new Err(getOpCode());
        }
        return new Ack(getOpCode(), msg);
    }
}
