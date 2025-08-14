package com.csc207.group.views.Components;

import com.csc207.group.model.Achievement;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class AchievementCard {
    private HBox card;

    public AchievementCard(Achievement achievement) {
        card = new HBox(15);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setStyle("-fx-background-color: #2a2a2a; -fx-padding: 10; -fx-background-radius: 8;");
        card.setPrefWidth(600);

        // Achievement Image
        ImageView imageView = new ImageView();
        try {
            Image image = new Image(achievement.getAchievementImage(), 64, 64, true, true);
            imageView.setImage(image);
        } catch (Exception e) {
            // Placeholder for failed image load
            Image placeholder = new Image("", 64, 64, false, true);
            imageView.setImage(placeholder);
        }

        // Achievement Info
        VBox infoBox = new VBox(5);
        Label nameLabel = new Label(achievement.getAchievementName());
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: white;");

        Label descriptionLabel = new Label(achievement.getAchievementDescription());
        descriptionLabel.setWrapText(true);
        descriptionLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #ccc;");

        Tooltip.install(nameLabel, new Tooltip(achievement.getAchievementDescription()));

        infoBox.getChildren().addAll(nameLabel, descriptionLabel);

        // Rarity and Percentage
        VBox rarityBox = new VBox(5);
        rarityBox.setAlignment(Pos.CENTER);
        Label rarityLabel = new Label(achievement.calculateRarity());
        rarityLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        switch (achievement.calculateRarity()) {
            case "Common":
                rarityLabel.setTextFill(Color.LIGHTGRAY);
                break;
            case "Uncommon":
                rarityLabel.setTextFill(Color.LIGHTGREEN);
                break;
            case "Rare":
                rarityLabel.setTextFill(Color.DODGERBLUE);
                break;
            case "Ultra Rare":
                rarityLabel.setTextFill(Color.MEDIUMPURPLE);
                break;
        }

        Label percentageLabel = new Label(achievement.getAchievementCompletionPercentage() + "% unlocked");
        percentageLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #aaa;");

        rarityBox.getChildren().addAll(rarityLabel, percentageLabel);

        card.getChildren().addAll(imageView, infoBox, rarityBox);
        HBox.setHgrow(infoBox, javafx.scene.layout.Priority.ALWAYS);
    }

    public HBox getCard() {
        return card;
    }
}
