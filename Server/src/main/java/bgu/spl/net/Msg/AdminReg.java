package bgu.spl.net.Msg;

import bgu.spl.net.Db.Database;
import bgu.spl.net.api.MessagingProtocol;
import bgu.spl.net.srv.User;

public class AdminReg extends absMsg{
    private String userName;
    private String password;

    public AdminReg(String userName, String password) {
        super((short) 1);
        this.userName = userName;
        this.password = password;
    }

    @Override
    public Message process(User user) {
        if (getDb().adminReg(userName, password)) {
            return new Ack(getOpCode(), "");
        }
        return new Err(getOpCode());
    }
}
