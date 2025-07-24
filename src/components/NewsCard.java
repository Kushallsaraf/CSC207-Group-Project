package components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;

public class NewsCard {
    private StackPane view;

    public NewsCard(String title, String imageUrl) {
        view = new StackPane();
        view.setAlignment(Pos.BOTTOM_CENTER);
        view.setStyle("-fx-background-radius: 15; -fx-border-radius: 15;"); // For rounded corners

        // News Image
        ImageView imageView = new ImageView(new Image(imageUrl, 300, 170, false, true));
        // Apply rounded corners to the image view itself
        imageView.setStyle("-fx-background-radius: 15; -fx-border-radius: 15;");

        // Title Overlay
        Label titleLabel = new Label(title);
        titleLabel.setWrapText(true);
        titleLabel.setTextAlignment(TextAlignment.CENTER);
        titleLabel.setStyle("-fx-padding: 15; -fx-background-color: rgba(0, 0, 0, 0.6); -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");
        titleLabel.setMaxWidth(300);
        // This ensures the label doesn't extend beyond the image corners
        titleLabel.prefHeightProperty().bind(view.heightProperty());
        titleLabel.setAlignment(Pos.BOTTOM_CENTER);


        view.getChildren().addAll(imageView, titleLabel);
    }

    public StackPane getView() {
        return view;
    }
}