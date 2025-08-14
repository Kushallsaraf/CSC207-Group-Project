package com.csc207.group.auth;

import com.csc207.group.util.Constants;

import java.io.IOException;

public class SignupInteractor {
    private UserRepository handler;
    private SignupPresenter presenter;

    public SignupInteractor(UserRepository handler, SignupPresenter presenter){
        this.handler = handler;
        this.presenter = presenter;
    }


    public void handleSignup(String usernameInput, String passwordInput) throws IOException {
        if (this.handler.usernameExists(usernameInput)){
            presenter.updateView(Constants.USERNAME_TAKEN);
        } else if (InputValidator.validateInput(usernameInput, passwordInput).equals(Constants.SUCCESSFUL_SIGNUP)) {
            handler.registerUser(usernameInput, PasswordHasher.hashPassword(passwordInput));
        }presenter.updateView(InputValidator.validateInput(usernameInput,passwordInput));



    }
}
