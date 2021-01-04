package bgu.spl.net.Msg;

import bgu.spl.net.srv.User;

public class Logout extends absMsg implements CtoSMessage{
    public Logout() {
        super((short)4);
    }

    @Override
    public Message process(User user) {
        if(getDb().Logout(user.getUserName())) {
            user.setLoggedIn(false);
            user.setUserName(null);
            return new Ack(getOpCode(), "");
        }
        return new Err(getOpCode());
    }
}
