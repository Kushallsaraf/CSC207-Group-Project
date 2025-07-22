package com.csc207.group;

import Login.LoginInteractor;
import UserAuthentication.Constants;
import UserAuthentication.UserAuthenticationController;
import Signup.SignupInteractor;
import UserAuthentication.UserDataHandler;

import java.io.FileNotFoundException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        UserDataHandler handler = new UserDataHandler(Constants.FILE_PATH_ONE);
        SignupInteractor signupInteractor = new SignupInteractor(handler);
        LoginInteractor loginInteractor = new LoginInteractor(handler);
        UserAuthenticationController userAuthenticationController = new UserAuthenticationController(signupInteractor, loginInteractor);
        // TODO: PRIORITY 1: Decouple controller, have quasi main application to verify login [session manager]
        // TODO 1: impose restrictions on password and username inputs
        // TODO 2: Implement login functionality
        // TODO 7: Replace Swing with JavaFX
        // TODO 8: To make 7 smoother, it is best to modify the architecture to make the controller independent of GUI
        // Todo 3: If a username is taken, a message should be displayed (the program makes sure duplicate users are not added, but the message is for the user's convienience)
        // Todo 4: It is best practice to decouple launching the UI from the controller
        // Todo 5: Implement logout functionality
        // Todo 6: When Login/Signup is fully operational, it should be decided how user authentication works in the broader sense of the app
        // TODO 9: Organize packages and files in a more appropriate manner

    }
}