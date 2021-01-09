package bgu.spl.net.Msg;

import bgu.spl.net.srv.MsgProtocol;
import bgu.spl.net.srv.User;

public class Ack implements StoCMessage{
    private String print;
    private short opCode;

    public Ack(short msgOpCode, String print) {
        this.print = print;
        this.opCode = msgOpCode;
    }

    public String getPrint(){return this.print; }
    public short getOpCode(){ return this.opCode; }

    @Override
    public Message visit(MsgProtocol msgProtocol) {
        return null;
    }
}
