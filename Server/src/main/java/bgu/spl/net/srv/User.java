package bgu.spl.net.srv;

public class User {
    private String userName;
    private boolean isLoggedIn;

    public User() {
        userName = null;
        isLoggedIn = false;
    }

    public boolean getLoggedIn() {
        return isLoggedIn;
    }

    public String getUserName() {
        return userName;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
