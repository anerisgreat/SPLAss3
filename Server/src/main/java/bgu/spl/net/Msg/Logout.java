package bgu.spl.net.Msg;

import bgu.spl.net.srv.User;

public class Logout extends absMsg implements CtoSMessage{
    public Logout() {
        super((short)4);
    }

    @Override
    public Message process(User user) {
            getDb().logout(user.getUserName());
            user.setLoggedIn(false);
            user.setUserName(null);
            user.setShouldTerminate(true);
            return new Ack(getOpCode(), "");
    }
}
