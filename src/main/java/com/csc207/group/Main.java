package com.csc207.group;

import Signup.SignupController;
import Signup.SignupInteractor;

import java.io.FileNotFoundException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        SignupInteractor signupInteractor = new SignupInteractor();
        SignupController signupController = new SignupController(signupInteractor);
        // TODO 1: Add a delete_user feature UserDataHandler
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