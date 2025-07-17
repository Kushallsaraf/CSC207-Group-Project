import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
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

        // Main layout
        mainLayout = new BorderPane();

        // Top navigation bar
        HBox topNav = createTopNav();
        mainLayout.setTop(topNav);

        // Set initial view to Home
        mainLayout.setCenter(new HomeView().getView());

        // Set the scene
        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Creates the top navigation bar with buttons.
     * @return HBox containing the navigation elements.
     */
    private HBox createTopNav() {
        HBox topNav = new HBox(20);
        topNav.setStyle("-fx-padding: 10; -fx-background-color: #333; -fx-alignment: center-left;");

        // Navigation Buttons
        Button homeButton = new Button("Home");
        Button newsButton = new Button("News");
        Button myListsButton = new Button("My Lists");

        // User Library Label
        Label userLibraryLabel = new Label("Library");
        userLibraryLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

        // Button actions
        homeButton.setOnAction(e -> mainLayout.setCenter(new HomeView().getView()));
        newsButton.setOnAction(e -> mainLayout.setCenter(new NewsView().getView()));
        //myListsButton.setOnAction(e -> mainLayout.setCenter(new MyListsView().getView()));

        // Style buttons
        String buttonStyle = "-fx-background-color: #555; -fx-text-fill: white; -fx-font-weight: bold;";
        homeButton.setStyle(buttonStyle);
        newsButton.setStyle(buttonStyle);
        myListsButton.setStyle(buttonStyle);


        topNav.getChildren().addAll(homeButton, newsButton, myListsButton, userLibraryLabel);
        return topNav;
    }
}