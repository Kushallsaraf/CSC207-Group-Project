package com.csc207.group.ui.controller;

import com.csc207.group.app.GameCentralController;
import com.csc207.group.model.GameRecommendation;
import com.csc207.group.model.Recommendation;
import com.csc207.group.service.recommendation.RecommendationEngine;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import com.csc207.group.model.GamePreview;
import com.csc207.group.service.GameService;
import com.csc207.group.service.UserInteractor;
import com.csc207.group.ui.HomeView;

import java.util.ArrayList;
import java.util.List;

public class HomeController {

    private final HomeView view;
    private final GameService gameService;
    private final UserInteractor userInteractor;
    private final UserProfileController userProfileController;
    private final RecommendationEngine recommendationEngine;
    private final GameCentralController gameCentralController;


    public HomeController(HomeView view, GameService gameService, UserInteractor userInteractor,
                          UserProfileController userProfileController,
                          RecommendationEngine engine, GameCentralController gameCentralController) {
        this.view = view;
        this.gameService = gameService;
        this.userInteractor = userInteractor;
        this.userProfileController = userProfileController;
        this.recommendationEngine = engine;
        this.gameCentralController = gameCentralController;

        view.setSearchButtonHandler(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleSearch();
            }
        });
    }

    public void setRecommendations(){
        List<Node> nodes = new ArrayList<Node>();

        Recommendation recommendation = recommendationEngine.generateRecommendation();
        if (recommendation == null) {
            view.setRecommendations(nodes, "no recommendation");
            return;
        }

        List<GameRecommendation> recs = recommendation.getRecommendations();
        if (recs == null || recs.isEmpty()) {
            view.setRecommendations(nodes, "no recommendations");
            return;
        }

        for (int i = 0; i < recs.size(); i++) {
            GameRecommendation r = recs.get(i);
            javafx.scene.Node card = buildRecommendationNode(r);
            if (card != null) {
                nodes.add(card);
            }
        }

        view.setRecommendations(nodes, recommendation.getMessage());



    }

    private Node buildRecommendationNode(GameRecommendation recommendation){
        javafx.scene.layout.VBox card = new javafx.scene.layout.VBox(6);
        card.setAlignment(javafx.geometry.Pos.TOP_CENTER);
        card.setStyle("-fx-background-color: #333; -fx-padding: 8; -fx-background-radius: 10;");
        card.setPrefWidth(150);
        card.setMaxWidth(150);

        // --- Cover image ---
        javafx.scene.image.ImageView iv = new javafx.scene.image.ImageView(recommendation.getCoverImage());
        iv.setFitWidth(134);   // fits nicely inside card width
        iv.setFitHeight(135);  // reduced ~25% from original 180
        iv.setPreserveRatio(true);
        iv.setSmooth(true);
        iv.setCache(true);

        javafx.scene.layout.StackPane imgWrap = new javafx.scene.layout.StackPane(iv);
        imgWrap.setStyle("-fx-background-color: #222; -fx-background-radius: 8;");
        imgWrap.setPadding(new javafx.geometry.Insets(4));

        // --- Title + year ---
        String title = recommendation.getTitle();
        String year = String.valueOf(recommendation.getYear());
        Label titleLbl = new Label(title + " (" + year + ")");
        titleLbl.setStyle("-fx-text-fill: #fff; -fx-font-size: 12px; -fx-font-weight: bold;");
        titleLbl.setWrapText(true);
        titleLbl.setMaxWidth(140);

        // --- Numeric rating only ---
        double raw = recommendation.getRating(); // Assuming already 0–100 or 0–10


        javafx.scene.control.Label numeric = new javafx.scene.control.Label(String.valueOf(raw));
        numeric.setStyle("-fx-text-fill: #bbb; -fx-font-size: 11px;");

        // Assemble
        card.getChildren().addAll(imgWrap, titleLbl, numeric);
        card.setCursor(Cursor.HAND);
        card.setOnMouseEntered(e -> {
            card.setStyle("-fx-background-color: #333; -fx-padding: 8; -fx-background-radius: 10; "
                    + "-fx-border-color: #00ffff; -fx-border-width: 2; -fx-border-radius: 10; "
                    + "-fx-effect: dropshadow(gaussian, #00ffff, 10, 0.8, 0, 0);");
            card.setCursor(Cursor.HAND);
        });

        card.setOnMouseExited(e -> {
            card.setStyle("-fx-background-color: #333; -fx-padding: 8; -fx-background-radius: 10;");
            card.setCursor(Cursor.DEFAULT);
        });

        card.setOnMouseClicked(new GamePreviewClickHandler(gameCentralController));

        return card;


    }



    private void handleSearch() {
        String query = view.getSearchQuery();
        if (!query.isEmpty()) {
            List<Integer> gameIds = gameService.searchGame(query);

            List<GamePreview> previews = new ArrayList<>();
            for (Integer id : gameIds) {
                GamePreview preview = gameService.getGamePreviewById(id);
                if (preview != null) {
                    previews.add(preview);
                }
            }

            List<Node> previewNodes = new ArrayList<>();
            for (GamePreview game : previews) {
                previewNodes.add(createGamePreviewNode(game));
            }

            view.displayGameResults(previewNodes);
        }
    }

    private Node createGamePreviewNode(GamePreview game) {
        ImageView imageView = new ImageView(game.getCoverImage());
        imageView.setFitHeight(100);
        imageView.setFitWidth(75);
        imageView.setPreserveRatio(true);

        Label titleLabel = new Label(game.getTitle() + " (" + game.getYear() + ")");
        titleLabel.setFont(Font.font(16));

        VBox infoBox = new VBox(5, titleLabel);

        Button libraryButton = new Button();
        if (userInteractor.isInLibrary(game.getGameid())) {
            libraryButton.setText("Remove from Library");
        } else {
            libraryButton.setText("Add to Library");
        }
        libraryButton.setUserData(game);
        libraryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleLibraryButtonClick(event);
            }
        });

        Button wishlistButton = new Button();
        if (userInteractor.isInWishlist(game.getGameid())) {
            wishlistButton.setText("Remove from Wishlist");
        } else {
            wishlistButton.setText("Add to Wishlist");
        }
        wishlistButton.setUserData(game);
        wishlistButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                handleWishlistButtonClick(actionEvent);
            }
        });

        VBox buttonBox = new VBox(5, libraryButton, wishlistButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        HBox previewBox = new HBox(15, imageView, infoBox, buttonBox);
        previewBox.setPadding(new Insets(10));
        previewBox.setStyle("-fx-border-color: lightgray; -fx-background-color: #222;");


        previewBox.setAlignment(Pos.CENTER_LEFT);
        previewBox.setCursor(Cursor.HAND);
        // Glow effect + click handler
        previewBox.setOnMouseEntered(e -> {
            previewBox.setCursor(Cursor.HAND);
            previewBox.setStyle(
                    "-fx-border-color: #00ffff; " + // cyan border
                            "-fx-border-width: 2; " +
                            "-fx-background-color: #222; " +
                            "-fx-effect: dropshadow(gaussian, #00ffff, 10, 0.8, 0, 0);"
            );
        });

        previewBox.setOnMouseExited(e -> {
            previewBox.setCursor(Cursor.DEFAULT);
            previewBox.setStyle("-fx-border-color: lightgray; -fx-background-color: #222;");
        });

// Click event to open game page
        previewBox.setOnMouseClicked(new GamePreviewClickHandler(gameCentralController));


        return previewBox;
    }

    private void handleLibraryButtonClick(ActionEvent event) {
        Button button = (Button) event.getSource();
        GamePreview game = (GamePreview) button.getUserData();
        int gameid = game.getGameid();

        if (userInteractor.isInLibrary(gameid)) {
            userInteractor.removeFromLibrary(gameid);
            button.setText("Add to Library");
        } else {
            userInteractor.addToLibrary(gameid);
            button.setText("Remove from Library");
        }
        userProfileController.refresh();
    }

    private void handleWishlistButtonClick(ActionEvent event) {
        Button button = (Button) event.getSource();
        GamePreview game = (GamePreview) button.getUserData();
        int gameid = game.getGameid();

        if (userInteractor.isInWishlist(gameid)) {
            userInteractor.removeFromWishlist(gameid);
            button.setText("Add to Wishlist");
        } else {
            userInteractor.addToWishlist(gameid);
            button.setText("Remove from Wishlist");
        }
    }
}