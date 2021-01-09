package bgu.spl.net.Msg;

import bgu.spl.net.srv.User;

public class Ack extends absMsg{
    private String print;
    public Ack(String print) {
        super();
        this.print = print;
    }

    public String getPrint(){return this.print; }

    //TODO: Remove
    @Override
    public Message process(User user) {
        return null;
    }
}
