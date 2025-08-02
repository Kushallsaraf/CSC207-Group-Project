import User.User;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import UserAuthentication.*;
import org.mindrot.jbcrypt.BCrypt;

import java.io.FileNotFoundException;
import java.util.Map;

public class Login extends Application {

    public Login() throws FileNotFoundException {
    }

    public void start(Stage stage) throws FileNotFoundException {
        UserDataHandler chk = new UserAuthentication.UserDataHandler("C:\\CSC207\\Grp Proj Exp\\src\\main\\java\\SignUp\\users.json");

        Label logo = new Label("Game Central");
        logo.setFont(Font.font("Bahnschrift", FontWeight.BOLD, 30));
        logo.setStyle("-fx-background-color: transparent");

        Label label = new Label("Login");
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

        Button loginButton = new Button("Login");
        loginButton.setFont(Font.font("Bahnschrift", FontWeight.BOLD, 14));
        loginButton.setStyle("-fx-background-color: ROYALBLUE; -fx-border-color: Black; -fx-border-width: 2");

        HBox SignUp = new HBox(15);
        Label SignUpLabel = new Label("Don't have an account yet?");
        SignUpLabel.setFont(Font.font("Bahnschrift", FontWeight.NORMAL, 12));
        Button signUpButton = new Button("Sign Up");
        signUpButton.setFont(Font.font("Bahnschrift", FontWeight.NORMAL, 12));
        signUpButton.setStyle("-fx-background-color: ROYALBLUE; -fx-border-color: Black; -fx-border-width: 2");
        SignUp.setAlignment(Pos.CENTER);
        SignUp.getChildren().addAll(SignUpLabel, signUpButton);

        VBox top =  new VBox(25);
        top.setStyle("-fx-background-color: rgb(0,206,209);");
        top.getChildren().addAll(logo, label, user, password, loginButton,  SignUp);
        top.setAlignment(Pos.TOP_CENTER);
        Scene scene = new Scene(top,640,360);
        stage.setScene(scene);
        stage.show();

        loginButton.setOnAction(e -> {
            boolean result = authen(chk.getUsers(),usernameField.getText(),passwordField.getText());
            if (result) {
//                new GameCentral().start(stage);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Login");
                alert.setHeaderText(null);
                alert.setContentText("Login Failed");
                alert.showAndWait();
            }

        });
        signUpButton.setOnAction(e -> {
            try {
                new SignUp().start(stage);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });


    }

    public boolean authen(Map<String, User> dct, String username, String password) {
        boolean result = false;
        String hscode = dct.get(username).getHashedPassword();
        result = BCrypt.checkpw(password,hscode);
        return result;
    }

    public static void main(String[] args) {
        launch(args);
    }
}