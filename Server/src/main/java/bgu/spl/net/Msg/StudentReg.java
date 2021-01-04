package bgu.spl.net.Msg;

import bgu.spl.net.srv.User;

public class StudentReg extends absMsg implements CtoSMessage{
    private String userName;
    private String password;
    public StudentReg(String userName, String password) {
        super((short)2);
        this.userName = userName;
        this.password = password;
    }

    @Override
    public Message process(User user) {
        if (getDb().studentReg(userName, password)) {
            return new Ack(getOpCode(),"");
        }
        return new Err(getOpCode());
    }
}
