package bgu.spl.net.Msg;

public class OpCodeEvent implements CtoSMessage{
    private short opCode;

    public OpCodeEvent(short opCode){
        this.opCode = opCode;
    }

    public short getOpCode(){ return this.opCode; }
}
