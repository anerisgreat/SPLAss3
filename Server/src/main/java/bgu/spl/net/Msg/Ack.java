package bgu.spl.net.Msg;

import bgu.spl.net.srv.User;

public class Ack extends absMsg{
    private String print;
    private short msgOpCode;
    public Ack(short msgOpCode, String print) {
        super((short)12);
        this.msgOpCode = msgOpCode;
        this.print = print;
    }

    public String getPrint(){return this.print; }

    @Override
    public Message process(User user) {
        return null;
    }
}
