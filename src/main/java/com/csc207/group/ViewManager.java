package com.csc207.group;

import UserAuthentication.UserAuthenticationView;
import UserAuthentication.controllers.JavaFXUserAuthenticationView;
import com.csc207.group.JavaFXUserAuthenticator;
import javafx.stage.Stage;

public class ViewManager {
    private final Stage primaryStage;
    private JavaFXUserAuthenticationView javaFXUserAuthenticationView;

    public ViewManager(Stage primaryStage, JavaFXUserAuthenticationView javaFXUserAuthenticationView) {
        this.primaryStage = primaryStage;
        this.javaFXUserAuthenticationView = javaFXUserAuthenticationView;
    }

    public void showLoginView() {


            javaFXUserAuthenticationView.display();

    }

    public void showHomepage() {
        GameCentralHomepageView gameCentralHomepageView = new GameCentralHomepageView(primaryStage);
        gameCentralHomepageView.display();
    }
}
