package bgu.spl.net.Msg;

public class Login implements CtoSMessage{
    private String userName;
    private String password;

    public Login(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName(){ return userName; }
    public String getPassword(){ return password; }
    
    //TODO: Remove
    @Override
    public Message process(User user) {
        if(user.getLoggedIn()) {
            return new Err(getOpCode());
        }
        if (getDb().Login(userName, password)) {
            user.setLoggedIn(true);
            user.setUserName(userName);
            user.setShouldTerminate(false);
            return new Ack(getOpCode(),"");
        }
        return new Err(getOpCode());
    }
}
