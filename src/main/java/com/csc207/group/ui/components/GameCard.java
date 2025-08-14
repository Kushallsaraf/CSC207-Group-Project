package com.csc207.group.ui.components;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public final class GameCard {
    public static final int FIVE = 5;
    public static final int ONEHUNDREDFIFTY = 150;
    public static final int TWOHUNDRED = 200;
    public static final int THIRTY = 30;
    private VBox view;

    public GameCard(String title, String developer, String imageUrl, EventHandler<MouseEvent> clickHandler) {
        view = new VBox(FIVE);
        view.setAlignment(Pos.CENTER_LEFT);
        // Added a hand cursor and updated the background radius
        view.setStyle("-fx-padding: 10; -fx-background-color: #444; -fx-background-radius: 30; -fx-cursor: hand;");

        ImageView gameImageView = new ImageView();
        try {
            Image image = new Image(imageUrl, ONEHUNDREDFIFTY, TWOHUNDRED, false, true);
            gameImageView.setImage(image);
        }
        catch (Exception ex) {
            System.err.println("Failed to load image: " + imageUrl);
            Image placeholder = new Image("", ONEHUNDREDFIFTY, TWOHUNDRED, false, true);
            gameImageView.setImage(placeholder);
        }

        // Set a clip to round the corners of the image
        Rectangle clip = new Rectangle(ONEHUNDREDFIFTY, TWOHUNDRED);
        clip.setArcWidth(THIRTY);
        clip.setArcHeight(THIRTY);
        gameImageView.setClip(clip);

        gameImageView.setFitWidth(ONEHUNDREDFIFTY);
        gameImageView.setFitHeight(TWOHUNDRED);

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: white;");
        titleLabel.setWrapText(true);
        titleLabel.setMaxWidth(ONEHUNDREDFIFTY);

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
