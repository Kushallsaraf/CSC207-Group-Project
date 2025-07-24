package components;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class GameCard {
    private VBox view;

    // Modified constructor to accept a click handler
    public GameCard(String title, String developer, String imageUrl, EventHandler<MouseEvent> clickHandler) {
        view = new VBox(5);
        view.setAlignment(Pos.CENTER_LEFT);
        // Added a hand cursor to indicate it's clickable
        view.setStyle("-fx-padding: 10; -fx-background-color: #444; -fx-background-radius: 10; -fx-cursor: hand;");

        ImageView gameImageView = new ImageView();
        try {
            Image image = new Image(imageUrl, 150, 200, false, true);
            gameImageView.setImage(image);
        } catch (Exception e) {
            System.err.println("Failed to load image: " + imageUrl);
            Image placeholder = new Image("https://placehold.co/150x200/ff0000/ffffff?text=Error", 150, 200, false, true);
            gameImageView.setImage(placeholder);
        }
        gameImageView.setFitWidth(150);
        gameImageView.setFitHeight(200);

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: white;");
        titleLabel.setWrapText(true);
        titleLabel.setMaxWidth(150);

        Label devLabel = new Label(developer);
        devLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #bbb;");

        view.getChildren().addAll(gameImageView, titleLabel, devLabel);

        // Set the click event on the card's view
        view.setOnMouseClicked(clickHandler);
    }

    public VBox getView() {
        return view;
    }
}