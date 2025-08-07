package com.csc207.group.auth;

import ui.UserAuthenticationView;

public class SignupPresenter {

    private UserAuthenticationView view;

    public SignupPresenter(UserAuthenticationView view){
        this.view = view;
    }
    public void updateView(String message) {
        view.updateMessageView(message);
    }
}
