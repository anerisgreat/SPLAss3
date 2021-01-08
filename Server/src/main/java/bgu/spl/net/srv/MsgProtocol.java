package bgu.spl.net.srv;

import bgu.spl.net.Msg.CtoSMessage;
import bgu.spl.net.Msg.Err;
import bgu.spl.net.Msg.Message;
import bgu.spl.net.api.MessagingProtocol;

public class MsgProtocol implements MessagingProtocol<Message> {

    private User user; //stores details of the user which has this instance of MsgProtocol in his connection handler


    public MsgProtocol() {
        user = new User();
    }

    @Override
    public Message process(Message msg) {
        if(!user.getLoggedIn()){
            //login msg (OpCode = 1) is the only one acceptable before logging in
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
        return user.getShouldTerminate();
    }

}
