package bgu.spl.net.Msg;

import bgu.spl.net.srv.User;

public class Err extends absMsg {
    private short msgOpCode;
    public Err(short msgOpCode) {
        super((short)13);
        this.msgOpCode = msgOpCode;
    }

    @Override
    public Message process(User user) {
        return null;
    }
}
