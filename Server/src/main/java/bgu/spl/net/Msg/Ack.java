package bgu.spl.net.Msg;

public class Ack implements StoCMessage{
    private short msgOpCode;
    private String print;
    public Ack(short msgOpCode, String print) {
        this.msgOpCode = msgOpCode;
        this.print = print;
    }
}
