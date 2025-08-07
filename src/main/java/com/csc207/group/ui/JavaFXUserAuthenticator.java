package com.csc207.group.ui;

import cache.FirebaseRestClient;
import com.csc207.group.data_access.FirebaseUserDataHandler;
import com.csc207.group.auth.*;
import com.csc207.group.app.GameCentralController;
import javafx.stage.Stage;
import com.csc207.group.ui.controller.JavaFXUserAuthenticationController;

public class JavaFXUserAuthenticator{

    private ui.JavaFXUserAuthenticationView view;
    private JavaFXUserAuthenticationController controller;
    private UserDataHandler dataHandler;
    private LoginPresenter loginPresenter;
    private LoginInteractor loginInteractor;
    private SignupInteractor signupInteractor;
    private SignupPresenter signupPresenter;
    private GameCentralController gameCentralController;


    public JavaFXUserAuthenticator(Stage stage, GameCentralController gameCentralController){
        view = new ui.JavaFXUserAuthenticationView(stage);
        this.gameCentralController = gameCentralController;
        gameCentralController.setUserAuthenticationView(view);


        FirebaseRestClient firebaseRestClient = new FirebaseRestClient();
        dataHandler = new FirebaseUserDataHandler(firebaseRestClient);
        loginPresenter = new LoginPresenter(view);
        signupPresenter = new SignupPresenter(view);
        loginInteractor = new LoginInteractor(dataHandler, loginPresenter);
        signupInteractor = new SignupInteractor(dataHandler, signupPresenter);
        controller = new JavaFXUserAuthenticationController(view, loginInteractor,
                signupInteractor, gameCentralController);
    }




    public void run() {

        gameCentralController.showUserAuthenticationView();
    }

}
