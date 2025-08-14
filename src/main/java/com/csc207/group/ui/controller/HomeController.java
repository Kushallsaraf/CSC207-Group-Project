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

public final class HomeController {

    public static final int ONEHUNDREDFIFTY = 150;
    public static final int ONEHUNDREDTHIRTYFOUR = 134;
    public static final int SIX = 6;
    public static final int ONEHUNDREDTHIRTYFIVE = 135;
    public static final int FOUR = 4;
    public static final int ONEHUNDREDFOURTY = 140;
    public static final String DROP_SHADOW = "-fx-effect: dropshadow(gaussian, #00ffff, 10, 0.8, 0, 0);";
    public static final int TWELVE = 12;
    public static final int TEN = 10;
    public static final int FOURTYEIGHT = 48;
    public static final int SEVENTYFIVE = 75;
    public static final int ONEHUNDRED = 100;
    public static final int FIVE = 5;
    public static final int FIFTEEN = 15;
    public static final int SIXTEEN = 16;
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
     * Set's recomendations.
     */
    public void setRecommendations() {
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
            if (card != null) {
                nodes.add(card);
            }
        }
        view.setRecommendations(nodes, recommendation.getMessage());
    }

    private Node buildRecommendationNode(GameRecommendation recommendation) {
        VBox card = new VBox(SIX);
        card.setAlignment(Pos.TOP_CENTER);
        card.setStyle("-fx-background-color: #333; -fx-padding: 8; -fx-background-radius: 10;");
        card.setPrefWidth(ONEHUNDREDFIFTY);
        card.setMaxWidth(ONEHUNDREDFIFTY);

        ImageView iv = new ImageView(recommendation.getCoverImage());
        iv.setFitWidth(ONEHUNDREDTHIRTYFOUR);
        iv.setFitHeight(ONEHUNDREDTHIRTYFIVE);
        iv.setPreserveRatio(true);
        iv.setSmooth(true);
        iv.setCache(true);

        StackPane imgWrap = new StackPane(iv);
        imgWrap.setStyle("-fx-background-color: #222; -fx-background-radius: 8;");
        imgWrap.setPadding(new Insets(FOUR));

        String title = recommendation.getTitle();
        String year = String.valueOf(recommendation.getYear());
        Label titleLbl = new Label(title + " (" + year + ")");
        titleLbl.setStyle("-fx-text-fill: #fff; -fx-font-size: 12px; -fx-font-weight: bold;");
        titleLbl.setWrapText(true);
        titleLbl.setMaxWidth(ONEHUNDREDFOURTY);

        Label numeric = new Label(String.valueOf(recommendation.getRating()));
        numeric.setStyle("-fx-text-fill: #bbb; -fx-font-size: 11px;");

        card.getChildren().addAll(imgWrap, titleLbl, numeric);
        card.setCursor(Cursor.HAND);
        card.setOnMouseEntered(event -> {
            card.setStyle("-fx-background-color: #333; -fx-padding: 8; -fx-background-radius: 10; "
                    + "-fx-border-color: #00ffff; -fx-border-width: 2; -fx-border-radius: 10; "
                    + DROP_SHADOW);
            card.setCursor(Cursor.HAND);
        });
        card.setOnMouseExited(event -> {
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

    private void handleGenreSearch() {
        String query = view.getSearchQuery();
        if (query == null || query.trim().isEmpty()) {
            view.displayGameResults(new ArrayList<>());
            return;
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
        catch (Exception ex) {
            ex.printStackTrace();
            // Optionally, display an error to the user
            view.displayGameResults(new ArrayList<>());
        }

        view.displayGameResults(resultNodes);
    }

    private Node createUserResultNode(User u) {
        HBox row = new HBox(TWELVE);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(TEN));
        row.setStyle("-fx-border-color: lightgray; -fx-background-color: #1e1e1e; "
                + "-fx-background-radius: 6; -fx-border-radius: 6;");

        ImageView avatar = new ImageView();
        avatar.setFitWidth(FOURTYEIGHT);
        avatar.setFitHeight(FOURTYEIGHT);
        avatar.setPreserveRatio(false);
        String avatarUrl = u.getProfilePictureUrl();
        if (avatarUrl != null && !avatarUrl.isEmpty()) {
            try {
                avatar.setImage(new Image(avatarUrl, true));
            }
            catch (Exception ignored) {}
        }

        Label name = new Label(u.getUsername());
        name.setStyle("-fx-text-fill: #fff; -fx-font-size: 16px; -fx-font-weight: bold;");
        VBox info = new VBox(FOUR, name);
        info.setAlignment(Pos.CENTER_LEFT);

        row.getChildren().addAll(avatar, info);

        // Hover + click → open profile (avoid navigating to your own profile view)
        row.setOnMouseEntered(event -> {
            row.setCursor(Cursor.HAND);
            row.setStyle("-fx-border-color: #00ffff; -fx-border-width: 2; -fx-background-color: #1e1e1e; "
                    + "-fx-background-radius: 6; -fx-border-radius: 6; "
                    + DROP_SHADOW);
        });
        row.setOnMouseExited(event -> {
            row.setCursor(Cursor.DEFAULT);
            row.setStyle("-fx-border-color: lightgray; -fx-background-color: #1e1e1e; "
                    + "-fx-background-radius: 6; -fx-border-radius: 6;");
        });

        String loggedIn = userInteractor.getUser().getUsername();
        row.setOnMouseClicked(e -> {
            if (!u.getUsername().equals(loggedIn)) {
                gameCentralController.showUserProfileView(u.getUsername());
            }
            else {
                // If you prefer to jump to your own profile:
                // gameCentralController.showPersonalProfileView();
            }
        });

        row.setUserData(u.getUsername());
        // optional
        return row;
    }
    // ----------------- UPDATED: search now returns games + users -----------------

    // -----------------------------------------------------------------------------

    private Node createGamePreviewNode(GamePreview game) {
        ImageView imageView = new ImageView(game.getCoverImage());
        imageView.setFitHeight(ONEHUNDRED);
        imageView.setFitWidth(SEVENTYFIVE);
        imageView.setPreserveRatio(true);

        Label titleLabel = new Label(game.getTitle() + " (" + game.getYear() + ")");
        titleLabel.setFont(Font.font(SIXTEEN));
        titleLabel.setStyle("-fx-text-fill: white;");

        VBox infoBox = new VBox(FIVE, titleLabel);

        Button libraryButton;
        if (userInteractor.isInLibrary(game.getGameid())) {
            libraryButton = new Button("Remove from Library");
        }
        else {
            libraryButton = new Button("Add to Library");
        }
        libraryButton.setUserData(game);
        libraryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                handleLibraryButtonClick(event);
            }
        });

        Button wishlistButton;
        if (userInteractor.isInWishlist(game.getGameid())) {
            wishlistButton = new Button("Remove from Wishlist");
        }
        else {
            wishlistButton = new Button("Add to Wishlist");
        }
        wishlistButton.setUserData(game);
        wishlistButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                handleWishlistButtonClick(actionEvent);
            }
        });

        VBox buttonBox = new VBox(FIVE, libraryButton, wishlistButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        HBox previewBox = new HBox(FIFTEEN, imageView, infoBox, buttonBox);
        previewBox.setPadding(new Insets(TEN));
        previewBox.setStyle("-fx-border-color: lightgray; -fx-background-color: #222;");
        previewBox.setAlignment(Pos.CENTER_LEFT);
        previewBox.setCursor(Cursor.HAND);

        previewBox.setOnMouseEntered(event -> {
            previewBox.setCursor(Cursor.HAND);
            previewBox.setStyle("-fx-border-color: #00ffff; -fx-border-width: 2; -fx-background-color: #222; "
                    + DROP_SHADOW);
        });
        previewBox.setOnMouseExited(event -> {
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
