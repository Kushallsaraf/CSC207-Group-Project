package com.csc207.group.ui.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;

public final class NewsCard {
    public static final int THREEHUNDRED = 300;
    public static final int ONEHUNDREDSEVENTY = 170;
    public static final int THIRTY = 30;
    private StackPane view;

    public NewsCard(String title, String imageUrl) {
        view = new StackPane();
        view.setAlignment(Pos.BOTTOM_CENTER);
        // Set the background radius for the main container
        view.setStyle("-fx-background-radius: 30; -fx-border-radius: 30;");

        // News Image
        ImageView imageView = new ImageView(new Image(imageUrl, THREEHUNDRED, ONEHUNDREDSEVENTY, false, true));

        // To properly round the image corners, we apply a clip
        Rectangle clip = new Rectangle(THREEHUNDRED, ONEHUNDREDSEVENTY);
        clip.setArcWidth(THIRTY);
        clip.setArcHeight(THIRTY);
        imageView.setClip(clip);

        // Title Overlay
        Label titleLabel = new Label(title);
        titleLabel.setWrapText(true);
        titleLabel.setTextAlignment(TextAlignment.CENTER);
        titleLabel.setStyle("-fx-padding: 15; -fx-background-color: rgba(0, 0, 0, 0.6); -fx-text-fill: white; "
                + "-fx-font-weight: bold; -fx-font-size: 14px;");
        titleLabel.setMaxWidth(THREEHUNDRED);
        titleLabel.prefHeightProperty().bind(view.heightProperty());
        titleLabel.setAlignment(Pos.BOTTOM_CENTER);

        view.getChildren().addAll(imageView, titleLabel);
    }

    public StackPane getView() {
        return view;
    }
}
