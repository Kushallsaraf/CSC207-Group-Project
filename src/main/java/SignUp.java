import Signup.SignupUseCase;
import UserAuthentication.UserDataHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;

public class SignUp {
    public void start(Stage stage) throws FileNotFoundException {
        Label logo = new Label("Game Central");
        logo.setFont(Font.font("Bahnschrift", FontWeight.BOLD, 30));
        logo.setStyle("-fx-background-color: transparent");

        Label label = new Label("SignUp");
        label.setFont(Font.font("Bahnschrift", FontWeight.BOLD, 20));
        label.setStyle("-fx-background-color: transparent;");

        HBox user =  new HBox(15);
        Label username = new Label("Username");
        username.setFont(Font.font("Bahnschrift", FontWeight.NORMAL, 12));
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setStyle("-fx-border-color: Black; -fx-border-width: 2");
        user.getChildren().addAll(username, usernameField);
        user.setAlignment(Pos.CENTER);
        user.setStyle("-fx-background-color: transparent;");

        HBox password = new HBox(15);
        Label passwordLabel = new Label("Password");
        passwordLabel.setFont(Font.font("Bahnschrift", FontWeight.NORMAL, 12));
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setStyle("-fx-border-color: Black; -fx-border-width: 2");
        password.getChildren().addAll(passwordLabel, passwordField);
        password.setAlignment(Pos.CENTER);
        password.setStyle("-fx-background-color: transparent;");

        Button SignUp = new Button("SignUp");
        SignUp.setFont(Font.font("Bahnschrift", FontWeight.BOLD, 14));
        SignUp.setStyle("-fx-background-color: ROYALBLUE; -fx-border-color: Black; -fx-border-width: 2");

        VBox top =  new VBox(25);
        top.setStyle("-fx-background-color: rgb(0,206,209);");
        top.getChildren().addAll(logo, label, user, password, SignUp);
        top.setAlignment(Pos.TOP_CENTER);
        Scene scene = new Scene(top,640,360);
        stage.setScene(scene);
        stage.show();

        SignUp.setOnAction(event -> {
            try {
                SignupUseCase signupUseCase = new SignupUseCase(new UserDataHandler("src/main/java/UserData/users.json"));
                signupUseCase.signUp(usernameField.getText(),passwordField.getText());

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("SignUp");
                alert.setHeaderText(null);
                alert.setContentText("Signed up successfully!");
                alert.showAndWait();

                new Login().start(stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
