package components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class GameCard {
    private VBox view;

    public GameCard(String title, String developer, String imageUrl) {
        view = new VBox(5);
        view.setAlignment(Pos.CENTER_LEFT);
        view.setStyle("-fx-padding: 10; -fx-background-color: #444; -fx-background-radius: 10;");

        // Game Image (using a placeholder)
        ImageView gameImageView = new ImageView();
        try {
            // It's good practice to handle potential loading errors
            Image image = new Image(imageUrl, 150, 200, false, true);
            gameImageView.setImage(image);
        } catch (Exception e) {
            System.err.println("Failed to load image: " + imageUrl);
            // You could set a default placeholder image here
            Image placeholder = new Image("https://placehold.co/150x200/ff0000/ffffff?text=Error", 150, 200, false, true);
            gameImageView.setImage(placeholder);
        }
        gameImageView.setFitWidth(150);
        gameImageView.setFitHeight(200);


        // Game Title
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: white;");
        titleLabel.setWrapText(true);
        titleLabel.setMaxWidth(150);


        // Game Developer
        Label devLabel = new Label(developer);
        devLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #bbb;");

        view.getChildren().addAll(gameImageView, titleLabel, devLabel);
    }

    public VBox getView() {
        return view;
    }
}