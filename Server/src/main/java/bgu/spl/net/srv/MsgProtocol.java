package bgu.spl.net.srv;

import bgu.spl.net.Msg.CtoSMessage;
import bgu.spl.net.Msg.Err;
import bgu.spl.net.Msg.Message;
import bgu.spl.net.api.MessagingProtocol;

public class MsgProtocol implements MessagingProtocol<Message> {

    private User user;

    public MsgProtocol() {
        user = new User();
    }

    @Override
    public Message process(Message msg) {
        if(!user.getLoggedIn()){
            if(msg.getOpCode() == 1) {
                return msg.process(user);
            }
            else return new Err(msg.getOpCode());
        }
        else {
            return msg.process(user);
        }
    }

    @Override
    public boolean shouldTerminate() {
        return false;
    }

}
