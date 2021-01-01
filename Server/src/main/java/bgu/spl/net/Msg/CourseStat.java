package bgu.spl.net.Msg;

public class CourseStat implements CtoSMessage{
    private short courseNum;
    public CourseStat(short courseNum) {
        this.courseNum = courseNum;
    }
}
