import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class ReviewView extends Application {

    public void start(Stage stage) {
        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-padding: 5; -fx-background-color: #222;");
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
        reviews.setAlignment(Pos.TOP_CENTER);

        Integer i = 0;
        for (Review review : gameReviews.getReviews()) {
            reviews.add(new ReviewCard(review).getCard(),i%2,i/2);
            i++;
        }

        ScrollPane curreviews = new ScrollPane(reviews);
        curreviews.setFitToWidth(true);
        curreviews.setPadding(new Insets(20));
        curreviews.setMaxHeight(680);
        curreviews.setStyle("-fx-background-color: #222");

        TextArea leave = new TextArea();
        leave.setPromptText("Tell what you think!");
        TextFormatter<String> formatter = new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 300) {
                return change;
            } else {
                return null;
            }
        });
        leave.setTextFormatter(formatter);
        leave.setMinWidth(1200);
        leave.setMinHeight(60);
        leave.setStyle("-fx-font-size: 14");

        TextField rating = new TextField();
        rating.setPromptText("Rating");

        Button post = new Button("Post");
        post.setStyle("-fx-background-color: #222; -fx-font-size: 16; -fx-background-radius: 10; -fx-border-radius: 10; -fx-text-fill: white; -fx-border-color: white; -fx-border-width: 4");

        HBox postsys = new HBox(25);
        postsys.setPrefSize(1750,80);
        postsys.setAlignment(Pos.CENTER);
        postsys.getChildren().addAll(leave,rating,post);

        vbox.getChildren().addAll(title,subtitle,curreviews,postsys);
        Scene scene = new Scene(vbox,1920,1000);
        stage.setScene(scene);
        stage.show();

        post.setOnAction(e -> {
            if (rating.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText(null);
                alert.setContentText("Please enter a valid rating 0-5");
                alert.showAndWait();
            }

            try {
                double value = Double.parseDouble(rating.getText());
                if (value < 0 || value > 5) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Input");
                    alert.setHeaderText(null);
                    alert.setContentText("Please enter a valid rating 0-5");
                    alert.showAndWait();
                } else {
                    gameReviews.addReview(new Review("newuser",leave.getText(), 4.5));
                }
            } catch (NumberFormatException f) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText(null);
                alert.setContentText("Please enter a valid rating 0-5");
                alert.showAndWait();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

}
