package com.csc207.group.app;

import com.csc207.group.auth.UserDataHandler;
import com.csc207.group.cache.FirebaseRestClient;
import com.csc207.group.data_access.FirebaseUserDataHandler;
import com.csc207.group.model.User;
import com.csc207.group.service.GameService;
import com.csc207.group.service.UserInteractor;
import com.csc207.group.service.UserProfileInteractor;
import com.csc207.group.ui.HomeView;
import com.csc207.group.ui.JavaFXUserAuthenticationView;
import com.csc207.group.ui.UserProfileView;
import com.csc207.group.ui.View;
import com.csc207.group.ui.controller.HomeController;
import com.csc207.group.ui.controller.UserProfileController;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class GameCentralController {

    private final Stage primaryStage;
    private final StackPane rootPane = new StackPane();
    private final Map<String, View> views = new HashMap<>();
    private UserProfileController userProfileController;
    private UserProfileInteractor userProfileInteractor;
    private UserInteractor userInteractor;
    private GameService gameService;
    private HomeController homeController;

    private JavaFXUserAuthenticationView javaFXUserAuthenticationView;
    private UserProfileView userProfileView;

    public GameCentralController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        Scene scene = new Scene(rootPane, 1200, 800); // Adjusted for a better window size
        primaryStage.setScene(scene);
        primaryStage.setTitle("Game Central");
        primaryStage.show();
    }

    public void setUserAuthenticationView(JavaFXUserAuthenticationView javaFXUserAuthenticationView) {
        this.javaFXUserAuthenticationView = javaFXUserAuthenticationView;
        registerView(javaFXUserAuthenticationView);
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
            if ("userProfile".equals(name) && userProfileController != null) {
                userProfileController.refresh();
            }
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
        HomeView homeView = new HomeView();
        homeView.getHomeButton().setOnAction(event -> switchToView("home"));
        homeView.getProfileButton().setOnAction(event -> {
            showUserProfile(user);
            switchToView("userProfile");
        });

        gameService = new GameService();

        // Correctly initialize FirebaseUserDataHandler
        FirebaseUserDataHandler firebaseUserDataHandler = new FirebaseUserDataHandler(new FirebaseRestClient());
        userInteractor = new UserInteractor(user, firebaseUserDataHandler);
        userProfileView = new UserProfileView();
        userProfileInteractor = new UserProfileInteractor(userInteractor, gameService);
        userProfileController = new UserProfileController(userProfileInteractor, userInteractor, userProfileView);
        userProfileView.setController(userProfileController);
        homeController = new HomeController(homeView, gameService, userInteractor, userProfileController);

        registerView(homeView);
        switchToView("home");
    }

    public void showUserProfile(User user) {
        // Ensure the profile view is registered before switching to it
        if (!views.containsKey("userProfile")) {
            registerView(userProfileView);
        }

        userProfileView.getHomeButton().setOnAction(event -> switchToView("home"));
        userProfileView.getProfileButton().setOnAction(event -> switchToView("userProfile"));

        userProfileController.refresh(); // Refresh data before showing
        switchToView("userProfile");
    }
}