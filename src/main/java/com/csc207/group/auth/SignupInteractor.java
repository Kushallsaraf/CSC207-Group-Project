package com.csc207.group.auth;

import java.io.IOException;

import com.csc207.group.util.Constants;
import org.w3c.dom.UserDataHandler;

public class SignupInteractor {
    private UserRepository handler;
    private SignupPresenter presenter;

    public SignupInteractor(UserRepository handler, SignupPresenter presenter) {
        this.handler = handler;
        this.presenter = presenter;
    }

    /**
     * Handles the signup process by validating input, checking if user exits, and registering.
     * @param usernameInput The input for username
     * @param passwordInput The input for password.
     * @throws IOException if there is an error accessing user data.
     */
    public void handleSignup(String usernameInput, String passwordInput) throws IOException {
        if (this.handler.usernameExists(usernameInput)) {
            presenter.updateView(Constants.USERNAME_TAKEN);
        }
        else if (InputValidator.validateInput(usernameInput, passwordInput).equals(Constants.SUCCESSFUL_SIGNUP)) {
            handler.registerUser(usernameInput, PasswordHasher.hashPassword(passwordInput));
        }
        presenter.updateView(InputValidator.validateInput(usernameInput, passwordInput));
    }
}
