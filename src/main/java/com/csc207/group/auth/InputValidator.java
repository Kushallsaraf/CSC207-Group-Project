package com.csc207.group.auth;

import com.csc207.group.util.Constants;

/**
 * Checks if given username and password is valid.
 */
public class InputValidator {

    public static final int MAX_USERNAME_LENGTH = 20;

    /**
     * Valids input by checking if username and password are valid.
     * @param username The username of the user.
     * @param password The password of the user.
     * @return Whether the input is valid or not.
     */
    public static String validateInput(String username, String password) {
        String result;

        if (!isUsernameValid(username) && !isPasswordValid(password)) {
            result = Constants.INVALID_INPUTS;

        }

        else if (!isUsernameValid(username)) {
            result = Constants.INVALID_USERNAME;
        }

        else if (!isPasswordValid(password)) {
            result = Constants.INVALID_PASSWORD;

        }

        else {
            result = Constants.SUCCESSFUL_SIGNUP;
        }
        return result;
    }

    private static boolean isUsernameValid(String username) {
        return username != null && !username.isEmpty()
                && username.length() <= MAX_USERNAME_LENGTH && !username.contains(" ");
    }

    private static boolean isPasswordValid(String password) {
        return password != null && !password.isEmpty()
                && password.length() <= MAX_USERNAME_LENGTH && !password.contains(" ");
    }
}
