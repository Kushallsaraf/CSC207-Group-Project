package com.csc207.group.views;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;



import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GameDetailViewFunc extends VBox{
    VBox titleAndTags = new VBox(10);
    Label title;
    HBox genre = new HBox(10);
    List<Node> tags;

    HBox userScore = new HBox(5);
    Label scoreLabel = new Label();

    HBox mainContent = new HBox(30);
    ImageView imageView;

    String developer;
    String releaseDate;
    String platforms;
    String ageRating;
    String synopsisStr;

    VBox buttons = new VBox(10);
    Button wishlistButton = new Button("Add to Wishlist");
    Button libraryButton = new Button("Add to Library");

    VBox synopsis;
    VBox overview;


    public GameDetailViewFunc() {
        title = new Label();
        tags = new ArrayList<>();
        imageView = new ImageView();
        synopsis = new VBox();
        overview = new VBox();

        title.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: white;");
        genre.getChildren().addAll(tags);
        titleAndTags.getChildren().addAll(title, genre);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        userScore.setAlignment(Pos.CENTER);
        scoreLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
        SVGPath starIcon = new SVGPath();
        starIcon.setContent("M12 17.27L18.18 21l-1.64-7.03L22 9.24l-7.19-.61L12 2 9.19 8.63 2 9.24l5.46 4.73L5.82 21z");
        starIcon.setFill(Color.GOLD);
        userScore.getChildren().addAll(scoreLabel, starIcon);

        imageView.setPreserveRatio(true);
        imageView.setFitHeight(250);

        String actionButtonStyle = "-fx-background-color: #333; -fx-text-fill: white; -fx-font-size: 14px; -fx-pref-width: 150px; -fx-padding: 10;";
        wishlistButton.setStyle(actionButtonStyle);
        libraryButton.setStyle(actionButtonStyle);
        buttons.getChildren().addAll(wishlistButton, libraryButton);
        mainContent.getChildren().addAll(imageView, buttons);

        synopsis = createSection("Synopsis",synopsisStr);

        this.getChildren().addAll(titleAndTags, spacer, userScore, mainContent, synopsis, overview);
    }

    public Node createTag(String text) {
        javafx.scene.control.Label tag = new javafx.scene.control.Label(" " + text + " ");
        tag.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-size: 12px; -fx-padding: 4 8; -fx-background-radius: 5;");
        return tag;
    }

    public VBox createSection(String title, String content) {
        VBox section = new VBox(5);
        javafx.scene.control.Label header = new javafx.scene.control.Label(title);
        header.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");

        Text body = new Text(content);
        body.setStyle("-fx-font-size: 14px; -fx-fill: #ccc;");
        body.setWrappingWidth(600);

        section.getChildren().addAll(header, body);
        return section;
    }

    private VBox createOverviewSection(Consumer<String> developerClickHandler) {
        VBox section = new VBox(10);
        Label header = new Label("Overview:");
        header.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");

        GridPane grid = new GridPane();
        grid.setHgap(40);
        grid.setVgap(10);

        grid.add(createOverviewRow("Developer:"), 0, 0);
        grid.add(createOverviewRow(developer), 1, 0); // Add the clickable label
        grid.add(createOverviewRow("Release Date:"), 0, 1);
        grid.add(createOverviewRow(releaseDate), 1, 1);
        grid.add(createOverviewRow("Platforms:"), 0, 2);
        grid.add(createOverviewRow(platforms), 1, 2);
        grid.add(createOverviewRow("Age Rating (ESRB):"), 0, 3);
        grid.add(createOverviewRow(ageRating), 1, 3);

        section.getChildren().addAll(header, grid);
        return section;
    }

    public Label createOverviewRow(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 14px; -fx-text-fill: #ccc;");
        return label;
    }


}
