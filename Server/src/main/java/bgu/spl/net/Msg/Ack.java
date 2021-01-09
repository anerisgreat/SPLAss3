package bgu.spl.net.Msg;

public class Ack implements CtoSMessage{
    private String print;
    private short opCode;

    public Ack(short msgOpCode, String print) {
        this.print = print;
        this.opCode = msgOpCode;
    }

    public String getPrint(){return this.print; }
    public short getOpCode(){ return this.opCode; }

    //TODO: Remove
    @Override
    public Message process(User user) {
        return null;
    }
}
