package bgu.spl.net.Msg;

public class CourseReg implements CtoSMessage{
    private short courseNum;
    public CourseReg(short courseNum) {
        this.courseNum = courseNum;
    }
}
