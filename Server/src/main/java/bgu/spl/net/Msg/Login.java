package bgu.spl.net.Msg;

public class Login implements CtoSMessage{
    public String userName;
    public String password;
    public Login(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
