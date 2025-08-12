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
import com.csc207.group.service.UserProfileInteractor;
import com.csc207.group.service.UserInteractor;
import com.csc207.group.ui.PersonalProfileView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PersonalProfileController {


    private final UserProfileInteractor profileInteractor;
    private final UserInteractor userInteractor;
    private PersonalProfileView view;
    private final GameCentralController gameCentralController;
    private List<Node> previewCards;
    private List<Node> libraryCards;



    public PersonalProfileController(UserProfileInteractor profileInteractor, UserInteractor userInteractor,
                                     PersonalProfileView view, GameCentralController gameCentralController) {
        this.profileInteractor = profileInteractor;
        this.userInteractor = userInteractor;
        this.view = view;
        view.setFollowersCount(userInteractor.getUser().getNumberOfFollowers());
        view.setFollowingCount(userInteractor.getUser().getNumberOfFollowing());
        this.gameCentralController = gameCentralController;


    }

    private void loadLibraryCards(){
        Map<Integer, LibraryEntry> entries = profileInteractor.getEntries();
        libraryCards = new ArrayList<>();

        for (Map.Entry<Integer, LibraryEntry> entry : entries.entrySet()) {
            Integer gameId = entry.getKey();
            LibraryEntry libraryEntry = entry.getValue();

            HBox card = new HBox(5);
            card.setPadding(new Insets(10));
            card.setStyle("-fx-border-color: #ccc; -fx-background-color: #f9f9f9; -fx-background-radius: 5;" +
                    " -fx-border-radius: 5;");
            card.setUserData(gameId);

            ImageView cover = new ImageView();
            cover.setFitWidth(50);
            cover.setFitHeight(50);
            String imageUrl = libraryEntry.getCoverImage().getUrl();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                try {
                    cover.setImage(new Image(imageUrl, true));
                } catch (Exception e) {
                    System.err.println("Failed to load image for gameId " + gameId + ": " + imageUrl);
                }
            }

            Label name = new Label(libraryEntry.getTitle() + " (" + libraryEntry.getYear() + ")");


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
                card.setStyle(
                        "-fx-border-color: #999; " + // slightly darker border
                                "-fx-background-color: #f0f0f0; " + // a bit darker background
                                "-fx-background-radius: 5; " +
                                "-fx-border-radius: 5; " +
                                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 6, 0.2, 0, 0);" // soft shadow
                );
            });

            card.setOnMouseExited(e -> {
                card.setCursor(Cursor.DEFAULT);
                card.setStyle(
                        "-fx-border-color: #ccc; " +
                                "-fx-background-color: #f9f9f9; " +
                                "-fx-background-radius: 5; " +
                                "-fx-border-radius: 5;"
                );
            });
            card.setUserData(gameId);

            libraryCards.add(card);
        }
        }

    public void refresh() {
        view.setController(this);
        view.setProfileImage(profileInteractor.getProfilePictureUrl());
        view.setBio(profileInteractor.getBio());

        view.getEditProfileButton().setOnAction(e -> {
            String currentBio = profileInteractor.getBio();
            String currentImageUrl = profileInteractor.getProfilePictureUrl();
            view.showEditProfilePopup(currentBio, currentImageUrl);
        });

        view.setUsername(profileInteractor.getUsername());
        profileInteractor.reload();
        loadLibraryCards();
        loadPreviewCards();
        view.setLibraryCards(libraryCards);
        view.setWishlistCards(previewCards);
    }



    private void loadPreviewCards() {
        Map<Integer, GamePreview> entries = profileInteractor.getPreviews();
        previewCards = new ArrayList<>();

        for (Map.Entry<Integer, GamePreview> entry : entries.entrySet()) {
            Integer gameId = entry.getKey();
            GamePreview preview = entry.getValue();

            HBox card = new HBox(5);
            card.setPadding(new Insets(10));
            card.setStyle("-fx-border-color: #ccc; -fx-background-color: #f9f9f9; -fx-background-radius: 5;" +
                    " -fx-border-radius: 5;");
            card.setUserData(gameId);

            ImageView cover = new ImageView();
            cover.setFitWidth(50);
            cover.setFitHeight(50);
            String imageUrl = preview.getCoverImage().getUrl();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                try {
                    cover.setImage(new Image(imageUrl, true));
                } catch (Exception e) {
                    System.err.println("Failed to load image for gameId " + gameId + ": " + imageUrl);
                }
            }

            Label name = new Label(preview.getTitle() + " (" + preview.getYear() + ")");

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
            card.setUserData(gameId);
            previewCards.add(card);
        }
    }

    public void handleProfileUpdate(String newBio, String newImageUrl) {
        this.userInteractor.editProfilePicture(newImageUrl);
        this.userInteractor.editBio(newBio);
        this.view.setBio(newBio);
        this.view.setProfileImage(newImageUrl);
    }
}
