package com.csc207.group.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.csc207.group.app.GameCentralController;
import com.csc207.group.model.Game;
import com.csc207.group.model.GamePreview;
import com.csc207.group.model.GameRecommendation;
import com.csc207.group.model.Recommendation;
import com.csc207.group.model.User;
import com.csc207.group.service.GameService;
import com.csc207.group.service.GenreService;
import com.csc207.group.service.UserInteractor;
import com.csc207.group.service.recommendation.RecommendationEngine;
import com.csc207.group.ui.HomeView;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class HomeController {

    private static final int SPACING_SMALL = 6;
    private static final int CARD_WIDTH = 150;
    private static final int IMAGE_WIDTH = 134;
    private static final int IMAGE_HEIGHT = 135;
    private static final int TITLE_MAX_WIDTH = 140;
    private static final int IMAGE_PADDING = 4;
    private static final int AVATAR_SIZE = 48;
    private static final int USER_ROW_SPACING = 12;
    private static final int PREVIEW_BOX_SPACING = 10;
    private static final int BUTTON_SPACING = 5;
    private static final int TITLE_FONT_SIZE = 16;
    private static final int GAME_TITLE_SPACING = 16;
    private static final int IMAGEVIEW_HEIGHT = 75;
    private static final int IMAGEVIEW_WIDTH = 100;
    private static final String HOVER_EFFECT = "-fx-effect: dropshadow(gaussian, #00ffff, 10, 0.8, 0, 0);";

    private final HomeView view;
    private final GameService gameService;
    private final GenreService genreService;
    private final UserInteractor userInteractor;
    private final PersonalProfileController personalProfileController;
    private final RecommendationEngine recommendationEngine;
    private final GameCentralController gameCentralController;

    public HomeController(HomeView view, GameService gameService, UserInteractor userInteractor,
                          PersonalProfileController personalProfileController,
                          RecommendationEngine engine, GameCentralController gameCentralController) {
        this.view = view;
        this.gameService = gameService;
        this.genreService = new GenreService();
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

        view.setGenreSearchButtonHandler(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleGenreSearch();
            }
        });
    }

    /**
     * Sets game recommendations in the view.
     * Fetches recommendations and builds recommendation nodes.
     */
    public void setRecommendations() {
        List<Node> nodes = new ArrayList<>();
        Recommendation recommendation = recommendationEngine.generateRecommendation();
        if (recommendation == null) {
            view.setRecommendations(nodes, "no recommendation");
        }

        List<GameRecommendation> recs = recommendation.getRecommendations();
        if (recs == null || recs.isEmpty()) {
            view.setRecommendations(nodes, "no recommendations");
        }

        for (GameRecommendation rec : recs) {
            Node card = buildRecommendationNode(rec);
            if (card != null) {
                nodes.add(card);
            }
        }
        view.setRecommendations(nodes, recommendation.getMessage());
    }

    private Node buildRecommendationNode(GameRecommendation recommendation) {
        VBox card = new VBox(SPACING_SMALL);
        card.setAlignment(Pos.TOP_CENTER);
        card.setStyle("-fx-background-color: #333; -fx-padding: 8; -fx-background-radius: 10;");
        card.setPrefWidth(CARD_WIDTH);
        card.setMaxWidth(CARD_WIDTH);

        ImageView iv = new ImageView(recommendation.getCoverImage());
        iv.setFitWidth(IMAGE_WIDTH);
        iv.setFitHeight(IMAGE_HEIGHT);
        iv.setPreserveRatio(true);
        iv.setSmooth(true);
        iv.setCache(true);

        StackPane imgWrap = new StackPane(iv);
        imgWrap.setStyle("-fx-background-color: #222; -fx-background-radius: 8;");
        imgWrap.setPadding(new Insets(IMAGE_PADDING));

        String title = recommendation.getTitle();
        String year = String.valueOf(recommendation.getYear());
        Label titleLbl = new Label(title + " (" + year + ")");
        titleLbl.setStyle("-fx-text-fill: #fff; -fx-font-size: 12px; -fx-font-weight: bold;");
        titleLbl.setWrapText(true);
        titleLbl.setMaxWidth(TITLE_MAX_WIDTH);

        Label numeric = new Label(String.valueOf(recommendation.getRating()));
        numeric.setStyle("-fx-text-fill: #bbb; -fx-font-size: 11px;");

        card.getChildren().addAll(imgWrap, titleLbl, numeric);
        card.setCursor(Cursor.HAND);
        card.setOnMouseEntered(mouseEvent -> {
            card.setStyle("-fx-background-color: #333; -fx-padding: 8; -fx-background-radius: 10; "
                    + "-fx-border-color: #00ffff; -fx-border-width: 2; -fx-border-radius: 10; "
                    + HOVER_EFFECT);
        });
        card.setOnMouseExited(mouseEvent -> {
            card.setStyle("-fx-background-color: #333; -fx-padding: 8; -fx-background-radius: 10;");
        });
        card.setOnMouseClicked(new GamePreviewClickHandler(gameCentralController));
        card.setUserData(recommendation.getGameid());
        return card;
    }

    private void handleSearch() {
        String query = view.getSearchQuery();
        if (query == null || query.trim().isEmpty()) {
            view.displayGameResults(new ArrayList<>());
        }
        query = query.trim();

        List<Node> resultNodes = new ArrayList<>();

        User exactUser = userInteractor.getUserByUsername(query);
        if (exactUser != null) {
            resultNodes.add(createUserResultNode(exactUser));
        }

        List<Integer> gameIds = gameService.searchGame(query);
        for (Integer id : gameIds) {
            GamePreview preview = gameService.getGamePreviewById(id);
            if (preview != null) {
                resultNodes.add(createGamePreviewNode(preview));
            }
        }

        view.displayGameResults(resultNodes);
    }

    private void handleGenreSearch() {
        String query = view.getSearchQuery();
        if (query == null || query.trim().isEmpty()) {
            view.displayGameResults(new ArrayList<>());
        }
        query = query.trim();

        List<Node> resultNodes = new ArrayList<>();
        try {
            List<Game> games = genreService.getGamesByGenre(query);
            for (Game game : games) {
                if (game.getGameid() != 0) {
                    GamePreview preview = gameService.getGamePreviewById(game.getGameid());
                    if (preview != null) {
                        resultNodes.add(createGamePreviewNode(preview));
                    }
                }
            }
        }
        catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            view.displayGameResults(new ArrayList<>());
        }
        catch (Exception argument) {
            throw new RuntimeException(argument);
        }

        view.displayGameResults(resultNodes);
    }

    private Node createUserResultNode(User user) {
        HBox row = new HBox(USER_ROW_SPACING);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(PREVIEW_BOX_SPACING));
        row.setStyle("-fx-border-color: lightgray; -fx-background-color: #1e1e1e; "
                + "-fx-background-radius: 6; -fx-border-radius: 6;");

        ImageView avatar = new ImageView();
        avatar.setFitWidth(AVATAR_SIZE);
        avatar.setFitHeight(AVATAR_SIZE);
        avatar.setPreserveRatio(false);
        String avatarUrl = user.getProfilePictureURL();
        if (avatarUrl != null && !avatarUrl.isEmpty()) {
            try {
                avatar.setImage(new Image(avatarUrl, true));
            }
            catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            }
        }

        Label nameLabel = new Label(user.getUsername());
        nameLabel.setStyle("-fx-text-fill: #fff; -fx-font-size: 16px; -fx-font-weight: bold;");
        VBox info = new VBox(IMAGE_PADDING, nameLabel);
        info.setAlignment(Pos.CENTER_LEFT);

        row.getChildren().addAll(avatar, info);

        row.setOnMouseEntered(mouseEvent -> {
            row.setCursor(Cursor.HAND);
            row.setStyle("-fx-border-color: #00ffff; -fx-border-width: 2; -fx-background-color: #1e1e1e; "
                    + "-fx-background-radius: 6; -fx-border-radius: 6; " + HOVER_EFFECT);
        });
        row.setOnMouseExited(mouseEvent -> {
            row.setCursor(Cursor.DEFAULT);
            row.setStyle("-fx-border-color: lightgray; -fx-background-color: #1e1e1e; "
                    + "-fx-background-radius: 6; -fx-border-radius: 6;");
        });

        String loggedIn = userInteractor.getUser().getUsername();
        row.setOnMouseClicked(mouseEvent -> {
            if (!user.getUsername().equals(loggedIn)) {
                gameCentralController.showUserProfileView(user.getUsername());
            }
        });

        row.setUserData(user.getUsername());
        return row;
    }

    private Node createGamePreviewNode(GamePreview game) {
        ImageView imageView = new ImageView(game.getCoverImage());
        imageView.setFitHeight(IMAGEVIEW_HEIGHT);
        imageView.setFitWidth(IMAGEVIEW_WIDTH);
        imageView.setPreserveRatio(true);

        Label titleLabel = new Label(game.getTitle() + " (" + game.getYear() + ")");
        titleLabel.setFont(Font.font(TITLE_FONT_SIZE));
        titleLabel.setStyle("-fx-text-fill: white;");

        VBox infoBox = new VBox(BUTTON_SPACING, titleLabel);

        Button libraryButton = new Button();
        if (userInteractor.isInLibrary(game.getGameid())) {
            libraryButton.setText("Remove from Library");
        }
        else {
            libraryButton.setText("Add to Library");
        }
        libraryButton.setUserData(game);
        libraryButton.setOnAction(event -> handleLibraryButtonClick(event));

        Button wishlistButton = new Button();
        if (userInteractor.isInWishlist(game.getGameid())) {
            wishlistButton.setText("Remove from Wishlist");
        }
        else {
            wishlistButton.setText("Add to Wishlist");
        }
        wishlistButton.setUserData(game);
        wishlistButton.setOnAction(event -> handleWishlistButtonClick(event));

        VBox buttonBox = new VBox(BUTTON_SPACING, libraryButton, wishlistButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        HBox previewBox = new HBox(PREVIEW_BOX_SPACING, imageView, infoBox, buttonBox);
        previewBox.setPadding(new Insets(PREVIEW_BOX_SPACING));
        previewBox.setStyle("-fx-border-color: lightgray; -fx-background-color: #222;");
        previewBox.setAlignment(Pos.CENTER_LEFT);
        previewBox.setCursor(Cursor.HAND);

        previewBox.setOnMouseEntered(mouseEvent -> {
            previewBox.setCursor(Cursor.HAND);
            previewBox.setStyle("-fx-border-color: #00ffff; -fx-border-width: 2; "
                    + "-fx-background-color: #222; " + HOVER_EFFECT);
        });
        previewBox.setOnMouseExited(mouseEvent -> {
            previewBox.setCursor(Cursor.DEFAULT);
            previewBox.setStyle("-fx-border-color: lightgray; -fx-background-color: #222;");
        });
        previewBox.setOnMouseClicked(new GamePreviewClickHandler(gameCentralController));
        previewBox.setUserData(game.getGameid());

        return previewBox;
    }

    private void handleLibraryButtonClick(ActionEvent event) {
        Button button = (Button) event.getSource();
        GamePreview game = (GamePreview) button.getUserData();
        int gameid = game.getGameid();

        if (userInteractor.isInLibrary(gameid)) {
            userInteractor.removeFromLibrary(gameid);
            button.setText("Add to Library");
        }
        else {
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
        }
        else {
            userInteractor.addToWishlist(gameid);
            button.setText("Remove from Wishlist");
        }
    }
}
