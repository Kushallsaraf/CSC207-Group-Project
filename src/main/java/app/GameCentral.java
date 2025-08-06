package app;

import ui.JavaFXUserAuthenticator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import ui.GameDetailView;
import ui.HomeView;
import ui.MyListsView;
import ui.NewsView;

public class GameCentral extends Application {

    private BorderPane mainLayout;
    private Stage primaryStage; // Store the primary stage

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage; // Keep a reference to the stage
        primaryStage.setTitle("Game Central");


        GameCentralController gameCentralController = new GameCentralController(primaryStage);
        JavaFXUserAuthenticator authenticator = new JavaFXUserAuthenticator(primaryStage, gameCentralController);
        authenticator.run();
    }






}