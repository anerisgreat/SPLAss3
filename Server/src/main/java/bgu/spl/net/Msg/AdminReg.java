package bgu.spl.net.Msg;

public class AdminReg implements CtoSMessage{
    public String userName;
    public String password;
    public AdminReg(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
