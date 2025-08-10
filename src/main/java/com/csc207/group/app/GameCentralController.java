package com.csc207.group.app;

import com.csc207.group.model.GamePreview;
import com.csc207.group.model.User;
import com.csc207.group.service.GameService;
import com.csc207.group.service.NewsService;
import com.csc207.group.service.UserInteractor;
import com.csc207.group.service.UserProfileInteractor;
import com.csc207.group.service.recommendation.RecommendationEngine;
import com.csc207.group.service.recommendation.RecommendationService;
import com.csc207.group.ui.*;
import com.csc207.group.ui.controller.HomeController;
import com.csc207.group.ui.controller.UserProfileController;
import com.csc207.group.views.NewsView;
import javafx.application.HostServices;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class GameCentralController {

    private final Stage primaryStage;
    private final BorderPane mainLayout = new BorderPane(); // Switched to BorderPane
    private UserProfileController userProfileController;
    private UserInteractor userInteractor;
    private GameService gameService;
    private UserProfileView userProfileView;
    private HostServices hostServices;
    private JavaFXUserAuthenticationView authView;
    private RecommendationEngine recommendationEngine;

    public GameCentralController(Stage primaryStage, HostServices hostServices) {
        this.primaryStage = primaryStage;
        this.hostServices = hostServices;

        Scene scene = new Scene(mainLayout, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Game Central");
        primaryStage.show();
    }

    // This method is now simplified to just set the center content
    public void setCenterView(Node view) {
        mainLayout.setCenter(view);
    }

    /**
     * Called after login. Sets up the main UI with the navigation bar.
     */
    public void showMainApp(User user) {
        // Initialize services and controllers
        gameService = new GameService();
        RecommendationService recommendationService = new RecommendationService();
        recommendationEngine = new RecommendationEngine(recommendationService, user);
        userInteractor = new UserInteractor(user, new com.csc207.group.data_access.FirebaseUserDataHandler
                (new com.csc207.group.cache.FirebaseRestClient()));
        userProfileView = new UserProfileView();
        UserProfileInteractor userProfileInteractor = new UserProfileInteractor(userInteractor, gameService);
        userProfileController = new UserProfileController(userProfileInteractor, userInteractor, userProfileView, this);
        userProfileView.setController(userProfileController);

        // Create and set the top navigation bar
        mainLayout.setTop(createTopNav());

        // Show the home page by default
        showHomeView(recommendationEngine);
    }

    /**
     * Creates the top navigation bar.
     * @return HBox containing the navigation elements.
     */
    private HBox createTopNav() {
        HBox topNav = new HBox(15);
        topNav.setStyle("-fx-padding: 10 20; -fx-background-color: #333; -fx-alignment: center-left;");

        Button homeButton = new Button("Home");
        Button newsButton = new Button("News");

        TextField searchField = new TextField();
        searchField.setPromptText("Search...");
        searchField.setStyle("-fx-font-size: 14px; -fx-pref-width: 350px;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button libraryButton = new Button("Your Library");
        libraryButton.setStyle("-fx-background-color: #1a1a1a; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 12; -fx-background-radius: 5;");

        // --- Event Handlers for Navigation ---
        homeButton.setOnAction(e -> showHomeView(recommendationEngine));
        newsButton.setOnAction(e -> showNewsView());
        libraryButton.setOnAction(e -> showUserProfileView());

        // Style the navigation buttons
        String buttonStyle = "-fx-background-color: transparent; -fx-text-fill: #FFFFFF; -fx-font-size: 16px; -fx-cursor: hand;";
        homeButton.setStyle(buttonStyle);
        newsButton.setStyle(buttonStyle);

        topNav.getChildren().addAll(homeButton, newsButton, searchField, spacer, libraryButton);
        return topNav;
    }


    // --- Methods to Switch Views ---

    private void showHomeView(RecommendationEngine engine) {
        // The HomeView is now much simpler and doesn't need navigation buttons
        HomeView homeView = new HomeView();
        HomeController homeController = new HomeController(homeView, gameService, userInteractor,
                userProfileController, engine, this);
        homeController.setRecommendations();
        setCenterView(homeView.getView());
    }

    private void showNewsView() {
        NewsService newsService = new NewsService();
        NewsView newsView = new NewsView(newsService.ArticleBuilder(), url -> hostServices.showDocument(url));
        setCenterView(newsView.getView());
    }

    private void showUserProfileView() {
        userProfileController.refresh(); // Make sure data is up-to-date
        setCenterView(userProfileView.getView());
    }


    public void showUserAuthenticationView() {
        if (authView == null) {
            throw new IllegalStateException("Auth view not set. Call setUserAuthenticationView(...) first.");
        }
        // Hide the top nav while unauthenticated (optional)
        mainLayout.setTop(null);

        // Let the view reset fields/messages
        authView.onShow();

        // IMPORTANT: do NOT call authView.display(); that replaces the Scene.
        setCenterView(authView.getView());

    }

    public void showGamePage(){
        GamePageView gamePageView = new GamePageView();
        setCenterView(gamePageView.getView());

    }

    public void setUserAuthenticationView(JavaFXUserAuthenticationView view) {
        this.authView = view;
    }



}