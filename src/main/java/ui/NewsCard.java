package ui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;

public class NewsCard {
    private StackPane view;

    public NewsCard(String title, String imageUrl) {
        view = new StackPane();
        view.setAlignment(Pos.BOTTOM_CENTER);
        // Set the background radius for the main container
        view.setStyle("-fx-background-radius: 30; -fx-border-radius: 30;");

        // News Image
        ImageView imageView = new ImageView(new Image(imageUrl, 300, 170, false, true));

        // To properly round the image corners, we apply a clip
        Rectangle clip = new Rectangle(300, 170);
        clip.setArcWidth(30);
        clip.setArcHeight(30);
        imageView.setClip(clip);

        // Title Overlay
        Label titleLabel = new Label(title);
        titleLabel.setWrapText(true);
        titleLabel.setTextAlignment(TextAlignment.CENTER);
        titleLabel.setStyle("-fx-padding: 15; -fx-background-color: rgba(0, 0, 0, 0.6); -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");
        titleLabel.setMaxWidth(300);
        titleLabel.prefHeightProperty().bind(view.heightProperty());
        titleLabel.setAlignment(Pos.BOTTOM_CENTER);


        view.getChildren().addAll(imageView, titleLabel);
    }

    public StackPane getView() {
        return view;
    }
}