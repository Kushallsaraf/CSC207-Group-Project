package com.csc207.group.views;

import com.csc207.group.views.Components.DLCcard;
import javafx.animation.PauseTransition;
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
import javafx.util.Duration;

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

    private HBox DlcBox;
    private ScrollPane DlcScrollPane;

    private Consumer<String> developerClickHandler = s -> {};

    private TextArea reviewArea;
    private TextField ratingField;
    private Button submitReviewButton;
    private VBox reviewsContainer;
    private VBox achievementsContainer;
    private Button btnPhotos;
    private FlowPane screenshotContainer;
    private ScrollPane screenshotScrollPane;

    // Buttons and Label from the first version
    private Button buyButton;
    private Button addButton;
    private Button moreButton;
    private Label confirmationLabel;

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

        content = new VBox(CONTENT_SPACING);
        content.setStyle("-fx-background-color: #1a1a1a; -fx-padding: 30;");
        content.setAlignment(Pos.TOP_CENTER);

        scroll.setContent(content);
        VBox.setVgrow(scroll, Priority.ALWAYS);

        buildUI();

        this.getChildren().setAll(scroll);
    }

    private void buildUI() {
        // --- Header ---
        HBox headerSection = new HBox(CONSTANT_6);
        headerSection.setAlignment(Pos.CENTER_LEFT);

        VBox titleAndTags = new VBox(CONSTANT_3);
        title = new Label(titleStr != null ? titleStr : "Title");
        title.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: white;");

        tagsBox = new HBox(CONSTANT_4);
        updateTags();
        titleAndTags.getChildren().addAll(title, tagsBox);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox userScoreBox = new HBox(CONSTANT_5);
        userScoreBox.setAlignment(Pos.CENTER);
        scoreLabel = new Label(userScore > 0 ? "Critic Rating: " + userScore + "/100" : "User Score: --/100");
        scoreLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

        starIcon = new SVGPath();
        starIcon.setContent("M12 17.27L18.18 21l-1.64-7.03L22 9.24l-7.19-.61L12 2 9.19 8.63 2 9.24l5.46 4.73L5.82 21z");
        starIcon.setFill(Color.GOLD);

        userScoreBox.getChildren().addAll(scoreLabel, starIcon);
        headerSection.getChildren().addAll(titleAndTags, spacer, userScoreBox);

        // --- Main (images + actions) ---
        HBox mainContent = new HBox(CONSTANT_1);

        HBox imagesBox = new HBox(CONSTANT_2);
        image1 = new ImageView();
        image1.setPreserveRatio(true);
        image1.setFitHeight(IMAGE_HEIGHT);
        image2 = new ImageView();
        image2.setPreserveRatio(true);
        image2.setFitHeight(IMAGE_HEIGHT);
        updateImages();
        imagesBox.getChildren().addAll(image1, image2);

        actionButtons = new VBox(CONSTANT_3);
        actionButtons.setAlignment(Pos.CENTER);
        btnPhotos = new Button("View All Photos");
        String actionButtonStyle = "-fx-background-color: #333; -fx-text-fill: white; -fx-font-size: 14px; -fx-pref-width: 150px; -fx-padding: 10;";
        btnPhotos.setStyle(actionButtonStyle);
        actionButtons.getChildren().addAll(btnPhotos);

        // --- Screenshot Display Area ---
        screenshotContainer = new FlowPane();
        screenshotContainer.setPadding(new Insets(CONSTANT_3));
        screenshotContainer.setVgap(CONSTANT_3);
        screenshotContainer.setHgap(CONSTANT_3);
        screenshotContainer.setStyle("-fx-background-color: #222;");

        screenshotScrollPane = new ScrollPane(screenshotContainer);
        screenshotScrollPane.setFitToWidth(true);
        screenshotScrollPane.setPrefHeight(IMAGE_HEIGHT);
        screenshotScrollPane.setVisible(false);
        screenshotScrollPane.setStyle("-fx-background: #222; -fx-background-color: #222;");

        mainContent.getChildren().addAll(imagesBox, actionButtons, screenshotScrollPane);
        HBox.setHgrow(screenshotScrollPane, Priority.ALWAYS);

        // --- Synopsis ---
        synopsisBox = createSection("Synopsis:", synopsisStr != null ? synopsisStr : "No synopsis available.");

        // --- Overview ---
        overviewBox = createOverviewSection();

        // --- DLCs ---
        DlcBox = new HBox(CONSTANT_3);
        DlcScrollPane = createDLCSection(DlcBox);

        // --- Achievements ---
        Label achievementsHeader = new Label("Achievements");
        achievementsHeader.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");
        achievementsContainer = new VBox(CONSTANT_3);
        ScrollPane achievementsScroll = new ScrollPane(achievementsContainer);
        achievementsScroll.setFitToWidth(true);
        achievementsScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        achievementsScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        achievementsScroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        achievementsScroll.setPrefHeight(CONSTANT_7);
        VBox achievementsSection = new VBox(CONSTANT_3, achievementsHeader, achievementsScroll);

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
        VBox reviewInputBox = new VBox(CONSTANT_4, reviewLabel, ratingField, reviewArea, submitReviewButton);
        reviewInputBox.setAlignment(Pos.CENTER_LEFT);

        // --- Reviews list ---
        Label reviewsHeader = new Label("Reviews");
        reviewsHeader.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");
        reviewsContainer = new VBox(CONSTANT_3);
        reviewsContainer.setAlignment(Pos.TOP_LEFT);
        VBox reviewsSection = new VBox(CONSTANT_3, reviewsHeader, reviewsContainer);
        reviewsSection.setAlignment(Pos.CENTER_LEFT);

        // --- Footer ---
        footerButtons = new HBox(CONSTANT_6);
        footerButtons.setAlignment(Pos.CENTER);
        buyButton = new Button("Buy Now");
        addButton = new Button("Add to Library");
        moreButton = new Button("More Like This");
        String footerButtonStyle = "-fx-background-color: #555; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;";
        buyButton.setStyle(footerButtonStyle);
        addButton.setStyle(footerButtonStyle);
        moreButton.setStyle(footerButtonStyle);
        footerButtons.getChildren().addAll(buyButton, addButton, moreButton);

        // --- Confirmation Label ---
        confirmationLabel = new Label();
        confirmationLabel.setStyle("-fx-text-fill: #2ecc71; -fx-font-size: 14px; -fx-font-weight: bold;");
        confirmationLabel.setVisible(false);

        // Add all components to the main content VBox
        content.getChildren().setAll(
                headerSection,
                mainContent,
                synopsisBox,
                overviewBox,
                DlcScrollPane,
                achievementsSection,
                reviewInputBox,
                reviewsSection,
                footerButtons,
                confirmationLabel
        );
    }

    // --- Getters from the first version ---
    public Button getBuyNowButton() {
        return buyButton;
    }

    public Button getAddButton() {
        return addButton;
    }

    public Button getMoreButton() {
        return moreButton;
    }

    public Button getViewPhotosButton() {
        return btnPhotos;
    }

    // --- Methods from both versions, merged ---
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
            screenshotScrollPane.setVisible(true);
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
        VBox section = new VBox(CONSTANT_5);
        Label header = new Label(title);
        header.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");
        Text body = new Text(content);
        body.setStyle("-fx-font-size: 14px; -fx-fill: #ccc;");
        body.setWrappingWidth(600);
        section.getChildren().addAll(header, body);
        return section;
    }

    private VBox createOverviewSection() {
        VBox section = new VBox(CONSTANT_3);
        Label header = new Label("Overview:");
        header.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");
        GridPane grid = new GridPane();
        grid.setHgap(CONSTANT_10);
        grid.setVgap(CONSTANT_3);
        grid.add(createOverviewRow("Developer:"), 0, 0);
        grid.add(createOverviewRow(developer != null ? developer : "Developer Unknown"), 1, 0);
        grid.add(createOverviewRow("Release Date:"), 0, 1);
        grid.add(createOverviewRow(releaseDate != null ? releaseDate : CONSTANT_9), 1, 1);
        grid.add(createOverviewRow("Platforms:"), 0, 2);
        grid.add(createOverviewRow(platforms != null ? platforms : CONSTANT_9), 1, 2);
        grid.add(createOverviewRow("Age Rating (ESRB):"), 0, CONSTANT_8);
        grid.add(createOverviewRow(ageRating != null ? ageRating : CONSTANT_9), 1, CONSTANT_8);
        section.getChildren().addAll(header, grid);
        return section;
    }

    private Label createOverviewRow(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 14px; -fx-text-fill: #ccc;");
        return label;
    }

    private ScrollPane createDLCSection(HBox dlcBox) {
        ScrollPane scrollPane = new ScrollPane(dlcBox);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
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

    public void setDeveloperClickHandler(Consumer<String> handler) {
        this.developerClickHandler = handler != null ? handler : s -> {};
        rebuildOverview();
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
        DlcBox.getChildren().clear();
        for (VBox dlc : dlcs) {
            DlcBox.getChildren().add(dlc);
        }
        DlcBox.setStyle("-fx-background-color: #1a1a1a; -fx-padding: 30;");
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

    public void showConfirmation(String message) {
        confirmationLabel.setText(message);
        confirmationLabel.setVisible(true);

        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(event -> confirmationLabel.setVisible(false));
        delay.play();
    }

    public void showStorePopup(List<GameStore> stores, HostServices hostServices) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Available Stores");

        VBox popupLayout = new VBox(10);
        popupLayout.setPadding(new Insets(15));
        popupLayout.setStyle("-fx-background-color: #2a2a2a;");

        if (stores == null || stores.isEmpty()) {
            Label noStoresLabel = new Label("No online stores found for this game.");
            noStoresLabel.setStyle("-fx-text-fill: white;");
            popupLayout.getChildren().add(noStoresLabel);
        } else {
            for (GameStore store : stores) {
                Button storeButton = new Button("Buy on " + store.getStoreName());
                storeButton.setStyle("-fx-background-color: #555; -fx-text-fill: white; -fx-font-weight: bold;");
                storeButton.setOnAction(e -> {
                    hostServices.showDocument(store.getStoreUrl());
                    popupStage.close();
                });
                popupLayout.getChildren().add(storeButton);
            }
        }

        Scene popupScene = new Scene(popupLayout);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }
}