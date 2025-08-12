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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import com.csc207.group.model.GamePreview;
import com.csc207.group.model.User;
import com.csc207.group.service.GameService;
import com.csc207.group.service.UserInteractor;
import com.csc207.group.ui.HomeView;

import java.util.ArrayList;
import java.util.List;

public class HomeController {

    private final HomeView view;
    private final GameService gameService;
    private final UserInteractor userInteractor;
    private final PersonalProfileController personalProfileController;
    private final RecommendationEngine recommendationEngine;
    private final GameCentralController gameCentralController;

    public HomeController(HomeView view, GameService gameService, UserInteractor userInteractor,
                          PersonalProfileController personalProfileController,
                          RecommendationEngine engine, GameCentralController gameCentralController) {
        this.view = view;
        this.gameService = gameService;
        this.userInteractor = userInteractor;
        this.personalProfileController = personalProfileController;
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
        List<Node> nodes = new ArrayList<>();
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
        for (GameRecommendation r : recs) {
            Node card = buildRecommendationNode(r);
            if (card != null) nodes.add(card);
        }
        view.setRecommendations(nodes, recommendation.getMessage());
    }

    private Node buildRecommendationNode(GameRecommendation recommendation){
        VBox card = new VBox(6);
        card.setAlignment(Pos.TOP_CENTER);
        card.setStyle("-fx-background-color: #333; -fx-padding: 8; -fx-background-radius: 10;");
        card.setPrefWidth(150);
        card.setMaxWidth(150);

        ImageView iv = new ImageView(recommendation.getCoverImage());
        iv.setFitWidth(134);
        iv.setFitHeight(135);
        iv.setPreserveRatio(true);
        iv.setSmooth(true);
        iv.setCache(true);

        StackPane imgWrap = new StackPane(iv);
        imgWrap.setStyle("-fx-background-color: #222; -fx-background-radius: 8;");
        imgWrap.setPadding(new Insets(4));

        String title = recommendation.getTitle();
        String year = String.valueOf(recommendation.getYear());
        Label titleLbl = new Label(title + " (" + year + ")");
        titleLbl.setStyle("-fx-text-fill: #fff; -fx-font-size: 12px; -fx-font-weight: bold;");
        titleLbl.setWrapText(true);
        titleLbl.setMaxWidth(140);

        Label numeric = new Label(String.valueOf(recommendation.getRating()));
        numeric.setStyle("-fx-text-fill: #bbb; -fx-font-size: 11px;");

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
        card.setUserData(recommendation.getGameid());
        return card;
    }

    private void handleSearch() {
        String query = view.getSearchQuery();
        if (query == null || query.trim().isEmpty()) {
            view.displayGameResults(new ArrayList<>());
            return;
        }
        query = query.trim();

        List<Node> resultNodes = new ArrayList<>();

        // (Optional) show exact user match first
        User exactUser = userInteractor.getUserByUsername(query);
        if (exactUser != null) {
            resultNodes.add(createUserResultNode(exactUser));
        }

        // Game results
        List<Integer> gameIds = gameService.searchGame(query);
        for (Integer id : gameIds) {
            GamePreview preview = gameService.getGamePreviewById(id);
            if (preview != null) {
                resultNodes.add(createGamePreviewNode(preview));
            }
        }

        view.displayGameResults(resultNodes);
    }

    private Node createUserResultNode(User u) {
        HBox row = new HBox(12);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(10));
        row.setStyle("-fx-border-color: lightgray; -fx-background-color: #1e1e1e; " +
                "-fx-background-radius: 6; -fx-border-radius: 6;");

        ImageView avatar = new ImageView();
        avatar.setFitWidth(48);
        avatar.setFitHeight(48);
        avatar.setPreserveRatio(false);
        String avatarUrl = u.getProfilePictureURL();
        if (avatarUrl != null && !avatarUrl.isEmpty()) {
            try { avatar.setImage(new Image(avatarUrl, true)); } catch (Exception ignored) {}
        }

        Label name = new Label(u.getUsername());
        name.setStyle("-fx-text-fill: #fff; -fx-font-size: 16px; -fx-font-weight: bold;");
        VBox info = new VBox(4, name);
        info.setAlignment(Pos.CENTER_LEFT);

        row.getChildren().addAll(avatar, info);

        // Hover + click → open profile (avoid navigating to your own profile view)
        row.setOnMouseEntered(e -> {
            row.setCursor(Cursor.HAND);
            row.setStyle("-fx-border-color: #00ffff; -fx-border-width: 2; -fx-background-color: #1e1e1e; " +
                    "-fx-background-radius: 6; -fx-border-radius: 6; " +
                    "-fx-effect: dropshadow(gaussian, #00ffff, 10, 0.8, 0, 0);");
        });
        row.setOnMouseExited(e -> {
            row.setCursor(Cursor.DEFAULT);
            row.setStyle("-fx-border-color: lightgray; -fx-background-color: #1e1e1e; " +
                    "-fx-background-radius: 6; -fx-border-radius: 6;");
        });

        String loggedIn = userInteractor.getUser().getUsername();
        row.setOnMouseClicked(e -> {
            if (!u.getUsername().equals(loggedIn)) {
                gameCentralController.showUserProfileView(u.getUsername());
            } else {
                // If you prefer to jump to your own profile:
                // gameCentralController.showPersonalProfileView();
            }
        });

        row.setUserData(u.getUsername()); // optional
        return row;
    }


    // ----------------- UPDATED: search now returns games + users -----------------

    // -----------------------------------------------------------------------------

    private Node createGamePreviewNode(GamePreview game) {
        ImageView imageView = new ImageView(game.getCoverImage());
        imageView.setFitHeight(100);
        imageView.setFitWidth(75);
        imageView.setPreserveRatio(true);

        Label titleLabel = new Label(game.getTitle() + " (" + game.getYear() + ")");
        titleLabel.setFont(Font.font(16));

        VBox infoBox = new VBox(5, titleLabel);

        Button libraryButton = new Button(userInteractor.isInLibrary(game.getGameid())
                ? "Remove from Library" : "Add to Library");
        libraryButton.setUserData(game);
        libraryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) { handleLibraryButtonClick(event); }
        });

        Button wishlistButton = new Button(userInteractor.isInWishlist(game.getGameid())
                ? "Remove from Wishlist" : "Add to Wishlist");
        wishlistButton.setUserData(game);
        wishlistButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) { handleWishlistButtonClick(actionEvent); }
        });

        VBox buttonBox = new VBox(5, libraryButton, wishlistButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        HBox previewBox = new HBox(15, imageView, infoBox, buttonBox);
        previewBox.setPadding(new Insets(10));
        previewBox.setStyle("-fx-border-color: lightgray; -fx-background-color: #222;");
        previewBox.setAlignment(Pos.CENTER_LEFT);
        previewBox.setCursor(Cursor.HAND);

        previewBox.setOnMouseEntered(e -> {
            previewBox.setCursor(Cursor.HAND);
            previewBox.setStyle("-fx-border-color: #00ffff; -fx-border-width: 2; -fx-background-color: #222; "
                    + "-fx-effect: dropshadow(gaussian, #00ffff, 10, 0.8, 0, 0);");
        });
        previewBox.setOnMouseExited(e -> {
            previewBox.setCursor(Cursor.DEFAULT);
            previewBox.setStyle("-fx-border-color: lightgray; -fx-background-color: #222;");
        });
        previewBox.setOnMouseClicked(new GamePreviewClickHandler(gameCentralController));
        previewBox.setUserData(game.getGameid());

        return previewBox;
    }

    // ---------- NEW: render a user search result ----------

    // ------------------------------------------------------

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
        personalProfileController.refresh();
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
