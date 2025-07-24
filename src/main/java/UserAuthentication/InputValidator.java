package UserAuthentication;

/**Checks if given username and password is valid
 *
 */
public class InputValidator {
    public static String validateInput(String username, String password){
        if (!isUsernameValid(username) && !isPasswordValid(password)){
            return Constants.INVALID_INPUTS;
        } else if (!isUsernameValid(username)) {
            return Constants.INVALID_USERNAME;


        }
        else if (!isPasswordValid(password)){
            return Constants.INVALID_PASSWORD;

        }

        else{
            return Constants.SUCCESSFUL_SIGNUP;
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
                password.length() >= 1 &&
                password.length() <= 20 &&
                !password.contains(" ");
    }
}
