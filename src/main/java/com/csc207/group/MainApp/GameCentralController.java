package com.csc207.group.MainApp;

import UserLoading.User;
import com.csc207.group.View.HomeView;
import com.csc207.group.View.JavaFXUserAuthenticationView;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class GameCentralController {
    private final Stage primaryStage;
    private JavaFXUserAuthenticationView javaFXUserAuthenticationView;

    public GameCentralController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void showUserAuthenticationView() {


            javaFXUserAuthenticationView.display();

    }

    public void setUserAuthenticationView(JavaFXUserAuthenticationView javaFXUserAuthenticationView){
        this.javaFXUserAuthenticationView = javaFXUserAuthenticationView;
    }

    /**this can be the entry point into the main app. As such, a User is passed in
     * so that their information can be loaded.
     *
     */
    public void showHomepage(User user) {
        EventHandler<MouseEvent> cardClickHandler = event -> {
            // Define behavior when a card is clicked (e.g., open detail view)
            System.out.println("Card clicked");
        };

        HomeView homeView = new HomeView(cardClickHandler);
        homeView.setUsername(user.getUsername());
        Scene scene = new Scene(homeView.getView(), 800, 600); // Adjust size as needed
        primaryStage.setScene(scene);
        primaryStage.setTitle("Game Central - Home");
        primaryStage.show();
    }
}
