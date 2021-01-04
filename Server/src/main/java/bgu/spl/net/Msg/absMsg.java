package bgu.spl.net.Msg;

import bgu.spl.net.Db.Database;

public abstract class absMsg implements Message{
    private short msgOpCode;
    private Database db;

    public absMsg(short msgOpCode) {
        this.msgOpCode = msgOpCode;
        db = Database.getInstance();
    }
    public absMsg() {
        msgOpCode = 0;
        db = Database.getInstance();
    }

    public Database getDb() {
        return db;
    }

    @Override
    public short getOpCode() {
        return msgOpCode;
    }
}
