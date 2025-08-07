package com.csc207.group.ui.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import model.GamePreview;
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

    public HomeController(HomeView view, GameService gameService, UserInteractor userInteractor,
                          UserProfileController userProfileController) {
        this.view = view;
        this.gameService = gameService;
        this.userInteractor = userInteractor;
        this.userProfileController = userProfileController;

        view.setSearchButtonHandler(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleSearch();
            }
        });
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
        previewBox.setStyle("-fx-border-color: lightgray; -fx-background-color: #f9f9f9;");
        previewBox.setAlignment(Pos.CENTER_LEFT);

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