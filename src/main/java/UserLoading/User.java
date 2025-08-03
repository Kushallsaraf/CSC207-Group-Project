package UserLoading;

public class User {

    private String username;
    private String hashedPassword;

    public User(String username, String hashedPassword){
        this.hashedPassword = hashedPassword;
        this.username = username;

    }
    public String getUsername() {
        return username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setReviews() {
        
    }
}
