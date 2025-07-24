import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import views.GameDetailView;
import views.HomeView;
import views.NewsView;

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

        Scene scene = new Scene(mainLayout, 1200, 800); // Increased size for better layout
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // --- Navigation Methods ---

    private void showHomeView() {
        // Create a HomeView, passing it the action to perform on card click.
        // The action is a lambda that calls showGameDetailView().
        HomeView homeView = new HomeView(e -> showGameDetailView());
        mainLayout.setCenter(homeView.getView());
    }

    private void showNewsView() {
        NewsView newsView = new NewsView();
        mainLayout.setCenter(newsView.getView());
    }

    private void showGameDetailView() {
        // In a real app, you would pass a game ID here
        GameDetailView gameDetailView = new GameDetailView();
        mainLayout.setCenter(gameDetailView.getView());
    }


    private HBox createTopNav() {
        HBox topNav = new HBox(20);
        topNav.setStyle("-fx-padding: 10; -fx-background-color: #333; -fx-alignment: center-left;");

        Button homeButton = new Button("Home");
        Button newsButton = new Button("News");
        Button myListsButton = new Button("My Lists");

        Label userLibraryLabel = new Label("Library");
        userLibraryLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

        // Button actions now call the navigation methods
        homeButton.setOnAction(e -> showHomeView());
        newsButton.setOnAction(e -> showNewsView());
        // myListsButton.setOnAction(e -> mainLayout.setCenter(new MyListsView().getView()));

        String buttonStyle = "-fx-background-color: #555; -fx-text-fill: white; -fx-font-weight: bold;";
        homeButton.setStyle(buttonStyle);
        newsButton.setStyle(buttonStyle);
        myListsButton.setStyle(buttonStyle);


        topNav.getChildren().addAll(homeButton, newsButton, myListsButton, userLibraryLabel);
        return topNav;
    }
}