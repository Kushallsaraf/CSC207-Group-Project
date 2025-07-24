import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import views.GameDetailView;
import views.HomeView;
import views.NewsView;
import views.MyListsView; // Import the new view

public class GameCentral extends Application {

    private BorderPane mainLayout;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Game Central");
        mainLayout = new BorderPane();

        HBox topNav = createTopNav();
        mainLayout.setTop(topNav);

        // Set initial view to Home
        showHomeView();

        Scene scene = new Scene(mainLayout, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // --- Navigation Methods ---

    private void showHomeView() {
        HomeView homeView = new HomeView(e -> showGameDetailView());
        mainLayout.setCenter(homeView.getView());
    }

    private void showNewsView() {
        NewsView newsView = new NewsView();
        mainLayout.setCenter(newsView.getView());
    }

    private void showGameDetailView() {
        GameDetailView gameDetailView = new GameDetailView();
        mainLayout.setCenter(gameDetailView.getView());
    }

    // Method to show the new My Lists view
    private void showMyListsView() {
        MyListsView myListsView = new MyListsView();
        mainLayout.setCenter(myListsView.getView());
    }


    private HBox createTopNav() {
        HBox topNav = new HBox(15);
        topNav.setStyle("-fx-padding: 10 20; -fx-background-color: #333; -fx-alignment: center-left;");

        Button homeButton = new Button("Home");
        Button newsButton = new Button("News");
        Button myListsButton = new Button("My Lists");

        // Search bar is now part of the top navigation
        TextField searchField = new TextField();
        searchField.setPromptText("Search...");
        searchField.setStyle("-fx-font-size: 14px; -fx-pref-width: 350px;");

        // Spacer pushes elements to the right
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Library button styled to match the screenshot
        Button libraryButton = new Button("Your Library");
        libraryButton.setStyle("-fx-background-color: #1a1a1a; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 12; -fx-background-radius: 5;");

        homeButton.setOnAction(e -> showHomeView());
        newsButton.setOnAction(e -> showNewsView());
        myListsButton.setOnAction(e -> showMyListsView()); // Action for the new view

        // Style for text-like buttons on the left
        String buttonStyle = "-fx-background-color: transparent; -fx-text-fill: #FFFFFF; -fx-font-size: 16px;";
        homeButton.setStyle(buttonStyle);
        newsButton.setStyle(buttonStyle);
        myListsButton.setStyle(buttonStyle);

        topNav.getChildren().addAll(homeButton, newsButton, myListsButton, searchField, spacer, libraryButton);
        return topNav;
    }
}