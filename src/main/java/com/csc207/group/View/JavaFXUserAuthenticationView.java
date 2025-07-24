package com.csc207.group.View;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class JavaFXUserAuthenticationView implements UserAuthenticationView {

    private TextField usernameField;
    private PasswordField passwordField;
    private Label messageLabel;
    private Stage primaryStage;
    private Button signupButton;
    private Button loginButton;

    public JavaFXUserAuthenticationView(Stage stage){
        this.construct(stage);
    }
    private void construct(Stage stage) {
        this.primaryStage = stage;

        Label logo = new Label("Game Central");
        logo.setFont(Font.font("Bahnschrift", FontWeight.BOLD, 30));
        logo.setStyle("-fx-background-color: transparent");

        Label label = new Label("Login");
        label.setFont(Font.font("Bahnschrift", FontWeight.BOLD, 20));
        label.setStyle("-fx-background-color: transparent;");

        HBox user = new HBox(15);
        Label username = new Label("Username");
        username.setFont(Font.font("Bahnschrift", FontWeight.NORMAL, 12));
        usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setStyle("-fx-border-color: Black; -fx-border-width: 2");
        user.getChildren().addAll(username, usernameField);
        user.setAlignment(Pos.CENTER);
        user.setStyle("-fx-background-color: transparent;");

        HBox password = new HBox(15);
        Label passwordLabel = new Label("Password");
        passwordLabel.setFont(Font.font("Bahnschrift", FontWeight.NORMAL, 12));
        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setStyle("-fx-border-color: Black; -fx-border-width: 2");
        password.getChildren().addAll(passwordLabel, passwordField);
        password.setAlignment(Pos.CENTER);
        password.setStyle("-fx-background-color: transparent;");

        loginButton = new Button("Login");
        loginButton.setFont(Font.font("Bahnschrift", FontWeight.BOLD, 14));
        loginButton.setStyle("-fx-background-color: ROYALBLUE; -fx-border-color: Black; -fx-border-width: 2");

        signupButton = new Button("Sign Up");
        signupButton.setFont(Font.font("Bahnschrift", FontWeight.BOLD, 14));
        signupButton.setStyle("-fx-background-color: LIGHTGRAY; -fx-border-color: Black; -fx-border-width: 2");

        HBox buttonBox = new HBox(20);
        buttonBox.getChildren().addAll(loginButton, signupButton);
        buttonBox.setAlignment(Pos.CENTER);

        messageLabel = new Label();
        messageLabel.setFont(Font.font("Bahnschrift", FontWeight.NORMAL, 12));
        messageLabel.setStyle("-fx-text-fill: red;");

        VBox top = new VBox(20);
        top.setStyle("-fx-background-color: rgb(0,206,209);");
        top.getChildren().addAll(logo, label, user, password, buttonBox, messageLabel);
        top.setAlignment(Pos.TOP_CENTER);

        Scene scene = new Scene(top, 640, 360);
        stage.setScene(scene);
        stage.setTitle("Login");

    }

    public Button getLoginButton(){
        return this.loginButton;
    }
    public Button getSignupButton(){
        return this.signupButton;
    }



    @Override
    public String getUsernameInput() {
        return usernameField.getText();
    }

    @Override
    public String getPasswordInput() {
        return passwordField.getText();
    }

    @Override
    public void display() {
        if (primaryStage != null) {
            primaryStage.show();
        }
    }

    @Override
    public void updateMessageView(String message) {
        messageLabel.setText(message);

        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                messageLabel.setText("");
            }
        });
        pause.play();


    }

    @Override
    public void close() {
        if (primaryStage != null) {
            primaryStage.close();
        }
    }

}

