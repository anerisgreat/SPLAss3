package bgu.spl.net.Msg;

public class IsRegistered implements CtoSMessage{
    private short courseNum;
    public IsRegistered(short courseNum) {
        this.courseNum = courseNum;
    }
}
