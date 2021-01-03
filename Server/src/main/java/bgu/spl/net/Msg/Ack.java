package bgu.spl.net.Msg;

public class Ack extends OpCodeEvent{
    private String print;
    public Ack(short msgOpCode, String print) {
        super(msgOpCode);
        this.print = print;
    }

    public String getPrint(){return this.print; }
}
