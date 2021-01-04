package bgu.spl.net.Msg;

import bgu.spl.net.srv.User;

public class CourseReg extends absMsg{
    private int courseNum;
    public CourseReg(short courseNum) {
        super((short)5);
        this.courseNum = courseNum;
    }

    @Override
    public Message process(User user) {
        if(getDb().CourseReg(courseNum, user.getUserName())) {
            return new Ack(getOpCode(), "");
        }
        else {
            return new Err(getOpCode());
        }
    }
}
