package bgu.spl.net.Msg;

import bgu.spl.net.srv.User;

public class Login extends absMsg implements CtoSMessage{
    private String userName;
    private String password;
    public Login(String userName, String password) {
        super((short)3);
        this.userName = userName;
        this.password = password;
    }

    @Override
    public Message process(User user) {
        if(user.getLoggedIn()) {
            return new Err(getOpCode());
        }
        if (getDb().Login(userName, password)) {
            user.setLoggedIn(true);
            user.setUserName(userName);
            return new Ack(getOpCode(),"");
        }
        return new Err(getOpCode());
    }
}
