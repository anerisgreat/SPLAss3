package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.Msg.Message;
import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.api.MessagingProtocol;
import bgu.spl.net.srv.EventEncoderDecoder;
import bgu.spl.net.srv.MsgProtocol;
import bgu.spl.net.srv.Server;

import java.util.function.Supplier;

public class TPCMain {
    public static void main(String[] args) {

        Supplier<MessagingProtocol<Message>>  protocolSupplier = MsgProtocol::new;
        Supplier<MessageEncoderDecoder<Message>>  encoderDecoderSupplier = EventEncoderDecoder::new;
        if(args.length < 1) {
            throw new IllegalArgumentException("illegal argument");
        }
        int portNum = 0;
        try {
            portNum = Integer.parseInt(args[0]);
        } catch (Exception e) {
            throw new IllegalArgumentException("illegal argument");
        }
        Server.threadPerClient(portNum, protocolSupplier, encoderDecoderSupplier).serve();
    }
}
