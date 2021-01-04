package bgu.spl.net.Msg;

import bgu.spl.net.api.MessagingProtocol;
import bgu.spl.net.srv.User;

public interface Message {

    public Message process(User user);
    public short getOpCode();
}
