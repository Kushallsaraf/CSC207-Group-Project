package com.csc207.group.ui.controller;

import com.csc207.group.auth.LoginInteractor;
import com.csc207.group.auth.SignupInteractor;
import com.csc207.group.app.GameCentralController;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import com.csc207.group.ui.JavaFXUserAuthenticationView;

import java.io.IOException;

public class JavaFXUserAuthenticationController {

    private JavaFXUserAuthenticationView view;
    private LoginInteractor loginInteractor;
    private SignupInteractor signupInteractor;
    private GameCentralController gameCentralController;

    public JavaFXUserAuthenticationController(JavaFXUserAuthenticationView view, LoginInteractor loginInteractor,
                                              SignupInteractor signupInteractor, GameCentralController gameCentralController){
        this.view = view;
        this.loginInteractor = loginInteractor;
        this.signupInteractor = signupInteractor;
        this.gameCentralController = gameCentralController;
        connectLoginButton();
        connectSignupButton();
    }

    private void connectLoginButton(){
        this.view.getLoginButton().setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                handleLogin();
            }
        });

    }

    private void handleLogin(){
        String username = this.view.getUsernameInput();
        String password = this.view.getPasswordInput();
        if (this.loginInteractor.handleLogin(username, password)){

            PauseTransition delay = new PauseTransition(Duration.seconds(1));
            delay.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    gameCentralController.showHomepage(loginInteractor.getUser(username));
                }
            });
            delay.play();

        }
    }

    private void connectSignupButton(){
        this.view.getSignupButton().setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                handleSignup();
            }
        });

    }

    private void handleSignup(){
        String username = this.view.getUsernameInput();
        String password = this.view.getPasswordInput();
        try {
            this.signupInteractor.handleSignup(username, password);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
