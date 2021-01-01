package bgu.spl.net.Msg;

public class UnRegister implements CtoSMessage{
    private short courseNum;
    public UnRegister(short courseNum) {
        this.courseNum = courseNum;
    }
}
