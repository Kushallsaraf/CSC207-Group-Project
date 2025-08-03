package views;

import Signup.SignupUseCase;
import UserAuthentication.UserDataHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.io.IOException;

public class SignupView {
    private VBox view;
    public SignupView() throws FileNotFoundException {
        UserDataHandler chk = new UserAuthentication.UserDataHandler("C:\\CSC207\\CSC207-Group-Project\\src\\main\\java\\UserData\\users.json");

        view = new VBox(25);
        view.setStyle("-fx-background-color: #2c3e50; -fx-padding: 40;");
        view.setAlignment(Pos.CENTER);

        // --- Logo and Title ---
        Text logo = new Text("Sign Up to Game Central");
        logo.setFont(Font.font("Bahnschrift", FontWeight.BOLD, 36));
        logo.setFill(Color.WHITE);

        Label label = new Label("Set your new username and password");
        label.setFont(Font.font("Bahnschrift", 20));
        label.setTextFill(Color.LIGHTGRAY);

        // --- Input Fields ---
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setStyle("-fx-font-size: 14px; -fx-pref-width: 300px; -fx-padding: 10; -fx-background-color: #34495e; -fx-text-fill: white; -fx-border-color: #2c3e50;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setStyle("-fx-font-size: 14px; -fx-pref-width: 300px; -fx-padding: 10; -fx-background-color: #34495e; -fx-text-fill: white; -fx-border-color: #2c3e50;");

        // --- Signup Button ---
        Button signupButton = new Button("Create account");
        signupButton.setFont(Font.font("Bahnschrift", FontWeight.BOLD, 16));
        signupButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-pref-width: 400px; -fx-padding: 10;");

        signupButton.setOnAction(e -> {
                    try {
                        SignupUseCase signupUseCase = new SignupUseCase(chk);
                        signupUseCase.signUp(usernameField.getText(), passwordField.getText());

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("SignUp");
                        alert.setHeaderText(null);
                        alert.setContentText("Signed up successfully!");
                        alert.showAndWait();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            );
    }
}