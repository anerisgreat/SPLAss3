package bgu.spl.net.Msg;

public class Err implements StoCMessage{
    private short msgOpCode;
    public Err(short msgOpCode) {
        this.msgOpCode = msgOpCode;
    }
    public short getMsgOpCode(){return this.msgOpCode; }
}
