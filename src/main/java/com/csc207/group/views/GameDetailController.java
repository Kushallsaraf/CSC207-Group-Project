package com.csc207.group.views;

import com.csc207.group.app.GameCentralController;
import com.csc207.group.cache.RawgFirebaseApiCache;
import com.csc207.group.data_access.RawgApiClient;
import com.csc207.group.model.*;
import com.csc207.group.service.DlcService;
import com.csc207.group.service.GamePageInteractor;
import com.csc207.group.service.GenreService;
import com.csc207.group.ui.controller.HomeController;
import com.csc207.group.views.Components.AchievementCard;
import com.csc207.group.views.Components.DLCcard;
import javafx.application.HostServices;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class GameDetailController {
    private GameCentralController gameCentralController;
    private Game game;
    private GameDetailViewFunc view;
    private GamePageInteractor gamePageInteractor;
    private User user;
    private RawgApiClient rawgApiClient;
    private HostServices hostServices;
    private HomeController homeController;

    public GameDetailController(Game game, GamePageInteractor gamePageInteractor,
                                GameCentralController gameCentralController, User user, HostServices hostServices, HomeController homeController) {
        this.view = new GameDetailViewFunc();
        this.game = game;
        this.gamePageInteractor = gamePageInteractor;
        this.gameCentralController = gameCentralController;
        this.user = user;
        this.rawgApiClient = new RawgApiClient(new RawgFirebaseApiCache());
        this.hostServices = hostServices;
        this.homeController = homeController;
    }

    // This method initializes the view with data from the game and returns the view instance
    public GameDetailViewFunc setView() {
        setTitle();
        setGenre();
        setUserScore();
        setImage();
        setSynopsis();
        setOverview();
        setDLCs();
        setAchievements();
        setupActionHandlers();
        updateReviewDisplay();
        return view;
    }

    private void setupActionHandlers() {
        view.getViewPhotosButton().setOnAction(e -> fetchAndDisplayScreenshots());
        view.getSubmitReviewButton().setOnAction(e -> handleReviewSubmission());
        view.getBuyNowButton().setOnAction(e -> handleBuyNow());
        view.getAddButton().setOnAction(e -> handleAddTOLibrary());
        view.getMoreButton().setOnAction(e -> handleMoreLikeThis());
    }

    private void handleMoreLikeThis() {
        if (game.getGenres() != null && !game.getGenres().isEmpty()) {
            String firstGenre = new GenreService().getGenresById(game.getGenres().get(0));
            gameCentralController.showHomeViewWithSearch(firstGenre.toLowerCase());
        }
    }


    private void handleAddTOLibrary() {
        gamePageInteractor.userInteractor.addToLibrary(game.getGameid());
        view.showConfirmation("Added to your library!");
    }


    private void handleBuyNow() {
        new Thread(() -> {
            try {
                Integer rawgId = rawgApiClient.findGameIdByName(game.getName());
                if (rawgId == null) {
                    javafx.application.Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Not Available");
                        alert.setHeaderText(null);
                        alert.setContentText("Could not find a store page for this game.");
                        alert.showAndWait();
                    });
                    return;
                }

                List<GameStore> stores = rawgApiClient.getStoresForGame(String.valueOf(rawgId));
                if (stores != null && !stores.isEmpty()) {
                    String storeUrl = stores.get(0).getStoreUrl();
                    javafx.application.Platform.runLater(() -> {
                        hostServices.showDocument(storeUrl);
                    });
                } else {
                    javafx.application.Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Not Available");
                        alert.setHeaderText(null);
                        alert.setContentText("No store pages are available for this game.");
                        alert.showAndWait();
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
                javafx.application.Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("An error occurred while trying to find a store page.");
                    alert.showAndWait();
                });
            }
        }).start();
    }


    private void fetchAndDisplayScreenshots() {
        new Thread(() -> {
            try {
                // First get RAWG game ID from the game name
                Integer rawgId = rawgApiClient.findGameIdByName(game.getName());
                if (rawgId == null) {
                    System.out.println("Could not find RAWG ID for " + game.getName());
                    javafx.application.Platform.runLater(() -> {
                        view.displayScreenshots(new ArrayList<>());
                    });
                    return;
                }

                // Use ID to get screenshots
                List<Screenshot> screenshots = rawgApiClient.getScreenshotsForGame(String.valueOf(rawgId));
                List<Image> images = new ArrayList<>();
                for (Screenshot s : screenshots) {
                    if (s.getImageUrl() != null && !s.getImageUrl().isEmpty()) {
                        images.add(new Image(s.getImageUrl(), true)); // Load in background
                    }
                }

                // Update UI on the JavaFX Application Thread
                javafx.application.Platform.runLater(() -> {
                    view.displayScreenshots(images);
                });

            } catch (Exception e) {
                e.printStackTrace();
                // Handle error on UI thread if needed
                javafx.application.Platform.runLater(() -> {
                    view.displayScreenshots(new ArrayList<>());
                });
            }
        }).start();
    }


    public void setTitle() {
        view.setTitle(game.getName());
    }

    public void setGenre() {
        List<String> genres = game.getGenres();
        List<String> genreNames = new ArrayList<>();
        for (String genre : genres) {
            genreNames.add(new GenreService().getGenresById(genre));
        }
        view.setTags(genreNames);
    }

    public void setUserScore() {
        view.setUserScore(game.getCriticRating());
    }

    public void setImage() {
        String coverUrl = game.getCoverImage();
        System.out.println("Cover image URL: " + coverUrl);

        if (coverUrl != null && !coverUrl.isEmpty()) {
            view.setImages(new Image(coverUrl), null);
        }
    }

    public void setSynopsis() {
        view.setSynopsis(game.getDescription());
    }

    public void setOverview() {
        String devs = String.join(", ", game.getDeveloper());
        view.setDeveloper(devs);

        view.setReleaseDate(game.getReleaseDate());
        view.setAgeRating(game.getAgeRating());

        String plats = String.join(", ", game.getPlatforms());
        view.setPlatforms(plats);
    }

    public void setDLCs() {
        List<Dlc> dlcs = new ArrayList<>();
        for (Integer id : game.getDownloadableContent()) {
            dlcs.add(new DlcService().getDlcById(id));
        }
        List<VBox> dlccards = new ArrayList<>();
        for (Dlc dlc : dlcs) {
            dlccards.add(new DLCcard(dlc).getCard());
        }
        view.setDLCs(dlccards);

    }

    private void setAchievements() {
        new Thread(() -> { // Run on a background thread to avoid freezing the UI
            try {
                Integer rawgId = rawgApiClient.findGameIdByName(game.getName());
                if (rawgId != null) {
                    List<Achievement> achievements = rawgApiClient.getGameAchievements(String.valueOf(rawgId));
                    List<Node> achievementNodes = new ArrayList<>();
                    for (Achievement achievement : achievements) {
                        achievementNodes.add(new AchievementCard(achievement).getCard());
                    }

                    // Update UI on the JavaFX Application Thread
                    javafx.application.Platform.runLater(() -> {
                        view.setAchievements(achievementNodes);
                    });
                } else {
                    javafx.application.Platform.runLater(() -> {
                        view.setAchievements(new ArrayList<>());
                    });
                }
            } catch (URISyntaxException | InterruptedException e) {
                e.printStackTrace();
                javafx.application.Platform.runLater(() -> {
                    view.setAchievements(new ArrayList<>()); // Show empty on error
                });
            }
        }).start();
    }

    private void handleReviewSubmission() {
        String ratingStr = view.getRatingField().getText();
        String content = view.getReviewArea().getText();
        double rating = 0;

        try {
            rating = Double.parseDouble(ratingStr);
        } catch (NumberFormatException ignored) {}

        rating = Math.max(0, Math.min(5, rating)); // Clamp between 0-5
        gamePageInteractor.saveReview(rating, content, game.getGameid(), game);
        view.clearReviewFields();
        updateReviewDisplay();
    }

    private void updateReviewDisplay() {
        List<Node> reviewNodes = buildReviewNodes(game.getReviews());
        view.setReviewNodes(reviewNodes);
    }

    private List<Node> buildReviewNodes(List<Review> reviews) {
        List<Node> nodes = new ArrayList<>();
        if (reviews == null || reviews.isEmpty()) {
            nodes.add(new Label("No reviews yet."));
            return nodes;
        }

        for (Review r : reviews) {
            if (r == null) continue;

            String userId = r.getUserId();
            String avatarUrl = gamePageInteractor.getProfileUrl(userId);

            ImageView avatar = new ImageView();
            if (avatarUrl != null && !avatarUrl.isEmpty()) {
                avatar.setImage(new Image(avatarUrl, true));
            }
            avatar.setFitWidth(40);
            avatar.setFitHeight(40);
            avatar.setPreserveRatio(false);

            Label userLabel = new Label(userId);
            userLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");
            userLabel.setCursor(javafx.scene.Cursor.HAND);
            userLabel.setOnMouseEntered(e -> userLabel.setStyle(
                    "-fx-font-weight: bold; -fx-text-fill: #00aaff; -fx-underline: true;"
            ));
            userLabel.setOnMouseExited(e -> userLabel.setStyle(
                    "-fx-font-weight: bold; -fx-text-fill: white;"
            ));

            if (!userId.equals(user.getUsername())){

                userLabel.setOnMouseClicked(e -> {
                    if (gameCentralController != null) {
                        gameCentralController.showUserProfileView(userId);
                    }
                });}

            Label ratingLabel = new Label("Rating: " + String.format("%.1f", r.getRating()) + " / 5");
            ratingLabel.setStyle("-fx-text-fill: #ccc;");

            Label contentLabel = new Label(r.getContent());
            contentLabel.setWrapText(true);
            contentLabel.setStyle("-fx-text-fill: white;");

            VBox textBox = new VBox(userLabel, ratingLabel, contentLabel);

            if (userId.equals(user.getUsername())) {
                javafx.scene.control.Button editButton = new javafx.scene.control.Button("Edit");

                textBox.getChildren().add(editButton);
            }
            HBox row = new HBox(10, avatar, textBox);
            row.setPadding(new Insets(8));
            row.setStyle("-fx-background-color: #2a2a2a; -fx-background-radius: 5;");

            nodes.add(row);
        }

        return nodes;
    }
}