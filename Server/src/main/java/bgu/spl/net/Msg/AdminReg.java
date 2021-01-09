package bgu.spl.net.Msg;

public class AdminReg implements CtoSMessage{
    private String userName;
    private String password;

    public AdminReg(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName(){ return userName; }
    public String getPassword(){ return password; }

    //TODO: Remove
    @Override
    public Message process(User user) {
        if (getDb().adminReg(userName, password)) {
            return new Ack(getOpCode(), "");
        }
        return new Err(getOpCode());
    }
}
