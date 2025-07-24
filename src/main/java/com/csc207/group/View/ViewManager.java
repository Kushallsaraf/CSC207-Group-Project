package com.csc207.group.View;

import UserAuthentication.User;
import javafx.stage.Stage;

public class ViewManager {
    private final Stage primaryStage;
    private JavaFXUserAuthenticationView javaFXUserAuthenticationView;

    public ViewManager(Stage primaryStage, JavaFXUserAuthenticationView javaFXUserAuthenticationView) {
        this.primaryStage = primaryStage;
        this.javaFXUserAuthenticationView = javaFXUserAuthenticationView;
    }

    public void showUserAuthenticationView() {


            javaFXUserAuthenticationView.display();

    }

    /**this can be the entry point into the main app. As such, a User is passed in
     * so that their information can be loaded.
     *
     */
    public void showHomepage(User user) {
        GameCentralHomepageView gameCentralHomepageView = new GameCentralHomepageView(primaryStage);
        gameCentralHomepageView.setUserInfo(user.getUsername());
        gameCentralHomepageView.display();
    }
}
