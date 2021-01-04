package bgu.spl.net.Msg;

public class StudentReg implements CtoSMessage{
    private String userName;
    private String password;
    public StudentReg(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
