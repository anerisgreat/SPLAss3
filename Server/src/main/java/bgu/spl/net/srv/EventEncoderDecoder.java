package bgu.spl.net.srv;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Arrays;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.Msg.*;

public class EventEncoderDecoder implements MessageEncoderDecoder<CtoSMessage> {

    private enum fieldType{
        stringfield,
        shortfield
    }

    static class MessageFieldInfo{
        public Class MessageType;
        public fieldType[] Fields;

        public MessageFieldInfo(Class p_messageType, fieldType[] p_fields){
            MessageType = p_messageType;
            Fields = p_fields;
        }
    }

    static class EncoderHelper{
        //Encoder fields
        private byte[] encoderBuff;
        private short currEncoderIndex;

        public EncoderHelper(){
            encoderBuff = new byte[128];
            currEncoderIndex = 0;
        }

        public void encode(Short p_short){
            encoderBuff[currEncoderIndex] = (byte)((p_short & 0xFF00) >> 8);
            encoderBuff[currEncoderIndex + 1] = (byte)(p_short & 0xFF);
            currEncoderIndex += 2;
        }

        public void encode(String p_string){
            for(int i = 0; i < p_string.length(); ++i){
                encoderBuff[currEncoderIndex] = (byte)(p_string.charAt(i));
                currEncoderIndex++;
            }
            encoderBuff[currEncoderIndex] = 0;
            currEncoderIndex++;
        }

        public byte[] getArr(){
            return Arrays.copyOfRange(encoderBuff, 0, currEncoderIndex);
        }
    }

    private static Map<Short, MessageFieldInfo> opCodeToEventType;

    private static Map<Class, Short> eventTypeToOpCode;

    static{
        opCodeToEventType = new HashMap<Short, MessageFieldInfo>();
        opCodeToEventType.put((short)1, new MessageFieldInfo(AdminReg.class,
                                                             new fieldType[]{
                                                                 fieldType.stringfield,
                                                                 fieldType.stringfield}));

        opCodeToEventType.put((short)2, new MessageFieldInfo(StudentReg.class,
                                                             new fieldType[]{
                                                                 fieldType.stringfield,
                                                                 fieldType.stringfield}));

        opCodeToEventType.put((short)3, new MessageFieldInfo(Login.class,
                                                             new fieldType[]{
                                                                 fieldType.stringfield,
                                                                 fieldType.stringfield}));

        opCodeToEventType.put((short)4, new MessageFieldInfo(Logout.class, new fieldType[]{}));

        opCodeToEventType.put((short)5, new MessageFieldInfo(CourseReg.class,
                                                             new fieldType[]{fieldType.shortfield}));

        opCodeToEventType.put((short)6, new MessageFieldInfo(KdamCheck.class,
                                                             new fieldType[]{fieldType.shortfield}));

        opCodeToEventType.put((short)7, new MessageFieldInfo(CourseStat.class,
                                                             new fieldType[]{fieldType.shortfield}));

        opCodeToEventType.put((short)8, new MessageFieldInfo(StudentStat.class,
                                                             new fieldType[]{fieldType.stringfield}));

        opCodeToEventType.put((short)9, new MessageFieldInfo(IsRegistered.class,
                                                             new fieldType[]{fieldType.shortfield}));

        opCodeToEventType.put((short)10, new MessageFieldInfo(UnRegister.class,
                                                             new fieldType[]{fieldType.shortfield}));

        opCodeToEventType.put((short)11, new MessageFieldInfo(MyCourses.class,
                                                             new fieldType[]{}));

        opCodeToEventType.put((short)12, new MessageFieldInfo(Ack.class,
                                                             new fieldType[]{
                                                                 fieldType.shortfield,
                                                                 fieldType.stringfield}));

        opCodeToEventType.put((short)13, new MessageFieldInfo(Err.class,
                                                             new fieldType[]{fieldType.shortfield}));

        eventTypeToOpCode = new HashMap<Class, Short>();
        eventTypeToOpCode.put(AdminReg.class, (short)1);
        eventTypeToOpCode.put(StudentReg.class, (short)2);
        eventTypeToOpCode.put(Login.class, (short)3);
        eventTypeToOpCode.put(Logout.class, (short)4);
        eventTypeToOpCode.put(CourseReg.class, (short)5);
        eventTypeToOpCode.put(KdamCheck.class, (short)6);
        eventTypeToOpCode.put(CourseStat.class ,(short)7);
        eventTypeToOpCode.put(StudentStat.class, (short)8);
        eventTypeToOpCode.put(IsRegistered.class, (short)9);
        eventTypeToOpCode.put(UnRegister.class, (short)10);
        eventTypeToOpCode.put(MyCourses.class, (short)11);
        eventTypeToOpCode.put(Ack.class, (short)12);
        eventTypeToOpCode.put(Err.class, (short)13);
    }

    //Decoder fields
    private byte[] byteArr;
    private short buffIndex;
    private short fieldIndex;
    private short indexInField;
    private short fieldStartIndex;
    private short currentOpCode;
    private Queue<String> stringValQueue;
    private Queue<Short> shortValQueue;

    private static short byteArrAtIndexToShort(byte[] p_byteArr, int p_index){
        return (short)(((short)p_byteArr[p_index]<<8) + (short)p_byteArr[p_index + 1]);
    }

    private static String byteArrAtIndexToString(byte[] p_byteArr, int p_index, int p_len){
        return new String(p_byteArr, p_index, p_len);
    }

    @Override
    public CtoSMessage decodeNextByte(byte nextByte) {
        /*

        this.byteArr = new byte[256];
        this.buffIndex = 0;
        this.fieldIndex = 0;
        this.indexInField = 0;
         */

        //Init with null. We will return this at the end.
        CtoSMessage ret = null;

        //Copy byte into array
        this.byteArr[buffIndex] = nextByte;
        this.buffIndex++;
        this.indexInField++;

        //Finished receiving opcode
        if(this.fieldIndex == -1 && this.indexInField == 2){
            //Get current op code.
            this.currentOpCode = byteArrAtIndexToShort(this.byteArr, 0);
            this.indexInField = 0;
            this.stringValQueue.clear();
            this.shortValQueue.clear();
            this.fieldStartIndex = buffIndex;

            //Check if opcode exists. If not, corrupted. Will reset to normal state,
            //even though exception will be thrown.
            if(!opCodeToEventType.containsKey(this.currentOpCode)){
                this.fieldIndex = -1;
                this.buffIndex = 0;
            }
            else{
                this.fieldIndex = 0;
            }
        }
        //Finished receiving short.
        else if((this.opCodeToEventType.get(this.currentOpCode).Fields[this.fieldIndex] == fieldType.shortfield)
                && (this.indexInField == 2))
        {
            this.shortValQueue.add(byteArrAtIndexToShort(this.byteArr, this.fieldStartIndex));
            
            //Prepare field start for next field.
            this.fieldIndex++;
            this.indexInField = 0;
            this.fieldStartIndex = this.buffIndex;
        }
        //Finished receiving string
        else if((this.opCodeToEventType.get(this.currentOpCode).Fields[this.fieldIndex] == fieldType.stringfield)
                && (nextByte == 0))
        {
            this.stringValQueue.add(byteArrAtIndexToString(this.byteArr,
                                                            this.fieldStartIndex,
                                                            this.indexInField - 1));
            //Prepare field start for next field.
            this.fieldIndex++;
            this.indexInField = 0;
            this.fieldStartIndex = this.buffIndex;
        }

        //Received last field. Time to construct message.
        if(this.fieldIndex == this.opCodeToEventType.get(this.currentOpCode).Fields.length){
            this.fieldIndex = -1;
            this.buffIndex = 0;

            String str1 = "";
            String str2 = "";
            Short shr1 = -1;
            Short shr2 = -1;

            //Retrieving from queues
            if(stringValQueue.peek() != null)
                str1 = stringValQueue.poll();
            if(stringValQueue.peek() != null)
                str2 = stringValQueue.poll();
            if(shortValQueue.peek() != null)
                shr1 = shortValQueue.poll();
            if(shortValQueue.peek() != null)
                shr2 = shortValQueue.poll();

            //Creating instance
            switch(this.currentOpCode){
            case 1: ret = new AdminReg(str1, str2); break;
            case 2: ret = new StudentReg(str1, str2); break;
            case 3: ret = new Login(str1, str2); break;
            case 4: ret = new Logout(); break;
            case 5: ret = new CourseReg(shr1); break;
            case 6: ret = new KdamCheck(shr1); break;
            case 7: ret = new CourseStat(shr1); break;
            case 8: ret = new StudentStat(str1); break;
            case 9: ret = new IsRegistered(shr1); break;
            case 10: ret = new UnRegister(shr1); break;
            case 11: ret = new MyCourses(); break;
            case 12: ret = new Ack(shr1, str1); break;
            case 13: ret = new Err(shr1); break;
            default: ret = new Err((short)-1); break;
            }
        }
        
        return ret;
    }

    @Override
    public byte[] encode(CtoSMessage message) {
        EncoderHelper h = new EncoderHelper();
        h.encode(eventTypeToOpCode.get(h.getClass()));

        if(message instanceof AdminReg){
            AdminReg m = (AdminReg)message;
            h.encode(m.getUserName());
            h.encode(m.getPassword());
        }
        else if(message instanceof StudentReg){
            StudentReg m = (StudentReg)message;
            h.encode(m.getUserName());
            h.encode(m.getPassword());
        }
        else if(message instanceof Login){
            Login m = (Login)message;
            h.encode(m.getUserName());
            h.encode(m.getPassword());
        }
        else if(message instanceof Logout){
            //No fields
        }
        else if(message instanceof CourseReg){
            CourseReg m = (CourseReg)message;
            h.encode(m.getCourseNum());
        }
        else if(message instanceof KdamCheck){
            KdamCheck m = (KdamCheck)message;
            h.encode(m.getCourseNum());
        }
        else if(message instanceof CourseStat){
            CourseStat m = (CourseStat)message;
            h.encode(m.getCourseNum());
        }
        else if(message instanceof StudentStat){
            StudentStat m = (StudentStat)message;
            h.encode(m.getStudentName());
        }
        else if(message instanceof IsRegistered){
            IsRegistered m = (IsRegistered)message;
            h.encode(m.getCourseNum());
        }
        else if(message instanceof UnRegister){
            UnRegister m = (UnRegister)message;
            h.encode(m.getCourseNum());
        }
        else if(message instanceof MyCourses){
            //No fields
        }
        else if(message instanceof Ack){
            Ack m = (Ack)message;
            h.encode(m.getOpCode());
            h.encode(m.getPrint());
        }
        else if(message instanceof Err){
            Err m = (Err)message;
            h.encode(m.getOpCode());
        }
                                            
        return h.getArr();
    }

    public EventEncoderDecoder(){
        this.byteArr = new byte[256];
        this.buffIndex = 0;
        //We use index -1 to indicate opcode field, so this should be expecting opcode on start.
        this.fieldIndex = -1;
        this.indexInField = 0;
        this.currentOpCode = -1;
        this.fieldStartIndex = 0;
        this.stringValQueue = new LinkedList<String>();
        this.shortValQueue = new LinkedList<Short>();
    }
}
