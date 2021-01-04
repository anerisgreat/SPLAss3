package bgu.spl.net.Msg;

public class Login implements CtoSMessage{
    private String userName;
    private String password;
    public Login(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
