package com.csc207.group.View;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * A JavaFX-based login view implementing the UserAuthenticationView interface.
 * Provides login and sign-up functionality.
 */
public class LoginView implements UserAuthenticationView {

    private final VBox view;
    private final TextField usernameField;
    private final PasswordField passwordField;
    private final Label messageLabel;
    private final Button loginButton;
    private final Button signUpButton;

    private final Stage stage;

    public LoginView(Stage stage) {
        this.stage = stage;

        view = new VBox(15);
        view.setStyle("-fx-background-color: #2c3e50; -fx-padding: 40;");
        view.setAlignment(Pos.CENTER);

        // --- Logo and Title ---
        Text logo = new Text("Game Central");
        logo.setFont(Font.font("Bahnschrift", FontWeight.BOLD, 36));
        logo.setFill(Color.WHITE);

        Label prompt = new Label("Sign in to continue");
        prompt.setFont(Font.font("Bahnschrift", 20));
        prompt.setTextFill(Color.LIGHTGRAY);

        // --- Input Fields ---
        usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setStyle("-fx-font-size: 14px; -fx-pref-width: 300px; -fx-padding: 10; -fx-background-color: #34495e; -fx-text-fill: white; -fx-border-color: #2c3e50;");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setStyle("-fx-font-size: 14px; -fx-pref-width: 300px; -fx-padding: 10; -fx-background-color: #34495e; -fx-text-fill: white; -fx-border-color: #2c3e50;");

        // --- Buttons ---
        loginButton = new Button("Login");
        loginButton.setFont(Font.font("Bahnschrift", FontWeight.BOLD, 16));
        loginButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-pref-width: 300px; -fx-padding: 10;");

        signUpButton = new Button("Sign Up");
        signUpButton.setFont(Font.font("Bahnschrift", FontWeight.BOLD, 16));
        signUpButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-pref-width: 300px; -fx-padding: 10;");

        // --- Message Label ---
        messageLabel = new Label();
        messageLabel.setTextFill(Color.RED);
        messageLabel.setFont(Font.font("Bahnschrift", 14));

        // --- Add all components to the view ---
        view.getChildren().addAll(logo, prompt, usernameField, passwordField, loginButton, signUpButton, messageLabel);
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
        stage.setScene(new Scene(view, 600, 450));
        stage.setTitle("Login - Game Central");
        stage.show();
    }

    @Override
    public void updateMessageView(String message) {
        messageLabel.setText(message);
    }

    @Override
    public void close() {
        stage.close();
    }

    @Override
    public void clearUsernameAndPasswordFields() {
        usernameField.clear();
        passwordField.clear();
    }

    @Override
    public void clearPasswordField() {
        passwordField.clear();
    }

    /** Returns the login button for controller to attach action handler */
    public Button getLoginButton() {
        return loginButton;
    }

    /** Returns the sign up button for controller to attach action handler */
    public Button getSignUpButton() {
        return signUpButton;
    }
}
