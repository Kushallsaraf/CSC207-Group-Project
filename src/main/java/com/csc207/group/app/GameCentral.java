package com.csc207.group.app;

import com.csc207.group.ui.JavaFxUserAuthenticator;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GameCentral extends Application {

    private BorderPane mainLayout;
    private Stage primaryStage;

    /**
     * Main entry point for the app.
     * @param args (command line arguments)
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        stage.setTitle("Game Central");

        HostServices h = getHostServices();
        GameCentralController gameCentralController = new GameCentralController(stage, h);
        JavaFxUserAuthenticator authenticator = new JavaFxUserAuthenticator(stage, gameCentralController);
        authenticator.run();
    }
}
