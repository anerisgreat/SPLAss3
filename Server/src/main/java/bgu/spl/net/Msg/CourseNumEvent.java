package bgu.spl.net.Msg;

public class CourseNumEvent implements CtoSMessage{
    public short courseNum;

    public CourseNumEvent(short courseNum){
        this.courseNum = courseNum;
    }
}
