package com.csc207.group.auth;

import com.csc207.group.model.User;
import com.csc207.group.util.Constants;
import org.w3c.dom.UserDataHandler;

public class LoginInteractor {
    private UserRepository handler;
    private LoginPresenter presenter;

    public LoginInteractor(UserRepository handler, LoginPresenter presenter) {
        this.handler = handler;
        this.presenter = presenter;
    }

    public boolean handleLogin(String usernameInput, String passwordInput) {
        if (!handler.usernameExists(usernameInput)) {
            presenter.updateView(Constants.FAILED_LOGIN_USERNAME);
            return false;

        }
        else if (!PasswordHasher.checkPassword(passwordInput, handler.getUser(usernameInput).getHashedPassword())) {
            presenter.updateView(Constants.FAILED_LOGIN_PASSWORD);
            return false;

        }
        presenter.updateView(Constants.SUCCESSFUL_LOGIN);
        return true;

    }

    public User getUser(String username){
        return this.handler.getUser(username);
    }
}
