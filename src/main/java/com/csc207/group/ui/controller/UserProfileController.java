package com.csc207.group.ui.controller;

import com.csc207.group.app.GameCentralController;
import com.csc207.group.model.GamePreview;
import com.csc207.group.model.LibraryEntry;
import com.csc207.group.model.User;
import com.csc207.group.service.GameService;
import com.csc207.group.service.UserInteractor;
import com.csc207.group.service.UserProfileInteractor;
import com.csc207.group.ui.UserProfileView;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserProfileController {

    private final UserInteractor userInteractor;             // logged-in + lookups
    private final UserProfileInteractor profileInteractor;   // exposes GameService
    private final UserProfileView view;                      // UI for other users' profiles
    private final String targetUsername;                     // whose profile
    private final GameCentralController gameCentralController;

    private List<Node> libraryCards;
    private List<Node> wishlistCards;

    public UserProfileController(UserInteractor userInteractor,
                                 UserProfileInteractor profileInteractor,
                                 UserProfileView view,
                                 String targetUsername,
                                 GameCentralController gameCentralController) {
        this.userInteractor = userInteractor;
        this.profileInteractor = profileInteractor;
        this.view = view;
        this.targetUsername = targetUsername;
        this.gameCentralController = gameCentralController;
        initialize();
    }

    private void initialize() {
        User targetUser = userInteractor.getUserByUsername(targetUsername);
        if (targetUser == null) {
            // Minimal fallback if user not found
            view.setUsername(targetUsername);
            view.setBio("User not found.");
            view.setProfileImage(null);
            view.setFollowersCount(0, targetUsername);
            view.setFollowingCount(0, targetUsername);
            return;
        }

        User loggedInUser = userInteractor.getUser();
        GameService gameService = profileInteractor.getGameService();

        // Header
        view.setUsername(targetUser.getUsername());
        view.setBio(targetUser.getBio());
        view.setProfileImage(targetUser.getProfilePictureURL());
        view.setFollowersCount(targetUser.getFollowers().size(), targetUsername);
        view.setFollowingCount(targetUser.getFollowing().size(), targetUsername);

        // Followers label → popup
        view.getFollowersLabel().setCursor(Cursor.HAND);
        view.getFollowersLabel().setOnMouseEntered(e ->
                view.getFollowersLabel().setStyle("-fx-underline: true; -fx-text-fill: #00aaff;"));
        view.getFollowersLabel().setOnMouseExited(e -> view.getFollowersLabel().setStyle("-fx-text-fill: #B3B3B3;"));
        view.getFollowersLabel().setOnMouseClicked(e ->
                view.showUsersPopup("Followers of " + targetUser.getUsername(),
                        makeNodesFromFollowers(targetUser))
        );

        // Following label → popup
        view.getFollowingLabel().setCursor(Cursor.HAND);
        view.getFollowingLabel().setOnMouseEntered(e ->
                view.getFollowingLabel().setStyle("-fx-underline: true; -fx-text-fill: #00aaff;"));
        view.getFollowingLabel().setOnMouseExited(e -> view.getFollowingLabel().setStyle("-fx-text-fill: #B3B3B3;"));
        view.getFollowingLabel().setOnMouseClicked(e ->
                view.showUsersPopup("Following by " + targetUser.getUsername(),
                        makeNodesFromFollowing(targetUser))
        );

        // Follow button
        boolean alreadyFollowing = loggedInUser != null && loggedInUser.isFollowing(targetUsername);
        view.getFollowButton().setText(alreadyFollowing ? "Unfollow" : "Follow");
        view.getFollowButton().setOnAction(
                new FollowButtonHandler(userInteractor, loggedInUser, targetUsername, view)
        );

        // Cards (target user; no Remove buttons)
        buildLibraryCards(targetUser, gameService);
        buildWishlistCards(targetUser, gameService);
        view.setLibraryCards(libraryCards);
        view.setWishlistCards(wishlistCards);
    }

    // ---------- Build popup node lists ----------
    private List<Node> makeNodesFromFollowers(User targetUser) {
        if (targetUser.getFollowers() == null || targetUser.getFollowers().isEmpty()) {
            return Collections.singletonList(emptyRow("No followers yet."));
        }
        List<String> followers = new ArrayList<>(targetUser.getFollowers());
        followers.sort(String::compareToIgnoreCase); // optional alphabetize
        List<Node> nodes = new ArrayList<>();
        for (String uname : followers) {
            User u = userInteractor.getUserByUsername(uname);
            nodes.add(userRow(u != null ? u.getUsername() : uname,
                    u != null ? u.getProfilePictureURL() : null));
        }
        return nodes;
    }

    private List<Node> makeNodesFromFollowing(User targetUser) {
        if (targetUser.getFollowing() == null || targetUser.getFollowing().isEmpty()) {
            return Collections.singletonList(emptyRow("Not following anyone yet."));
        }
        List<String> following = new ArrayList<>(targetUser.getFollowing());
        following.sort(String::compareToIgnoreCase); // optional alphabetize
        List<Node> nodes = new ArrayList<>();
        for (String uname : following) {
            User u = userInteractor.getUserByUsername(uname);
            nodes.add(userRow(u != null ? u.getUsername() : uname,
                    u != null ? u.getProfilePictureURL() : null));
        }
        return nodes;
    }

    private HBox emptyRow(String msg) {
        HBox row = new HBox(8);
        row.setPadding(new Insets(6));
        Label label = new Label(msg);
        label.setStyle("-fx-text-fill: white;");
        row.getChildren().add(label);
        return row;
    }

    private HBox userRow(String username, String avatarUrl) {
        HBox row = new HBox(8);
        row.setPadding(new Insets(6));
        row.setStyle("-fx-background-color: #2C2C2C; -fx-background-radius: 5; -fx-border-color: #333333; -fx-border-radius: 5;");

        ImageView avatar = new ImageView();
        avatar.setFitWidth(32);
        avatar.setFitHeight(32);
        avatar.setPreserveRatio(false);
        if (avatarUrl != null && !avatarUrl.isEmpty()) {
            try { avatar.setImage(new Image(avatarUrl, true)); } catch (Exception ignored) {}
        }

        Label name = new Label(username);
        name.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");

        row.getChildren().addAll(avatar, name);

        String me = userInteractor.getUser() != null ? userInteractor.getUser().getUsername() : null;
        if (username != null && !username.equals(me)) {
            row.setOnMouseEntered(ev -> row.setCursor(Cursor.HAND));
            row.setOnMouseClicked(ev -> {
                if (gameCentralController != null && !username.isEmpty()) {
                    gameCentralController.showUserProfileView(username);
                }
            });
        }
        return row;
    }
    // -------------------------------------------

    private void buildLibraryCards(User targetUser, GameService gameService) {
        libraryCards = new ArrayList<>();
        for (Integer gameId : targetUser.getLibrary()) {
            LibraryEntry entry = gameService.getLibraryEntryById(gameId);
            if (entry == null) continue;

            HBox card = baseCard(gameId);

            ImageView cover = new ImageView();
            cover.setFitWidth(50);
            cover.setFitHeight(50);
            String url = entry.getCoverImage() != null ? entry.getCoverImage().getUrl() : null;
            if (url != null && !url.isEmpty()) {
                try { cover.setImage(new Image(url, true)); } catch (Exception ignored) {}
            }

            Label name = new Label(entry.getTitle() + " (" + entry.getYear() + ")");
            name.setStyle("-fx-text-fill: white;");
            card.getChildren().addAll(cover, name);
            card.setOnMouseClicked(new GamePreviewClickHandler(gameCentralController));
            libraryCards.add(card);
        }
    }

    private void buildWishlistCards(User targetUser, GameService gameService) {
        wishlistCards = new ArrayList<>();
        for (Integer gameId : targetUser.getWishlist()) {
            GamePreview preview = gameService.getGamePreviewById(gameId);
            if (preview == null) continue;

            HBox card = baseCard(gameId);

            ImageView cover = new ImageView();
            cover.setFitWidth(50);
            cover.setFitHeight(50);
            String url = preview.getCoverImage() != null ? preview.getCoverImage().getUrl() : null;
            if (url != null && !url.isEmpty()) {
                try { cover.setImage(new Image(url, true)); } catch (Exception ignored) {}
            }

            Label name = new Label(preview.getTitle() + " (" + preview.getYear() + ")");
            name.setStyle("-fx-text-fill: white;");
            card.getChildren().addAll(cover, name);
            card.setOnMouseClicked(new GamePreviewClickHandler(gameCentralController));
            wishlistCards.add(card);
        }
    }

    private HBox baseCard(Integer gameId) {
        HBox card = new HBox(5);
        card.setPadding(new Insets(10));
        card.setStyle("-fx-border-color:#333333; -fx-background-color:#2C2C2C; -fx-background-radius:5; -fx-border-radius:5;");
        card.setUserData(gameId);

        card.setOnMouseEntered(new javafx.event.EventHandler<javafx.scene.input.MouseEvent>() {
            @Override public void handle(javafx.scene.input.MouseEvent event) {
                card.setCursor(Cursor.HAND);
                card.setStyle("-fx-border-color:#00ffff; -fx-background-color:#3C3C3C; -fx-background-radius:5; -fx-border-radius:5; -fx-effect:dropshadow(gaussian, rgba(0,0,0,0.15), 6, 0.2, 0, 0);");
            }
        });
        card.setOnMouseExited(new javafx.event.EventHandler<javafx.scene.input.MouseEvent>() {
            @Override public void handle(javafx.scene.input.MouseEvent event) {
                card.setCursor(Cursor.DEFAULT);
                card.setStyle("-fx-border-color:#333333; -fx-background-color:#2C2C2C; -fx-background-radius:5; -fx-border-radius:5;");
            }
        });

        return card;
    }
}