package bgu.spl.net.Msg;

import bgu.spl.net.srv.MsgProtocol;
import bgu.spl.net.srv.User;

public class Err implements StoCMessage {
    private short msgOpCode;
    public Err(short msgOpCode) {
        this.msgOpCode = msgOpCode;
    }

    public short getOpCode(){ return msgOpCode; }

    @Override
    public Message visit(MsgProtocol msgProtocol) {
        return null;
    }
}
