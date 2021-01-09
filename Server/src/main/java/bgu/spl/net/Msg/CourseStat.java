package bgu.spl.net.Msg;

import bgu.spl.net.srv.User;

public class CourseStat extends absMsg{
    private short courseNum;
    public CourseStat(short courseNum) {
        this.courseNum = courseNum;
    }

    public short getCourseNum(){ return courseNum; }

    @Override
    public Message process(User user) {
        String msg = getDb().courseStat(courseNum, user.getUserName());
        if (msg == null) {
            return new Err(getOpCode());
        }
        return new Ack(getOpCode(), msg);
    }
}
