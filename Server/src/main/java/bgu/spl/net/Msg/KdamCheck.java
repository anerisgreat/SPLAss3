package bgu.spl.net.Msg;

public class KdamCheck implements CtoSMessage{
    private short courseNum;
    public  KdamCheck(short courseNum) {
        this.courseNum = courseNum;
    }
}
