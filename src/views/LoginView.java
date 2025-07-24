package views;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class LoginView {

    private VBox view;
    // Hardcoded user credentials for demonstration
    private final String[][] users = {{"Yash", "Yash123"}, {"Taabish", "Taabish123"}};

    public LoginView(Runnable onLoginSuccess) {
        // Main container
        view = new VBox(25);
        view.setStyle("-fx-background-color: #2c3e50; -fx-padding: 40;");
        view.setAlignment(Pos.CENTER);

        // --- Logo and Title ---
        Text logo = new Text("Game Central");
        logo.setFont(Font.font("Bahnschrift", FontWeight.BOLD, 36));
        logo.setFill(Color.WHITE);

        Label label = new Label("Sign in to continue");
        label.setFont(Font.font("Bahnschrift", 20));
        label.setTextFill(Color.LIGHTGRAY);

        // --- Input Fields ---
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setStyle("-fx-font-size: 14px; -fx-pref-width: 300px; -fx-padding: 10; -fx-background-color: #34495e; -fx-text-fill: white; -fx-border-color: #2c3e50;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setStyle("-fx-font-size: 14px; -fx-pref-width: 300px; -fx-padding: 10; -fx-background-color: #34495e; -fx-text-fill: white; -fx-border-color: #2c3e50;");

        // --- Login Button ---
        Button loginButton = new Button("Login");
        loginButton.setFont(Font.font("Bahnschrift", FontWeight.BOLD, 16));
        loginButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-pref-width: 300px; -fx-padding: 10;");

        // --- Event Handler ---
        loginButton.setOnAction(e -> {
            boolean isAuthenticated = authenticate(usernameField.getText(), passwordField.getText());
            if (isAuthenticated) {
                // If login is successful, run the action provided by GameCentral
                onLoginSuccess.run();
            } else {
                // Otherwise, show an error
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText(null);
                alert.setContentText("Invalid username or password. Please try again.");
                alert.showAndWait();
            }
        });

        // Add all components to the view
        view.getChildren().addAll(logo, label, usernameField, passwordField, loginButton);
    }

    /**
     * Authenticates the user against the hardcoded list.
     */
    private boolean authenticate(String username, String password) {
        for (String[] user : users) {
            if (user[0].equals(username) && user[1].equals(password)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the VBox container for this view.
     */
    public VBox getView() {
        return view;
    }
}