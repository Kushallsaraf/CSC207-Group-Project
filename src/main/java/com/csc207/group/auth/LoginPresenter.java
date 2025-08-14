package com.csc207.group.auth;

import com.csc207.group.ui.UserAuthenticationView;

public class LoginPresenter {
    private UserAuthenticationView view;

    public LoginPresenter(UserAuthenticationView view) {
        this.view = view;
    }

    /**
     * Updates the view.
     * @param message the message that should be shown when view is updated.
     */
    public void updateView(String message) {
        this.view.updateMessageView(message);
    }
}
