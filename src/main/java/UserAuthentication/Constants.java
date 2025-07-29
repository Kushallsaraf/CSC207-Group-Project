package UserAuthentication;

public class Constants {

    public static final String INVALID_USERNAME = "usernames should be 1-20 characters long with no spaces in " +
            "between characters";

    public static final String INVALID_PASSWORD = "passwords should be 1-20 characters long with no spaces in between";

    public static final String INVALID_INPUTS = "usernames should be 1-20 characters long with no spaces in between " +
            "characters\n" + "passwords should be 1-20 characters long with no spaces in between";

    public static final String SUCCESSFUL_SIGNUP = "Successfully signed up. Please renter username and password";

    public static final String USERNAME_TAKEN = "this username is already taken";

    public static final String FAILED_LOGIN_USERNAME = "User does not exist";

    public static final String FAILED_LOGIN_PASSWORD = "incorrect password";

    public static final String SUCCESSFUL_LOGIN = "login successful";

    public static final String FILE_PATH = "src/main/java/UserAuthentication/JSON/users.json";
}
