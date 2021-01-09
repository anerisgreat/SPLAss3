package bgu.spl.net.Msg;

import bgu.spl.net.Db.Database;

//TODO: Remove
public abstract class absMsg implements Message{
    //Being that we will be removing all the functionality of this class, we must remove it entirely.
    private Database db;

    public absMsg() {
        //TODO: Remove
        db = Database.getInstance();
    }
    public absMsg() {
        //TODO: Remove
        db = Database.getInstance();
    }

    //TODO: Remove
    public Database getDb() {
        return db;
    }
}
