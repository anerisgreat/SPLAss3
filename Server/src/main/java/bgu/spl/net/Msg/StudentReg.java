package bgu.spl.net.Msg;

public class StudentReg implements CtoSMessage{
    public String userName;
    public String password;
    public StudentReg(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
