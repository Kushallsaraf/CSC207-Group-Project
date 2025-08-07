package com.csc207.group.Review;

import com.csc207.group.model.Review;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class ReviewCard {
    private VBox card;
    public ReviewCard(Review review) {
        card = new VBox(20);
        card.setPrefSize(750,250);
        card.setAlignment(Pos.TOP_LEFT);
        card.setPadding(new Insets(15));

        Label user = new Label(review.getUserid());
        Label areview = new Label(review.getContent());
        user.setFont(Font.font("Bahnschrift", FontWeight.BOLD, 14));
        areview.setFont(Font.font("Bahnschrift", FontWeight.NORMAL, 12));
        user.setStyle("-fx-text-fill: #666;");
        areview.setStyle("-fx-text-fill: #666;");

        SVGPath starIcon = new SVGPath();
        starIcon.setContent("M12 17.27L18.18 21l-1.64-7.03L22 9.24l-7.19-.61L12 2 9.19 8.63 2 9.24l5.46 4.73L5.82 21z");
        starIcon.setFill(Color.GOLD);

        Label rating = new Label(review.getRating() + "/5");
        rating.setFont(Font.font("Bahnschrift", FontWeight.NORMAL, 12));
        rating.setStyle("-fx-text-fill: #666;");

        HBox rateset = new HBox(7);
        rateset.setAlignment(Pos.CENTER_LEFT);
        rateset.getChildren().addAll(starIcon,rating);

        card.getChildren().addAll(user,areview,rateset);
        card.setStyle("-fx-background-color: #222; -fx-border-color: Black; -fx-border-width: 6; -fx-border-radius: 20; -fx-background-radius: 20");

    }
    public VBox getCard() {
        return card;
    }
}