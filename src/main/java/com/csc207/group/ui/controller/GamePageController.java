package com.csc207.group.ui.controller;

import com.csc207.group.model.Game;
import com.csc207.group.model.Review;
import com.csc207.group.service.GamePageInteractor;
import com.csc207.group.service.GameService;
import com.csc207.group.ui.GamePageView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class GamePageController {

    private final GameService gameService;
    private final GamePageView gamePageView;
    private final GamePageInteractor gamePageInteractor;
    private Game game;
    private int gameid;

    public GamePageController(GameService gameService, GamePageView gamePageView,
                              GamePageInteractor gamePageInteractor, Integer gameid) {
        this.gameService = gameService;
        this.gamePageView = gamePageView;
        this.gamePageInteractor = gamePageInteractor;
        this.game = gameService.getGameById(gameid);
        this.gameid = gameid;
    }

    public void setGamePageView(){
        gamePageView.setTitle(game.getName());
        gamePageView.getSubmitReviewButton().setOnAction(new ReviewSubmissionHandler(this));
        updateView(); // populate existing reviews immediately
    }

    /** Lets user interactor know to store review and under games, then refreshes UI. */
    public void handleReviewSubmission() {
        String rating = this.gamePageView.getRatingField().getText();
        String content = this.gamePageView.getReviewArea().getText();
        double ratingValue;

        try {
            ratingValue = Double.parseDouble(rating);
        } catch (NumberFormatException e) {
            ratingValue = 0.0;
        }
        if (ratingValue < 0) ratingValue = 0;
        if (ratingValue > 5) ratingValue = 5;

        this.gamePageInteractor.saveReview(ratingValue, content, gameid, game);
        this.gamePageView.clearReviewFields();
        updateView();
    }

    public void updateView(){
        this.gamePageView.setReviewNodes(buildReviewNodes());
    }

    private List<Node> buildReviewNodes(){
        List<Review> reviews = game.getReviews();
        List<Node> nodes = new ArrayList<Node>();

        if (reviews == null || reviews.isEmpty()) {
            nodes.add(new Label("No reviews yet."));
            return nodes;
        }

        for (int i = 0; i < reviews.size(); i++) {
            Review r = reviews.get(i);
            if (r == null) continue;

            String userId = r.getUserid();
            String avatarUrl = gamePageInteractor.getProfileURL(userId); // assumed to exist

            // Square avatar on the far left
            ImageView avatar = buildSquareAvatar(avatarUrl, 48); // 48x48

            Label userLabel = new Label(userId);
            userLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");

            double clamped = r.getRating();
            if (clamped < 0) clamped = 0;
            if (clamped > 5) clamped = 5;

            Label ratingLabel = new Label("Rating: " + String.format("%.1f", clamped) + " / 5");
            ratingLabel.setStyle("-fx-text-fill: #555;");

            Label contentLabel = new Label(r.getContent());
            contentLabel.setWrapText(true);

            VBox textBox = new VBox(2);
            textBox.getChildren().addAll(userLabel, ratingLabel, contentLabel);

            HBox row = new HBox(10);
            row.setAlignment(Pos.TOP_LEFT); // avatar anchored left, text to the right
            row.setPadding(new Insets(8, 10, 8, 10));
            row.getChildren().addAll(avatar, textBox);
            row.setStyle(
                    "-fx-background-color: #f6f6f6; " +
                            "-fx-background-radius: 8; " +
                            "-fx-border-color: #e2e2e2; " +
                            "-fx-border-radius: 8;"
            );

            nodes.add(row);
        }

        return nodes;
    }

    /** Builds a square avatar placed on the left.
     *  preserveRatio=false forces a square; switch to a viewport crop if you want no stretching.
     */
    private ImageView buildSquareAvatar(String url, double size) {
        ImageView iv = new ImageView();
        if (url != null && url.length() > 0) {
            Image img = new Image(url, true); // background loading
            iv.setImage(img);
        }
        iv.setFitWidth(size);
        iv.setFitHeight(size);
        iv.setPreserveRatio(false); // fill the square
        iv.setSmooth(true);
        return iv;
    }
}

