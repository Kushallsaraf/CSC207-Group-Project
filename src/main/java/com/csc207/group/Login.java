package com.csc207.group;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Login extends Application {
    String[][] ARR = {{"Yash","Yash123"},{"Taabish","Taabish123"}};
    public void start(Stage stage) {
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

        VBox top =  new VBox(25);
        top.setStyle("-fx-background-color: rgb(0,206,209);");
        top.getChildren().addAll(logo, label, user, password, loginButton);
        top.setAlignment(Pos.TOP_CENTER);
        Scene scene = new Scene(top,640,360);
        stage.setScene(scene);
        stage.show();

        loginButton.setOnAction(e -> {
            boolean result = authen(ARR,usernameField.getText(),passwordField.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login");
            alert.setHeaderText(null);
            if (result) {
//                new GameCentral().start(stage);
                alert.setContentText("*Shows GameCentral homepage, trust me");
            }
            else {
                alert.setContentText("Login Failed");
            }
            alert.showAndWait();
        });
    }

    public boolean authen(String[][] array, String username, String password) {
        boolean result = false;
        for (int i = 0; i < array.length; i++) {
            if (username.equals(array[i][0]) && password.equals(array[i][1])) {
                result = true;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
