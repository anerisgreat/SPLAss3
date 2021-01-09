package bgu.spl.net.Msg;

import bgu.spl.net.api.MessagingProtocol;
import bgu.spl.net.srv.MsgProtocol;
import bgu.spl.net.srv.User;

public interface Message {
    Message visit(MsgProtocol msgProtocol);
}
