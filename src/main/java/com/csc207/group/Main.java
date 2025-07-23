package com.csc207.group;


import UserAuthentication.*;

import java.io.FileNotFoundException;
import java.lang.constant.Constable;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        SwingUserAuthenticationView view = new SwingUserAuthenticationView();
        JSONUserDataHandler dataHandler = new JSONUserDataHandler(Constants.FILE_PATH);
        LoginPresenter loginPresenter = new LoginPresenter(view);
        SignupPresenter signupPresenter = new SignupPresenter(view);
        LoginInteractor loginInteractor = new LoginInteractor(dataHandler, loginPresenter);
        SignupInteractor signupInteractor = new SignupInteractor(dataHandler, signupPresenter);
        SwingUserAuthenticationController controller = new SwingUserAuthenticationController(view, loginInteractor,
                signupInteractor);


        view.display();





    }
}