package bgu.spl.net.Msg;

import bgu.spl.net.api.MessagingProtocol;
import bgu.spl.net.srv.User;

public interface Message {

    //TODO: Remove this. Must be moved to protocol.
    //Means we must remove this interface entirely
    public Message process(User user);
}
