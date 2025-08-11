package com.csc207.group.views;

import com.csc207.group.app.GameCentralController;
import com.csc207.group.model.DLC;
import com.csc207.group.model.Game;
import com.csc207.group.model.Review;
import com.csc207.group.service.DLCService;
import com.csc207.group.service.GamePageInteractor;
import com.csc207.group.service.GameService;
import com.csc207.group.service.GenreService;
import com.csc207.group.views.Components.DLCcard;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class GameDetailController {
    private GameCentralController gameCentralController;
    private Game game;
    private GameDetailViewFunc view;
    private GameService gameService;
    private GamePageInteractor gamePageInteractor;
    private DLCService dlcService;

    public GameDetailController(Game game, GamePageInteractor gamePageInteractor) {
        this.view = new GameDetailViewFunc();
        this.game = game;
        this.gamePageInteractor = gamePageInteractor;
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
        setupReviewHandlers();
        updateReviewDisplay();
        return view;
    }

    public void setTitle() {
        view.setTitle(game.getName());
    }

    public void setGenre() {
//        view.setTags(game.getGenres());
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

    private void setupReviewHandlers() {
        view.getSubmitReviewButton().setOnAction(e -> handleReviewSubmission());
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

            Label ratingLabel = new Label("Rating: " + String.format("%.1f", r.getRating()) + " / 5");
            ratingLabel.setStyle("-fx-text-fill: #ccc;");

            Label contentLabel = new Label(r.getContent());
            contentLabel.setWrapText(true);
            contentLabel.setStyle("-fx-text-fill: white;");

            VBox textBox = new VBox(userLabel, ratingLabel, contentLabel);
            HBox row = new HBox(10, avatar, textBox);
            row.setPadding(new Insets(8));
            row.setStyle("-fx-background-color: #2a2a2a; -fx-background-radius: 5;");

            nodes.add(row);
        }

        return nodes;
    }
}