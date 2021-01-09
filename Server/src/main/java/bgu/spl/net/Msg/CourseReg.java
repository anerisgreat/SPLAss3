package bgu.spl.net.Msg;

import bgu.spl.net.srv.User;

public class CourseReg extends absMsg{
    private short courseNum;
    public CourseReg(short courseNum) {
        this.courseNum = courseNum;
    }

    public short getCourseNum(){ return courseNum; } 

    //TODO: Remove
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
