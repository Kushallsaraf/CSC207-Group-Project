package Signup;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * A user will input a username and a password, we need to hash the password,
 * and store it in the json file.
 */
public class SignupUseCase {
    private UserDataHandler handler;

    public SignupUseCase() throws FileNotFoundException {
        this.handler = new UserDataHandler(Constants.FILE_PATH_ONE);


    }

    public String signUp(String username, String password) throws IOException {
        handler.addUser(username, PasswordHasher.hashPassword(password));
        return "success";

    }




}
