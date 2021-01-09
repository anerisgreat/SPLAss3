package bgu.spl.net.Msg;

import bgu.spl.net.srv.User;

public class StudentReg extends absMsg implements CtoSMessage{
    private String userName;
    private String password;
    public StudentReg(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName(){ return userName; }
    public String getPassword(){ return password; }

    //TODO: Remove
    @Override
    public Message process(User user) {
        if (getDb().studentReg(userName, password)) {
            return new Ack(getOpCode(),"");
        }
        return new Err(getOpCode());
    }
}
