package com.csc207.group.Review;

import com.csc207.group.model.Review;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class ReviewView extends Application {

    private List<Review> reviewList;

    public void setReviews(java.util.List<Review> reviews) {
        this.reviewList = reviews;
    }

    public void start(Stage stage) {
        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-padding: 5; -fx-background-color: #222;");
        vbox.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("Game Reviews");
        title.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label subtitle = new Label("See what others have to say, and tell others what you think!");
        subtitle.setStyle("-fx-font-size: 16px; -fx-text-fill: #ccc;");

        GridPane reviews = new GridPane();
        reviews.setVgap(10);
        reviews.setHgap(10);
        reviews.setStyle("-fx-background-color: #222");
        reviews.setAlignment(Pos.TOP_CENTER);

        Integer i = 0;
        for (Review review : reviewList) {
            reviews.add(new ReviewCard(review).getCard(),i%2,i/2);
            i++;
        }

        ScrollPane curreviews = new ScrollPane(reviews);
        curreviews.setFitToWidth(true);
        curreviews.setPadding(new Insets(20));
        curreviews.setPrefHeight(750);
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
        leave.setMinWidth(1600);
        leave.setMinHeight(60);
        leave.setStyle("-fx-font-size: 14");

        TextField rating = new TextField();
        rating.setPromptText("Rating");

        Button post = new Button("Post");
        post.setStyle("-fx-background-color: #222; -fx-font-size: 16; -fx-background-radius: 10; -fx-border-radius: 10; -fx-text-fill: white; -fx-border-color: white; -fx-border-width: 4");

        HBox postsys = new HBox(25);
        postsys.setPrefSize(1750,80);
        postsys.setAlignment(Pos.CENTER);
        postsys.getChildren().addAll(leave,post);

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
                    reviewList.add(new Review("newuser",leave.getText(),1234, 4.5));
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
