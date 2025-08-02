import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import views.GameDetailView;
import views.HomeView;
import views.LoginView;
import views.MyListsView;
import views.NewsView;
import views.DeveloperView; // Import the new view


public class GameCentral extends Application {

    private BorderPane mainLayout;
    private Stage primaryStage; // Store the primary stage

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
        NewsView newsView = new NewsView();
        mainLayout.setCenter(newsView.getView());
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