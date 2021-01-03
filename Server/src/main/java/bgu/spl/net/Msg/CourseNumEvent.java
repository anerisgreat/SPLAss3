package bgu.spl.net.Msg;

public class CourseNumEvent implements CtoSMessage{
    private short courseNum;

    public CourseNumEvent(short courseNum){
        this.courseNum = courseNum;
    }

    public short getCourseNum(){ return courseNum; }
}
