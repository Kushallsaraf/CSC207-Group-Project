package com.csc207.group;


import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Placeholder view for the main homepage of Game Central.
 */
public class GameCentralHomepageView {

    private Stage primaryStage;
    public GameCentralHomepageView(Stage stage){
        this.primaryStage = stage;
        construct(stage);
    }
    private void construct(Stage stage) {
        Label label = new Label("Welcome to Game Central!");
        StackPane root = new StackPane(label);
        root.setStyle("-fx-background-color: white;");

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Game Central - Homepage");
        stage.show();
    }

    public void display(){
        if (primaryStage != null) {
            primaryStage.show();
        }

    }
}

