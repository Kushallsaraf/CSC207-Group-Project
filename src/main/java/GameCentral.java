import com.csc207.group.model.News;
import com.csc207.group.service.NewsService;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import views.GameDetailView;
import com.csc207.group.ui.HomeView;
import views.LoginView;
import views.MyListsView;
import com.csc207.group.views.NewsView;
import com.csc207.group.views.DeveloperView;

import java.util.List;
import java.util.function.Consumer;

public class GameCentral extends Application {

    private BorderPane mainLayout;
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage; // Keep a reference to the stage
        primaryStage.setTitle("Game Central");

        // --- Show Login View First ---
        LoginView loginView = new LoginView(this::showMainApplication);
        Scene loginScene = new Scene(loginView.getView(), 1200, 800);
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    private void showMainApplication() {
        mainLayout = new BorderPane();
        HBox topNav = createTopNav();
        mainLayout.setTop(topNav);
        showHomeView(); // Show the home view by default

        Scene mainScene = new Scene(mainLayout, 1200, 800);
        primaryStage.setScene(mainScene);
    }

    // --- Navigation Methods ---

    private void showHomeView() {
        // Clicking a game card now correctly opens the detail view
        HomeView homeView = new HomeView(e -> showGameDetailView());
        mainLayout.setCenter(homeView.getView());
    }

    private void showNewsView() {
        // Show a loading indicator while fetching data
        ProgressIndicator loadingIndicator = new ProgressIndicator();
        mainLayout.setCenter(new StackPane(loadingIndicator));

        // Create a background task to fetch news
        Task<List<News>> fetchNewsTask = new Task<>() {
            @Override
            protected List<News> call() throws Exception {
                NewsService newsService = new NewsService();
                return newsService.ArticleBuilder();
            }
        };

        // When the task succeeds, update the UI with the news
        fetchNewsTask.setOnSucceeded(event -> {
            List<News> articles = fetchNewsTask.getValue();
            // Pass the list of articles and the method to open links to the view
            NewsView newsView = new NewsView(articles, this::openWebpage);
            mainLayout.setCenter(newsView.getView());
        });

        // If the task fails, show an error message
        fetchNewsTask.setOnFailed(event -> {
            mainLayout.setCenter(new Label("Failed to load news. Please check your connection."));
        });

        // Start the background task
        new Thread(fetchNewsTask).start();
    }

    /**
     * Opens the given URL in the system's default web browser.
     * @param url The URL to open.
     */
    private void openWebpage(String url) {
        getHostServices().showDocument(url);
    }

    private void showGameDetailView() {
        // The detail view now needs a handler for navigating to the developer's page
        GameDetailView gameDetailView = new GameDetailView(devName -> showDeveloperView(devName));
        mainLayout.setCenter(gameDetailView.getView());
    }

    private void showMyListsView() {
        MyListsView myListsView = new MyListsView();
        mainLayout.setCenter(myListsView.getView());
    }

    // This new method displays the developer-specific view
    private void showDeveloperView(String developerName) {
        DeveloperView developerView = new DeveloperView(developerName, e -> showGameDetailView());
        mainLayout.setCenter(developerView.getView());
    }


    private HBox createTopNav() {
        HBox topNav = new HBox(15);
        topNav.setStyle("-fx-padding: 10 20; -fx-background-color: #333; -fx-alignment: center-left;");

        Button homeButton = new Button("Home");
        Button newsButton = new Button("News");
        Button myListsButton = new Button("My Lists");

        TextField searchField = new TextField();
        searchField.setPromptText("Search...");
        searchField.setStyle("-fx-font-size: 14px; -fx-pref-width: 350px;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button libraryButton = new Button("Your Library");
        libraryButton.setStyle("-fx-background-color: #1a1a1a; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 12; -fx-background-radius: 5;");

        homeButton.setOnAction(e -> showHomeView());
        newsButton.setOnAction(e -> showNewsView());
        myListsButton.setOnAction(e -> showMyListsView());

        String buttonStyle = "-fx-background-color: transparent; -fx-text-fill: #FFFFFF; -fx-font-size: 16px;";
        homeButton.setStyle(buttonStyle);
        newsButton.setStyle(buttonStyle);
        myListsButton.setStyle(buttonStyle);

        topNav.getChildren().addAll(homeButton, newsButton, myListsButton, searchField, spacer, libraryButton);
        return topNav;
    }
}