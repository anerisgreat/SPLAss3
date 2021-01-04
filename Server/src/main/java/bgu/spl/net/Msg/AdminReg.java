package bgu.spl.net.Msg;

public class AdminReg implements CtoSMessage{
    private String userName;
    private String password;
    public AdminReg(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
