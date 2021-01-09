package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.Msg.Message;
import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.api.MessagingProtocol;
import bgu.spl.net.srv.MsgProtocol;
import bgu.spl.net.srv.Reactor;

import java.util.function.Supplier;

public class ReactorMain {
    public static void main(String[] args) {

        Supplier<MessagingProtocol<Message>>  protocolSupplier = MsgProtocol::new;
        Supplier<MessageEncoderDecoder<Message>>  encoderDecoderSupplier = MsgEncoderDecoder::new;
        if(args.length < 2) {
            throw new IllegalArgumentException("illegal argument");
        }
        int portNum = 0;
        int numOfThreads = 0;
        try {
            portNum = Integer.parseInt(args[0]);
            numOfThreads = Integer.parseInt(args[1]);
        } catch (Exception e) {
            throw new IllegalArgumentException("illegal argument");
        }
        Reactor<Message> reactor = new Reactor<Message>(portNum, numOfThreads, protocolSupplier, encoderDecoderSupplier);
        reactor.serve();
    }
}
