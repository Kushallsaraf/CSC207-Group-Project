package app;

import cache.FirebaseRestClient;
import data_access.FirebaseUserDataHandler;
import auth.UserDataHandler;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.User;
import service.GameService;
import service.UserInteractor;
import ui.HomeView;
import ui.JavaFXUserAuthenticationView;
import ui.View;
import ui.controller.HomeController;

import java.util.HashMap;
import java.util.Map;

public class GameCentralController {

    private final Stage primaryStage;
    private final StackPane rootPane = new StackPane();
    private final Map<String, View> views = new HashMap<>();

    private JavaFXUserAuthenticationView javaFXUserAuthenticationView;

    public GameCentralController(Stage primaryStage) {
        this.primaryStage = primaryStage;

        Scene scene = new Scene(rootPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Game Central");
        primaryStage.show();
    }

    public void setUserAuthenticationView(JavaFXUserAuthenticationView javaFXUserAuthenticationView) {
        this.javaFXUserAuthenticationView = javaFXUserAuthenticationView;
        registerView(javaFXUserAuthenticationView); // Register it as a View
    }

    public void showUserAuthenticationView() {
        switchToView("login");
    }

    public void registerView(View view) {
        if (!views.containsKey(view.getName())) {
            views.put(view.getName(), view);
            rootPane.getChildren().add(view.getView());
            view.getView().setVisible(false);
        }
    }

    public void switchToView(String name) {
        for (View view : views.values()) {
            view.getView().setVisible(false);
        }

        View target = views.get(name);
        if (target != null) {
            target.getView().setVisible(true);
            target.onShow();
        } else {
            System.err.println("View not found: " + name);
        }
    }

    /**
     * Call this after successful login. Sets up the homepage view and switches to it.
     */
    public void showHomepage(User user) {
        // Create the HomeView
        HomeView homeView = new HomeView();

        // Create the GameService (pass apiClient if required)
        GameService gameService = new GameService(/* apiClient if needed */);

        // Hook up the controller
        FirebaseUserDataHandler firebaseUserDataHandler = new FirebaseUserDataHandler(new FirebaseRestClient());
        UserInteractor userInteractor = new UserInteractor(user, firebaseUserDataHandler);
        HomeController homeController = new HomeController(homeView, gameService, userInteractor);

        // Register and switch to the view
        registerView(homeView);
        switchToView("home");

    }
}
