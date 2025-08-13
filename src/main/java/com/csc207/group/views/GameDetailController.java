package com.csc207.group.views;

import com.csc207.group.app.GameCentralController;
import com.csc207.group.cache.RawgFirebaseApiCache;
import com.csc207.group.data_access.RawgApiClient;
import com.csc207.group.model.Achievement;
import com.csc207.group.model.DLC;
import com.csc207.group.model.Game;
import com.csc207.group.model.Review;
import com.csc207.group.model.Screenshot;
import com.csc207.group.model.User;
import com.csc207.group.service.DLCService;
import com.csc207.group.service.GamePageInteractor;
import com.csc207.group.service.GenreService;
import com.csc207.group.views.Components.AchievementCard;
import com.csc207.group.views.Components.DLCcard;
import javafx.geometry.Insets;
import javafx.scene.Node;
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

    public GameDetailController(Game game, GamePageInteractor gamePageInteractor,
                                GameCentralController gameCentralController, User user) {
        this.view = new GameDetailViewFunc();
        this.game = game;
        this.gamePageInteractor = gamePageInteractor;
        this.gameCentralController = gameCentralController;
        this.user = user;
        this.rawgApiClient = new RawgApiClient(new RawgFirebaseApiCache());
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
                    if (s.getImageURL() != null && !s.getImageURL().isEmpty()) {
                        images.add(new Image(s.getImageURL(), true)); // Load in background
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
        view.setUserScore(game.getCritic_rating());
    }

    public void setImage() {
        String coverUrl = game.getCover_image();
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

        view.setReleaseDate(game.getRelease_date());
        view.setAgeRating(game.getAge_rating());

        String plats = String.join(", ", game.getPlatforms());
        view.setPlatforms(plats);
    }

    public void setDLCs() {
        List<DLC> dlcs = new ArrayList<>();
        for (Integer id : game.getDLCs()) {
            dlcs.add(new DLCService().getDLCById(id));
        }
        List<VBox> dlccards = new ArrayList<>();
        for (DLC dlc : dlcs) {
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
            }
            catch (URISyntaxException | InterruptedException e) {
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

            String userId = r.getUserid();
            String avatarUrl = gamePageInteractor.getProfileURL(userId);

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