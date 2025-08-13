package com.csc207.group.ui.controller;

import com.csc207.group.app.GameCentralController;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import com.csc207.group.model.GamePreview;
import com.csc207.group.model.LibraryEntry;
import com.csc207.group.model.User;
import com.csc207.group.service.PersonalProfileInteractor;
import com.csc207.group.service.UserInteractor;
import com.csc207.group.ui.PersonalProfileView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PersonalProfileController {

    private final PersonalProfileInteractor profileInteractor;
    private final UserInteractor userInteractor;
    private final PersonalProfileView view;
    private final GameCentralController gameCentralController;

    private List<Node> previewCards;
    private List<Node> libraryCards;

    public PersonalProfileController(PersonalProfileInteractor profileInteractor,
                                     UserInteractor userInteractor,
                                     PersonalProfileView view,
                                     GameCentralController gameCentralController) {
        this.profileInteractor = profileInteractor;
        this.userInteractor = userInteractor;
        this.view = view;
        this.gameCentralController = gameCentralController;

        // Initial counts + store username in label userdata (overload with username)
        User me = userInteractor.getUser();
        view.setFollowersCount(me.getNumberOfFollowers(), me.getUsername());
        view.setFollowingCount(me.getNumberOfFollowing(), me.getUsername());
    }

    public void refresh() {
        view.setController(this);
        profileInteractor.reload();

        // Header
        view.setUsername(profileInteractor.getUsername());
        view.setProfileImage(profileInteractor.getProfilePictureUrl());
        view.setBio(profileInteractor.getBio());

        // Keep counts fresh and store username on labels
        User me = userInteractor.getUser();
        view.setFollowersCount(me.getNumberOfFollowers(), me.getUsername());
        view.setFollowingCount(me.getNumberOfFollowing(), me.getUsername());

        // Clickable Followers / Following labels → open popup with user nodes
        view.getFollowersLabel().setCursor(Cursor.HAND);
        view.getFollowersLabel().setOnMouseClicked(e ->
                view.showUsersPopup("Your Followers", makeNodesFromFollowers(me))
        );

        view.getFollowingLabel().setCursor(Cursor.HAND);
        view.getFollowingLabel().setOnMouseClicked(e ->
                view.showUsersPopup("You Follow", makeNodesFromFollowing(me))
        );

        // Edit profile
        view.getEditProfileButton().setOnAction(e -> {
            String currentBio = profileInteractor.getBio();
            String currentImageUrl = profileInteractor.getProfilePictureUrl();
            view.showEditProfilePopup(currentBio, currentImageUrl);
        });

        // Cards
        loadLibraryCards();
        loadPreviewCards();
        view.setLibraryCards(libraryCards);
        view.setWishlistCards(previewCards);
    }

    // ------- NEW: build follower/following node lists for popup -------
    private List<Node> makeNodesFromFollowers(User me) {
        List<Node> nodes = new ArrayList<>();
        for (String followerUsername : me.getFollowers()) {
            nodes.add(userRow(followerUsername));
        }
        return nodes;
    }

    private List<Node> makeNodesFromFollowing(User me) {
        List<Node> nodes = new ArrayList<>();
        for (String followingUsername : me.getFollowing()) {
            nodes.add(userRow(followingUsername));
        }
        return nodes;
    }

    // Small helper: avatar + username row; click → open that user's profile
    private HBox userRow(String username) {
        HBox row = new HBox(8);
        row.setPadding(new Insets(6));
        row.setStyle("-fx-background-color:#2C2C2C; -fx-background-radius:5; -fx-border-color:#333333; -fx-border-radius:5;");

        ImageView avatar = new ImageView();
        avatar.setFitWidth(32);
        avatar.setFitHeight(32);
        avatar.setPreserveRatio(false);

        // Try to fetch profile image URL (optional; safe if null)
        String avatarUrl = userInteractor.getUserByUsername(username) != null
                ? userInteractor.getUserByUsername(username).getProfilePictureURL()
                : null;
        if (avatarUrl != null && !avatarUrl.isEmpty()) {
            try { avatar.setImage(new Image(avatarUrl, true)); } catch (Exception ignored) {}
        }

        Label name = new Label(username);
        name.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");

        row.getChildren().addAll(avatar, name);

        row.setOnMouseEntered(ev -> row.setCursor(Cursor.HAND));
        row.setOnMouseClicked(ev -> {
            if (gameCentralController != null && username != null && !username.isEmpty()) {
                gameCentralController.showUserProfileView(username);
            }
        });

        return row;
    }
    // -------------------------------------------------------------------

    private void loadLibraryCards() {
        Map<Integer, LibraryEntry> entries = profileInteractor.getEntries();
        libraryCards = new ArrayList<>();

        for (Map.Entry<Integer, LibraryEntry> entry : entries.entrySet()) {
            Integer gameId = entry.getKey();
            LibraryEntry libraryEntry = entry.getValue();

            HBox card = new HBox(5);
            card.setPadding(new Insets(10));
            card.setStyle("-fx-border-color:#333333; -fx-background-color:#2C2C2C; -fx-background-radius:5; -fx-border-radius:5;");
            card.setUserData(gameId);

            ImageView cover = new ImageView();
            cover.setFitWidth(50);
            cover.setFitHeight(50);
            String imageUrl = libraryEntry.getCoverImage().getUrl();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                try { cover.setImage(new Image(imageUrl, true)); } catch (Exception e) {
                    System.err.println("Failed to load image for gameId " + gameId + ": " + imageUrl);
                }
            }

            Label name = new Label(libraryEntry.getTitle() + " (" + libraryEntry.getYear() + ")");
            name.setStyle("-fx-text-fill: white;");

            Button removeButton = new Button("Remove");
            removeButton.setMaxWidth(Region.USE_PREF_SIZE);
            removeButton.setMinWidth(Region.USE_PREF_SIZE);
            removeButton.setPrefWidth(120);
            removeButton.setMaxHeight(Region.USE_PREF_SIZE);
            removeButton.setMinHeight(Region.USE_PREF_SIZE);
            removeButton.setOnAction(e -> {
                userInteractor.removeFromLibrary(gameId);
                profileInteractor.removeEntry(gameId);
                refresh();
            });

            card.setOnMouseClicked(new GamePreviewClickHandler(gameCentralController));

            card.getChildren().addAll(cover, name, removeButton);
            card.setOnMouseEntered(e -> {
                card.setCursor(Cursor.HAND);
                card.setStyle("-fx-border-color:#00ffff; -fx-background-color:#3C3C3C; -fx-background-radius:5; -fx-border-radius:5; -fx-effect:dropshadow(gaussian, rgba(0,0,0,0.15), 6, 0.2, 0, 0);");
            });
            card.setOnMouseExited(e -> {
                card.setCursor(Cursor.DEFAULT);
                card.setStyle("-fx-border-color:#333333; -fx-background-color:#2C2C2C; -fx-background-radius:5; -fx-border-radius:5;");
            });

            libraryCards.add(card);
        }
    }

    private void loadPreviewCards() {
        Map<Integer, GamePreview> entries = profileInteractor.getPreviews();
        previewCards = new ArrayList<>();

        for (Map.Entry<Integer, GamePreview> entry : entries.entrySet()) {
            Integer gameId = entry.getKey();
            GamePreview preview = entry.getValue();

            HBox card = new HBox(5);
            card.setPadding(new Insets(10));
            card.setStyle("-fx-border-color:#333333; -fx-background-color:#2C2C2C; -fx-background-radius:5; -fx-border-radius:5;");
            card.setUserData(gameId);

            ImageView cover = new ImageView();
            cover.setFitWidth(50);
            cover.setFitHeight(50);
            String imageUrl = preview.getCoverImage().getUrl();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                try { cover.setImage(new Image(imageUrl, true)); } catch (Exception e) {
                    System.err.println("Failed to load image for gameId " + gameId + ": " + imageUrl);
                }
            }

            Label name = new Label(preview.getTitle() + " (" + preview.getYear() + ")");
            name.setStyle("-fx-text-fill: white;");

            Button removeButton = new Button("Remove");
            removeButton.setMaxWidth(Region.USE_PREF_SIZE);
            removeButton.setMinWidth(Region.USE_PREF_SIZE);
            removeButton.setPrefWidth(120);
            removeButton.setMaxHeight(Region.USE_PREF_SIZE);
            removeButton.setMinHeight(Region.USE_PREF_SIZE);
            removeButton.setOnAction(e -> {
                userInteractor.removeFromWishlist(gameId);
                profileInteractor.removePreview(gameId);
                refresh();
            });

            card.getChildren().addAll(cover, name, removeButton);
            previewCards.add(card);
        }
    }

    public void handleProfileUpdate(String newBio, String newImageUrl) {
        userInteractor.editProfilePicture(newImageUrl);
        userInteractor.editBio(newBio);
        view.setBio(newBio);
        view.setProfileImage(newImageUrl);
    }
}