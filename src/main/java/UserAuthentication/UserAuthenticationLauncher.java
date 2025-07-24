package UserAuthentication;

import javafx.application.Application;
import javafx.stage.Stage;

public class UserAuthenticationLauncher extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        JavaFXUserAuthenticator authenticator = new JavaFXUserAuthenticator(stage);
        authenticator.run();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
