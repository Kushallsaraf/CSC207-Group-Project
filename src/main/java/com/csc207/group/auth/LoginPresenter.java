package com.csc207.group.auth;

import ui.UserAuthenticationView;

public class LoginPresenter {
    private UserAuthenticationView view;


    public LoginPresenter(UserAuthenticationView view){
        this.view = view;
    }
    public void updateView(String message) {
        this.view.updateMessageView(message);
    }
}
