package com.csc207.group.ui;

import com.csc207.group.cache.FirebaseRestClient;
import com.csc207.group.data_access.FirebaseUserRepository;
import com.csc207.group.auth.*;
import com.csc207.group.app.GameCentralController;
import javafx.stage.Stage;
import com.csc207.group.ui.controller.JavaFxUserAuthenticationController;
import org.w3c.dom.UserDataHandler;

public class JavaFxUserAuthenticator {

    private JavaFXUserAuthenticationView view;
    private JavaFxUserAuthenticationController controller;
    private FirebaseUserRepository dataHandler;
    private LoginPresenter loginPresenter;
    private LoginInteractor loginInteractor;
    private SignupInteractor signupInteractor;
    private SignupPresenter signupPresenter;
    private GameCentralController gameCentralController;


    public JavaFxUserAuthenticator(Stage stage, GameCentralController gameCentralController){
        view = new JavaFXUserAuthenticationView(stage);
        this.gameCentralController = gameCentralController;
        gameCentralController.setUserAuthenticationView(view);


        FirebaseRestClient firebaseRestClient = new FirebaseRestClient();
        dataHandler = new FirebaseUserRepository(firebaseRestClient);
        loginPresenter = new LoginPresenter(view);
        signupPresenter = new SignupPresenter(view);
        loginInteractor = new LoginInteractor(dataHandler, loginPresenter);
        signupInteractor = new SignupInteractor(dataHandler, signupPresenter);
        controller = new JavaFxUserAuthenticationController(view, loginInteractor,
                signupInteractor, gameCentralController);
    }

    public void run() {

        gameCentralController.showUserAuthenticationView();
    }

}
