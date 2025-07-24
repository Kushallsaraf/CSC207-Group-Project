package com.csc207.group.View;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Placeholder view for the main homepage of our app.
 */
public class GameCentralHomepageView {

    private Stage primaryStage;
    private Label messageLabel;
    public GameCentralHomepageView(Stage stage){
        this.primaryStage = stage;
        construct(stage);
    }
    private void construct(Stage stage) {

            Label titleLabel = new Label("Welcome to Game Central!");
            titleLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #333;");

            messageLabel = new Label();
            messageLabel.setStyle("-fx-text-fill: #555;");

            VBox root = new VBox(20, titleLabel, messageLabel);
            root.setAlignment(Pos.CENTER);
            root.setStyle("-fx-background-color: white;");
            root.setPadding(new Insets(20));

            Scene scene = new Scene(root, 800, 600);
            stage.setTitle("Game Central - Homepage");
            stage.setScene(scene);



    }
    public void setUserInfo(String username){
        messageLabel.setText("welcome " + username );

    }

    public void display(){

        if (primaryStage != null) {
            primaryStage.show();
        }

    }
}

