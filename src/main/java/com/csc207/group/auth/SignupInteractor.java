package com.csc207.group.auth;

import util.Constants;

import java.io.IOException;

public class SignupInteractor {
    private UserDataHandler handler;
    private SignupPresenter presenter;

    public SignupInteractor(UserDataHandler handler, SignupPresenter presenter){
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
