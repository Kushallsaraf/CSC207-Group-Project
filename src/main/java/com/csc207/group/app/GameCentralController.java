package com.csc207.group.app;

import com.csc207.group.model.Game;
import com.csc207.group.model.User;
import com.csc207.group.service.GamePageInteractor;
import com.csc207.group.service.GameService;
import com.csc207.group.service.NewsService;
import com.csc207.group.service.PersonalProfileInteractor;
import com.csc207.group.service.UserInteractor;
import com.csc207.group.service.UserProfileInteractor;
import com.csc207.group.service.recommendation.RecommendationEngine;
import com.csc207.group.service.recommendation.RecommendationService;
import com.csc207.group.ui.HomeView;
import com.csc207.group.ui.JavaFXUserAuthenticationView;
import com.csc207.group.ui.PersonalProfileView;
import com.csc207.group.ui.UserProfileView;
import com.csc207.group.ui.controller.HomeController;
import com.csc207.group.ui.controller.PersonalProfileController;
import com.csc207.group.ui.controller.UserProfileController;
import com.csc207.group.views.GameDetailController;
import com.csc207.group.views.GameDetailViewFunc;
import com.csc207.group.views.NewsView;
import javafx.application.HostServices;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class GameCentralController {

    private final Stage primaryStage;
    private final BorderPane mainLayout = new BorderPane(); // Switched to BorderPane
    private PersonalProfileController personalProfileController;
    private UserInteractor userInteractor;
    private GameService gameService;
    private PersonalProfileView personalProfileView;
    private HostServices hostServices;
    private JavaFXUserAuthenticationView authView;
    private RecommendationEngine recommendationEngine;
    private HomeController homeController;

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
        personalProfileView = new PersonalProfileView();
        PersonalProfileInteractor personalProfileInteractor = new PersonalProfileInteractor(userInteractor, gameService);
        personalProfileController = new PersonalProfileController(personalProfileInteractor, userInteractor, personalProfileView, this);
        personalProfileView.setController(personalProfileController);

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

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button libraryButton = new Button("Your Library");
        libraryButton.setStyle("-fx-background-color: #1a1a1a; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 12; -fx-background-radius: 5;");

        // --- Event Handlers for Navigation ---
        homeButton.setOnAction(e -> showHomeView(recommendationEngine));
        newsButton.setOnAction(e -> showNewsView());
        libraryButton.setOnAction(e -> showPersonalProfileView());

        // Style the navigation buttons
        String buttonStyle = "-fx-background-color: transparent; -fx-text-fill: #FFFFFF; -fx-font-size: 16px; -fx-cursor: hand;";
        homeButton.setStyle(buttonStyle);
        newsButton.setStyle(buttonStyle);

        topNav.getChildren().addAll(homeButton, newsButton, spacer, libraryButton);
        return topNav;
    }


    // --- Methods to Switch Views ---

    public void showHomeView(RecommendationEngine engine) {
        HomeView homeView = new HomeView();
        homeController = new HomeController(homeView, gameService, userInteractor,
                personalProfileController, engine, this);
        homeController.setRecommendations();
        setCenterView(homeView.getView());
    }

    public void showHomeViewWithSearch(String query) {
        HomeView homeView = new HomeView();
        homeController = new HomeController(homeView, gameService, userInteractor,
                personalProfileController, recommendationEngine, this);
        homeController.setRecommendations();
        homeView.getSearchQuery(); // This is a bit of a hack to make sure the field is not null
        homeView.displayGameResults(homeController.searchByGenre(query));
        setCenterView(homeView.getView());
    }


    private void showNewsView() {
        NewsService newsService = new NewsService();
        NewsView newsView = new NewsView(newsService.articleBuilder(), url -> hostServices.showDocument(url));
        setCenterView(newsView.getView());
    }

    private void showPersonalProfileView() {
        personalProfileController.refresh(); // Make sure data is up-to-date
        setCenterView(personalProfileView.getView());
    }

    public void showUserProfileView(String targetUsername){
        UserProfileView userProfileView = new UserProfileView();
        UserProfileInteractor userProfileInteractor = new UserProfileInteractor(userInteractor, gameService,
                targetUsername);
        UserProfileController userProfileController = new UserProfileController(userInteractor,
                userProfileInteractor,userProfileView, targetUsername, this);
        setCenterView(userProfileView.getView());

    }


    public void showUserAuthenticationView() {
        if (authView == null) {
            throw new IllegalStateException("Auth view not set. Call setUserAuthenticationView(...) first.");
        }
        // Hide the top nav while unauthenticated
        mainLayout.setTop(null);

        // Let the view reset fields/messages
        authView.onShow();

        setCenterView(authView.getView());

    }

    public void showGamePage(Integer gameid){
        Game game = gameService.getGameById(gameid);
        GamePageInteractor gamePageInteractor = new GamePageInteractor(userInteractor);
        GameDetailController controller = new GameDetailController(game, gamePageInteractor, this
                , userInteractor.getUser(), hostServices, homeController);
        GameDetailViewFunc view = controller.setView();
        setCenterView(view);

    }

    public void setUserAuthenticationView(JavaFXUserAuthenticationView view) {
        this.authView = view;
    }

}
