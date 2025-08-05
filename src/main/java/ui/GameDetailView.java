package ui;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;

public class GameDetailView {

    private VBox view;

    public GameDetailView() {
        // Main container
        view = new VBox(25);
        view.setStyle("-fx-background-color: #1a1a1a; -fx-padding: 30;");
        view.setAlignment(Pos.TOP_CENTER);

        // --- HEADER SECTION ---
        HBox headerSection = new HBox(20);
        headerSection.setAlignment(Pos.CENTER_LEFT);

        VBox titleAndTags = new VBox(10);
        Label title = new Label("Cyberpunk 2077");
        title.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: white;");

        HBox tags = new HBox(8);
        tags.getChildren().addAll(
                createTag("RPG"),
                createTag("Science Fiction"),
                createTag("Action-Adventure")
        );
        titleAndTags.getChildren().addAll(title, tags);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox userScore = new HBox(5);
        userScore.setAlignment(Pos.CENTER);
        Label scoreLabel = new Label("User Score: 4.2/5");
        scoreLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

        SVGPath starIcon = new SVGPath();
        starIcon.setContent("M12 17.27L18.18 21l-1.64-7.03L22 9.24l-7.19-.61L12 2 9.19 8.63 2 9.24l5.46 4.73L5.82 21z");
        starIcon.setFill(Color.GOLD);

        userScore.getChildren().addAll(scoreLabel, starIcon);
        headerSection.getChildren().addAll(titleAndTags, spacer, userScore);

        // --- MAIN CONTENT ---
        HBox mainContent = new HBox(30);

        HBox imagesBox = new HBox(15);
        ImageView image1 = new ImageView(new Image("https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/1091500/ss_9284d1c5b248726760233a933dbb83757d7d5d95.1920x1080.jpg", 250, 250, true, true));
        image1.setPreserveRatio(true);
        image1.setFitHeight(250);
        ImageView image2 = new ImageView(new Image("https://upload.wikimedia.org/wikipedia/en/9/9f/Cyberpunk_2077_box_art.jpg", 400, 250, true, true));
        image2.setPreserveRatio(true);
        image2.setFitHeight(250);
        imagesBox.getChildren().addAll(image1, image2);

        VBox actionButtons = new VBox(10);
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

        // --- SYNOPSIS ---
        VBox synopsisBox = createSection("Synopsis:", "Step into the shoes of V, a cyberpunk mercenary for hire and do what it takes to make a name for yourself in Night City, a megalopolis obsessed with power, glamour, and body modification. Legends are made here. What will yours be?");

        // --- OVERVIEW ---
        VBox overviewBox = createOverviewSection();

        // --- FOOTER BUTTONS ---
        HBox footerButtons = new HBox(20);
        footerButtons.setAlignment(Pos.CENTER);
        Button buyButton = new Button("Buy Now");
        Button addButton = new Button("Add to Library");
        Button moreButton = new Button("More Like This");
        String footerButtonStyle = "-fx-background-color: #555; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;";
        buyButton.setStyle(footerButtonStyle);
        addButton.setStyle(footerButtonStyle);
        moreButton.setStyle(footerButtonStyle);
        footerButtons.getChildren().addAll(buyButton, addButton, moreButton);


        view.getChildren().addAll(headerSection, mainContent, synopsisBox, overviewBox, footerButtons);
    }

    private Node createTag(String text) {
        Label tag = new Label(" " + text + " ");
        tag.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-size: 12px; -fx-padding: 4 8; -fx-background-radius: 5;");
        return tag;
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

        grid.add(createOverviewRow("MSRP:"), 0, 0);
        grid.add(createOverviewRow("$79.99"), 1, 0);
        grid.add(createOverviewRow("Developer:"), 0, 1);
        grid.add(createOverviewRow("CD Projekt Red"), 1, 1);
        grid.add(createOverviewRow("Release Date:"), 0, 2);
        grid.add(createOverviewRow("December 10, 2020"), 1, 2);
        grid.add(createOverviewRow("Platforms:"), 0, 3);
        grid.add(createOverviewRow("PC, PS4, PS5, Xbox Series, Xbox One, Switch 2"), 1, 3);
        grid.add(createOverviewRow("Age Rating (ESRB):"), 0, 4);
        grid.add(createOverviewRow("Mature 17+"), 1, 4);

        section.getChildren().addAll(header, grid);
        return section;
    }

    private Label createOverviewRow(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 14px; -fx-text-fill: #ccc;");
        return label;
    }

    public Pane getView() {
        return view;
    }
}