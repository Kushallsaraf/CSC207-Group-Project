package User;

public class User {


    private String username;
    private String hashedPassword;
    private boolean isLoggedIn;

    public User(String username, String hashedPassword){
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.isLoggedIn = false;

    }
    public String getUsername(){
        return username;

    }

    public String getHashedPassword(){
        return hashedPassword;
    }

    public boolean isLoggedIn(){return this.isLoggedIn;}

    public void logIn(){
        this.isLoggedIn = true;

    }

    public void logOut(){
        this.isLoggedIn = false;
    }
}
