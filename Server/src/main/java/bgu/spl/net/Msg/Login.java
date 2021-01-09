package bgu.spl.net.Msg;

import bgu.spl.net.srv.MsgProtocol;

public class Login implements CtoSMessage{
    private String userName;
    private String password;

    public Login(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName(){ return userName; }
    public String getPassword(){ return password; }


    @Override
    public Message visit(MsgProtocol msgProtocol) {
        return msgProtocol.visit(this);
    }
}
