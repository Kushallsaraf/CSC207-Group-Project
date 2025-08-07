import com.csc207.group.app.GameCentralController;
import com.csc207.group.ui.JavaFXUserAuthenticator;
import javafx.application.Application;
import javafx.stage.Stage;

public class GameCentral extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Game Central");

        // Initialize the main controller that manages view switching
        GameCentralController gameCentralController = new GameCentralController(primaryStage);

        // Start the authentication flow
        JavaFXUserAuthenticator authenticator = new JavaFXUserAuthenticator(primaryStage, gameCentralController);
        authenticator.run();
    }
}