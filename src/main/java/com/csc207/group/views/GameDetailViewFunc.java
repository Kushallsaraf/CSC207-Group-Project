package com.csc207.group.views;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GameDetailViewFunc extends VBox {
    private Label title;
    private HBox tagsBox;
    private Label scoreLabel;
    private SVGPath starIcon;
    private ImageView image1;
    private ImageView image2;
    private VBox actionButtons;
    private VBox synopsisBox;
    private VBox overviewBox;
    private HBox footerButtons;

    // Game info variables
    private String developer;
    private String releaseDate;
    private String platforms;
    private String ageRating;
    private String synopsisStr;
    private String titleStr;
    private List<String> tags = new ArrayList<>();
    private double userScore; // e.g. 4.2
    private Image imageUrl1;
    private Image imageUrl2;

    // Developer click handler, default to no-op
    private Consumer<String> developerClickHandler = s -> {};

    public GameDetailViewFunc() {
        this.setSpacing(25);
        this.setStyle("-fx-background-color: #1a1a1a; -fx-padding: 30;");
        this.setAlignment(Pos.TOP_CENTER);

        buildUI();
    }

    public void setDeveloperClickHandler(Consumer<String> handler) {
        this.developerClickHandler = handler != null ? handler : s -> {};
        rebuildOverview();
    }

    private void buildUI() {
        // Header Section
        HBox headerSection = new HBox(20);
        headerSection.setAlignment(Pos.CENTER_LEFT);

        VBox titleAndTags = new VBox(10);
        title = new Label(titleStr != null ? titleStr : "Title");
        title.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: white;");

        tagsBox = new HBox(8);
        updateTags();

        titleAndTags.getChildren().addAll(title, tagsBox);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox userScoreBox = new HBox(5);
        userScoreBox.setAlignment(Pos.CENTER);
        scoreLabel = new Label(userScore > 0 ? "Critic Rating: " + userScore + "/100" : "User Score: --/100");
        scoreLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

        starIcon = new SVGPath();
        starIcon.setContent("M12 17.27L18.18 21l-1.64-7.03L22 9.24l-7.19-.61L12 2 9.19 8.63 2 9.24l5.46 4.73L5.82 21z");
        starIcon.setFill(Color.GOLD);

        userScoreBox.getChildren().addAll(scoreLabel, starIcon);

        headerSection.getChildren().addAll(titleAndTags, spacer, userScoreBox);

        HBox mainContent = new HBox(30);

        HBox imagesBox = new HBox(15);
        image1 = new ImageView();
        image1.setPreserveRatio(true);
        image1.setFitHeight(250);
        image2 = new ImageView();
        image2.setPreserveRatio(true);
        image2.setFitHeight(250);

        updateImages();

        imagesBox.getChildren().addAll(image1, image2);

        actionButtons = new VBox(10);
        actionButtons.setAlignment(Pos.CENTER);
        Button btnPhotos = new Button("View All Photos");
        Button btnVideos = new Button("View All Videos");
        Button btnReviews = new Button("View User Reviews");
        String actionButtonStyle = "-fx-background-color: #333; -fx-text-fill: white; -fx-font-size: 14px; -fx-pref-width: 150px; -fx-padding: 10;";
        btnPhotos.setStyle(actionButtonStyle);
        btnVideos.setStyle(actionButtonStyle);
        btnReviews.setStyle(actionButtonStyle);
        actionButtons.getChildren().addAll(btnPhotos, btnVideos, btnReviews);

        mainContent.getChildren().addAll(imagesBox, actionButtons);

        // Synopsis Section
        synopsisBox = createSection("Synopsis:", synopsisStr != null ? synopsisStr : "No synopsis available.");

        // Overview Section
        overviewBox = createOverviewSection();

        // Footer Buttons
        footerButtons = new HBox(20);
        footerButtons.setAlignment(Pos.CENTER);
        Button buyButton = new Button("Buy Now");
        Button addButton = new Button("Add to Library");
        Button moreButton = new Button("More Like This");
        String footerButtonStyle = "-fx-background-color: #555; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;";
        buyButton.setStyle(footerButtonStyle);
        addButton.setStyle(footerButtonStyle);
        moreButton.setStyle(footerButtonStyle);
        footerButtons.getChildren().addAll(buyButton, addButton, moreButton);

        this.getChildren().setAll(headerSection, mainContent, synopsisBox, overviewBox, footerButtons);
    }

    private void updateTags() {
        tagsBox.getChildren().clear();
        for (String tagText : tags) {
            Label tag = new Label(" " + tagText + " ");
            tag.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-size: 12px; -fx-padding: 4 8; -fx-background-radius: 5;");
            tagsBox.getChildren().add(tag);
        }
    }

    private void updateImages() {
        if (imageUrl1 != null) image1.setImage(imageUrl1);
        if (imageUrl2 != null) image2.setImage(imageUrl2);
    }

    private VBox createSection(String title, String content) {
        VBox section = new VBox(5);
        Label header = new Label(title);
        header.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");

        Text body = new Text(content);
        body.setStyle("-fx-font-size: 14px; -fx-fill: #ccc;");
        body.setWrappingWidth(600);

        section.getChildren().addAll(header, body);
        return section;
    }

    private VBox createOverviewSection() {
        VBox section = new VBox(10);
        Label header = new Label("Overview:");
        header.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");

        GridPane grid = new GridPane();
        grid.setHgap(40);
        grid.setVgap(10);

        grid.add(createOverviewRow("Developer:"), 0, 0);
        grid.add(createOverviewRow(developer != null ? developer : "Developer Unknown"), 1, 0);
        grid.add(createOverviewRow("Release Date:"), 0, 1);
        grid.add(createOverviewRow(releaseDate != null ? releaseDate : "--"), 1, 1);
        grid.add(createOverviewRow("Platforms:"), 0, 2);
        grid.add(createOverviewRow(platforms != null ? platforms : "--"), 1, 2);
        grid.add(createOverviewRow("Age Rating (ESRB):"), 0, 3);
        grid.add(createOverviewRow(ageRating != null ? ageRating : "--"), 1, 3);

        section.getChildren().addAll(header, grid);
        return section;
    }

    private Label createOverviewRow(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 14px; -fx-text-fill: #ccc;");
        return label;
    }

    private Label createClickableOverviewRow(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 14px; -fx-text-fill: #A0C5E5; -fx-underline: true;");
        return label;
    }

    private void rebuildOverview() {
        if (overviewBox != null) {
            this.getChildren().remove(overviewBox);
            overviewBox = createOverviewSection();
            int synopsisIndex = this.getChildren().indexOf(synopsisBox);
            this.getChildren().add(synopsisIndex + 1, overviewBox);
        }
    }

    // --- Setter methods to update data dynamically ---

    public void setTitle(String title) {
        this.titleStr = title;
        if (title != null && this.title != null) this.title.setText(title);
    }

    public void setTags(List<String> tags) {
        this.tags = tags != null ? tags : new ArrayList<>();
        if (tagsBox != null) updateTags();
    }

    public void setUserScore(double score) {
        this.userScore = score;
        if (scoreLabel != null) scoreLabel.setText("Critic Score: " + Math.round(score) + "/100");
    }

    public void setImages(Image img1, Image img2) {
        this.imageUrl1 = img1;
        this.imageUrl2 = img2;
        if (image1 != null) image1.setImage(img1);
        if (image2 != null) image2.setImage(img2);
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
        rebuildOverview();
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
        rebuildOverview();
    }

    public void setPlatforms(String platforms) {
        this.platforms = platforms;
        rebuildOverview();
    }

    public void setAgeRating(String ageRating) {
        this.ageRating = ageRating;
        rebuildOverview();
    }

    public void setSynopsis(String synopsis) {
        this.synopsisStr = synopsis;
        if (synopsisBox != null) {
            synopsisBox.getChildren().clear();
            synopsisBox.getChildren().addAll(createSection("Synopsis:", synopsis).getChildren());
        }
    }
}