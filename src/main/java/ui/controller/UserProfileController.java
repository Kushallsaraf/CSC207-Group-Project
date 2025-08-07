package ui.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import model.GamePreview;
import model.LibraryEntry;
import service.GameService;
import service.UserProfileInteractor;
import service.UserInteractor;
import ui.UserProfileView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserProfileController {


    private final UserProfileInteractor profileInteractor;
    private final UserInteractor userInteractor;
    private UserProfileView view;
    private List<Node> previewCards;
    private List<Node> libraryCards;



    public UserProfileController(UserProfileInteractor profileInteractor, UserInteractor userInteractor,
                                 UserProfileView view) {
        this.profileInteractor = profileInteractor;
        this.userInteractor = userInteractor;
        this.view = view;

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

            card.getChildren().addAll(cover, name, removeButton);
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
