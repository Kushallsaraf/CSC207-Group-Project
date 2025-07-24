package Signup;

import UserAuthentication.Constants;
import UserAuthentication.PasswordHasher;
import UserAuthentication.UserDataHandler;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * A user will input a username and a password, we need to hash the password,
 * and store it in the json file.
 */
public class SignupUseCase {
    private UserDataHandler handler;

    public SignupUseCase(UserDataHandler handler) throws FileNotFoundException {
        this.handler = handler;


    }

    public String signUp(String username, String password) throws IOException {
        handler.addUser(username, PasswordHasher.hashPassword(password));
        return "success";

    }




}
