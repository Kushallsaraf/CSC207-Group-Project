package UserAuthentication;

/**Checks if given username and password is valid
 *
 */
public class InputValidator {
    public static String validateInput(String username, String password){
        if (!isUsernameValid(username) && !isPasswordValid(password)){
            return "usernames should be 1-20 characters long with no spaces in between characters\n" +
                    "passwords should be 10-20 characters long with no spaces in between";
        } else if (!isUsernameValid(username)) {
            return "usernames should be 1-20 characters long with no spaces in between characters";


        }
        else if (!isPasswordValid(password)){
            return "passwords should be 10-20 characters long with no spaces in between";

        }

        else{
            return "successfully signed up";
        }

    }
    private static boolean isUsernameValid(String username){
        return username != null &&
                username.length() >= 1 &&
                username.length() <= 20 &&
                !username.contains(" ");
    }

    private static boolean isPasswordValid(String password){
        return password != null &&
                password.length() >= 10 &&
                password.length() <= 20 &&
                !password.contains(" ");
    }
}
