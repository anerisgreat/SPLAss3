package bgu.spl.net.Msg;

public class StudentStat implements CtoSMessage{
    public String studentName;
    public  StudentStat(String studentName) {
        this.studentName = studentName;
    }
}
