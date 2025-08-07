package com.csc207.group.app;

import com.csc207.group.ui.JavaFXUserAuthenticator;
import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GameCentral extends Application {

    private BorderPane mainLayout;
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Game Central");


        GameCentralController gameCentralController = new GameCentralController(primaryStage);
        JavaFXUserAuthenticator authenticator = new JavaFXUserAuthenticator(primaryStage, gameCentralController);
        authenticator.run();
    }
}