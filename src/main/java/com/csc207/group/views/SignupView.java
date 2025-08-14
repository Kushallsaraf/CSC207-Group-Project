package com.csc207.group.views;

import java.io.FileNotFoundException;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class SignupView {
    private static final int SPACING = 25;
    private static final int TITLE_FONT_SIZE = 36;
    private static final int LABEL_FONT_SIZE = 20;
    private static final int BUTTON_FONT_SIZE = 16;
    private static final String FONT_FAMILY = "Bahnschrift";

    private VBox view;

    public SignupView() throws FileNotFoundException {
        view = new VBox(SPACING);
        view.setStyle("-fx-background-color: #2c3e50; -fx-padding: 40;");
        view.setAlignment(Pos.CENTER);

        // --- Logo and Title ---
        Text logo = new Text("Sign Up to Game Central");
        logo.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, TITLE_FONT_SIZE));
        logo.setFill(Color.WHITE);

        Label label = new Label("Set your new username and password");
        label.setFont(Font.font(FONT_FAMILY, LABEL_FONT_SIZE));
        label.setTextFill(Color.LIGHTGRAY);

        // --- Input Fields ---
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setStyle(
                "-fx-font-size: 14px; -fx-pref-width: 300px; -fx-padding: 10; "
                        + "-fx-background-color: #34495e; -fx-text-fill: white; -fx-border-color: #2c3e50;"
        );

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setStyle(
                "-fx-font-size: 14px; -fx-pref-width: 300px; -fx-padding: 10; "
                        + "-fx-background-color: #34495e; -fx-text-fill: white; -fx-border-color: #2c3e50;"
        );

        // --- Signup Button ---
        Button signupButton = new Button("Create account");
        signupButton.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, BUTTON_FONT_SIZE));
        signupButton.setStyle(
                "-fx-background-color: #3498db; -fx-text-fill: white; "
                        + "-fx-pref-width: 400px; -fx-padding: 10;"
        );

        signupButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("SignUp");
            alert.setHeaderText(null);
            alert.setContentText("Signed up successfully!");
            alert.showAndWait();
        });
    }
}
