package com.csc207.group.views.Components;

import com.csc207.group.model.DLC;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class DLCcard {
    VBox card;
    public DLCcard(DLC dlc) {
        card = new VBox();
        card = new VBox(5);
        card.setAlignment(Pos.CENTER_LEFT);
        // Added a hand cursor and updated the background radius
        card.setStyle("-fx-padding: 10; -fx-background-color: #444; -fx-background-radius: 30; -fx-cursor: hand;");

        ImageView gameImageView = new ImageView();
        try {
            Image image = new Image(dlc.getCover_image(), 150, 200, false, true);
            gameImageView.setImage(image);
        } catch (Exception e) {
            System.err.println("Failed to load image: " + dlc.getCover_image());
            Image placeholder = new Image("", 150, 200, false, true);
            gameImageView.setImage(placeholder);
        }

        // Set a clip to round the corners of the image
        Rectangle clip = new Rectangle(150, 200);
        clip.setArcWidth(30);
        clip.setArcHeight(30);
        gameImageView.setClip(clip);

        gameImageView.setFitWidth(150);
        gameImageView.setFitHeight(200);

        Label titleLabel = new Label(dlc.getName());
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: white;");
        titleLabel.setWrapText(true);
        titleLabel.setMaxWidth(150);



        card.getChildren().addAll(gameImageView, titleLabel);

        // Set the click event on the card's view
    }
    public VBox getCard() {
        return card;
    }
}
