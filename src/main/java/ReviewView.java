import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class ReviewView extends Application {

    public void start(Stage stage) {
        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-padding: 20; -fx-background-color: #222;");
        vbox.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("Game Reviews");
        title.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label subtitle = new Label("See what others have to say, and tell others what you think!");
        subtitle.setStyle("-fx-font-size: 16px; -fx-text-fill: #ccc;");

        GameReviews gameReviews = new GameReviews();
        gameReviews.addReview(new Review("Yash","Good Review", 4.5));
        gameReviews.addReview(new Review("Kushall","Everything good", 4.2));
        gameReviews.addReview(new Review("Yash","Good Review", 4.8));
        gameReviews.addReview(new Review("Yash123","Good Review 123", 4.8));
        gameReviews.addReview(new Review("Yash","Good Review", 4.5));
        gameReviews.addReview(new Review("Kushall","Everything good", 4.2));
        gameReviews.addReview(new Review("Yash","Good Review", 4.8));
        gameReviews.addReview(new Review("Yash123","Good Review 123", 4.8));
        gameReviews.addReview(new Review("Yash","Good Review", 4.5));
        gameReviews.addReview(new Review("Kushall","Everything good", 4.2));
        gameReviews.addReview(new Review("Yash","Good Review", 4.8));
        gameReviews.addReview(new Review("Yash123","Good Review 123", 4.8));
        gameReviews.addReview(new Review("Yash","Good Review", 4.5));
        gameReviews.addReview(new Review("Kushall","Everything good", 4.2));
        gameReviews.addReview(new Review("Yash","Good Review", 4.8));
        gameReviews.addReview(new Review("Yash123","Good Review 123", 4.8));


        GridPane reviews = new GridPane();
        reviews.setVgap(10);
        reviews.setHgap(10);
        reviews.setStyle("-fx-background-color: #222");

        Integer i = 0;
        for (Review review : gameReviews.getReviews()) {
            reviews.add(new ReviewCard(review).getCard(),i%2,i/2);
            i++;
        }

        ScrollPane curreviews = new ScrollPane(reviews);
        curreviews.setFitToWidth(true);
        curreviews.setPadding(new Insets(20));
        curreviews.setPrefHeight(600);
        curreviews.setStyle("-fx-background-color: #222");

        TextField leave = new TextField();
        leave.setPromptText("Tell what you think!");

        vbox.getChildren().addAll(title,subtitle,curreviews);
        Scene scene = new Scene(vbox,1600,900);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
