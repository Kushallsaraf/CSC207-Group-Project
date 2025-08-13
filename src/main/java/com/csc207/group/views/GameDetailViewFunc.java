package com.csc207.group.views;

import com.csc207.group.views.Components.DLCcard;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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

    private String developer;
    private String releaseDate;
    private String platforms;
    private String ageRating;
    private String synopsisStr;
    private String titleStr;
    private List<String> tags = new ArrayList<>();
    private double userScore;
    private Image imageUrl1;
    private Image imageUrl2;

    private HBox DLCbox;
    private ScrollPane DLCScrollPane;

    private Consumer<String> developerClickHandler = s -> {};

    private TextArea reviewArea;
    private TextField ratingField;
    private Button submitReviewButton;
    private VBox reviewsContainer;
    private VBox achievementsContainer;
    private Button btnPhotos;
    private FlowPane screenshotContainer;
    private ScrollPane screenshotScrollPane;
    private Button buyButton;


    // NEW: scroll container and content VBox
    private ScrollPane scroll;
    private VBox content;

    public GameDetailViewFunc() {
        this.setStyle("-fx-background-color: #1a1a1a;");
        this.setAlignment(Pos.TOP_CENTER);

        scroll = new ScrollPane();
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setStyle("-fx-background-color: transparent;");

        content = new VBox(25);
        content.setStyle("-fx-background-color: #1a1a1a; -fx-padding: 30;");
        content.setAlignment(Pos.TOP_CENTER);

        scroll.setContent(content);
        VBox.setVgrow(scroll, Priority.ALWAYS);

        buildUI();

        this.getChildren().setAll(scroll);
    }

    public void setDeveloperClickHandler(Consumer<String> handler) {
        this.developerClickHandler = handler != null ? handler : s -> {};
        rebuildOverview();
    }

    private void buildUI() {
        // --- Header ---
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

        // --- Main (images + actions) ---
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
        btnPhotos = new Button("View All Photos");
        Button btnReviews = new Button("View User Reviews");
        String actionButtonStyle = "-fx-background-color: #333; -fx-text-fill: white; -fx-font-size: 14px; -fx-pref-width: 150px; -fx-padding: 10;";
        btnPhotos.setStyle(actionButtonStyle);
        btnReviews.setStyle(actionButtonStyle);
        actionButtons.getChildren().addAll(btnPhotos, btnReviews);

        // --- Screenshot Display Area ---
        screenshotContainer = new FlowPane();
        screenshotContainer.setPadding(new Insets(10));
        screenshotContainer.setVgap(10);
        screenshotContainer.setHgap(10);
        screenshotContainer.setStyle("-fx-background-color: #222;");

        screenshotScrollPane = new ScrollPane(screenshotContainer);
        screenshotScrollPane.setFitToWidth(true);
        screenshotScrollPane.setPrefHeight(250); // Match image height
        screenshotScrollPane.setVisible(false); // Initially hidden
        screenshotScrollPane.setStyle("-fx-background: #222; -fx-background-color: #222;");

        mainContent.getChildren().addAll(imagesBox, actionButtons, screenshotScrollPane);
        HBox.setHgrow(screenshotScrollPane, Priority.ALWAYS); // Let it take available space

        // --- Synopsis ---
        synopsisBox = createSection("Synopsis:", synopsisStr != null ? synopsisStr : "No synopsis available.");

        // --- Overview ---
        overviewBox = createOverviewSection();

        // --- DLCs ---
        DLCbox = new HBox(10);
        DLCScrollPane = createDLCSection(DLCbox);

        // --- Achievements ---
        Label achievementsHeader = new Label("Achievements");
        achievementsHeader.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");
        achievementsContainer = new VBox(10);
        ScrollPane achievementsScroll = new ScrollPane(achievementsContainer);
        achievementsScroll.setFitToWidth(true);
        achievementsScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        achievementsScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        achievementsScroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        achievementsScroll.setPrefHeight(300); // Set a preferred height
        VBox achievementsSection = new VBox(10, achievementsHeader, achievementsScroll);

        // --- Review input ---
        Label reviewLabel = new Label("Your Review:");
        reviewLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
        ratingField = new TextField();
        ratingField.setPromptText("Enter a number from 1 to 5");
        reviewArea = new TextArea();
        reviewArea.setPromptText("Write your review here...");
        reviewArea.setWrapText(true);
        reviewArea.setPrefRowCount(4);
        submitReviewButton = new Button("Submit Review");
        submitReviewButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        VBox reviewInputBox = new VBox(8, reviewLabel, ratingField, reviewArea, submitReviewButton);
        reviewInputBox.setAlignment(Pos.CENTER_LEFT);

        // --- Reviews list ---
        Label reviewsHeader = new Label("Reviews");
        reviewsHeader.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");
        reviewsContainer = new VBox(10);
        reviewsContainer.setAlignment(Pos.TOP_LEFT);
        VBox reviewsSection = new VBox(10, reviewsHeader, reviewsContainer);
        reviewsSection.setAlignment(Pos.CENTER_LEFT);

        // --- Footer ---
        footerButtons = new HBox(20);
        footerButtons.setAlignment(Pos.CENTER);
        buyButton = new Button("Buy Now");
        Button addButton = new Button("Add to Library");
        Button moreButton = new Button("More Like This");
        String footerButtonStyle = "-fx-background-color: #555; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;";
        buyButton.setStyle(footerButtonStyle);
        addButton.setStyle(footerButtonStyle);
        moreButton.setStyle(footerButtonStyle);
        footerButtons.getChildren().addAll(buyButton, addButton, moreButton);

        // put EVERYTHING into the scrollable content
        content.getChildren().setAll(
                headerSection,
                mainContent,
                synopsisBox,
                overviewBox,
                DLCScrollPane,
                achievementsSection,
                reviewInputBox,
                reviewsSection,
                footerButtons
        );
    }

    public Button getBuyNowButton() {
        return buyButton;
    }

    public Button getViewPhotosButton() {
        return btnPhotos;
    }

    public void displayScreenshots(List<Image> images) {
        screenshotContainer.getChildren().clear();
        if (images != null && !images.isEmpty()) {
            for (Image img : images) {
                ImageView iv = new ImageView(img);
                iv.setFitHeight(150);
                iv.setPreserveRatio(true);
                iv.setSmooth(true);
                screenshotContainer.getChildren().add(iv);
            }
            screenshotScrollPane.setVisible(true);
        } else {
            Label noScreenshots = new Label("No screenshots available.");
            noScreenshots.setStyle("-fx-text-fill: #ccc;");
            screenshotContainer.getChildren().add(noScreenshots);
            screenshotScrollPane.setVisible(true); // Show the container with the message
        }
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

    private ScrollPane createDLCSection(HBox DLCbox) {

        ScrollPane scrollPane = new ScrollPane(DLCbox);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); // Always show horizontal bar
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);  // No vertical bar
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background-color: #1a1a1a; -fx-padding: 30;");
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.setPannable(true);



        return scrollPane;

    }

    private void rebuildOverview() {
        if (overviewBox != null) {
            int overviewIndex = content.getChildren().indexOf(overviewBox);
            if (overviewIndex != -1) {
                content.getChildren().remove(overviewIndex);
                overviewBox = createOverviewSection();
                content.getChildren().add(overviewIndex, overviewBox);
            }
        }
    }


    // --- Setters ---
    public void setTitle(String title) {
        this.titleStr = title;
        if (this.title != null) this.title.setText(title);
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
        if (synopsisBox != null && synopsisBox.getChildren().size() > 1 && synopsisBox.getChildren().get(1) instanceof Text) {
            Text body = (Text) synopsisBox.getChildren().get(1);
            body.setText(synopsis);
        }
    }

    public void setDLCs(List<VBox> dlcs) {
        DLCbox.getChildren().clear();
        for (VBox dlc : dlcs) {
            DLCbox.getChildren().add(dlc);
        }
        DLCbox.setStyle("-fx-background-color: #1a1a1a; -fx-padding: 30;");
    }

    public TextArea getReviewArea() {
        return reviewArea;
    }

    public TextField getRatingField() {
        return ratingField;
    }

    public Button getSubmitReviewButton() {
        return submitReviewButton;
    }

    public void setReviewNodes(List<Node> nodes) {
        reviewsContainer.getChildren().clear();
        if (nodes == null || nodes.isEmpty()) {
            reviewsContainer.getChildren().add(new Label("No reviews yet."));
        } else {
            reviewsContainer.getChildren().addAll(nodes);
        }
    }

    public void setAchievements(List<Node> achievementNodes) {
        achievementsContainer.getChildren().clear();
        if (achievementNodes == null || achievementNodes.isEmpty()) {
            Label noAchievements = new Label("No achievements found for this game.");
            noAchievements.setStyle("-fx-text-fill: #ccc;");
            achievementsContainer.getChildren().add(noAchievements);
        } else {
            achievementsContainer.getChildren().addAll(achievementNodes);
        }
    }

    public void clearReviewFields() {
        if (ratingField != null) ratingField.clear();
        if (reviewArea != null) reviewArea.clear();
    }
}