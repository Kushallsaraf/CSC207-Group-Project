package com.csc207.group.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.csc207.group.ui.controller.EditProfileSaveHandler;
import com.csc207.group.ui.controller.UserProfileController;

import java.util.List;

public class UserProfileView extends VBox implements ui.View {

    private final Button homeButton = new Button("Home");
    private final Button profileButton = new Button("Profile");

    private final VBox wishlistContainer = new VBox(10);
    private final VBox libraryContainer = new VBox(10);

    private final VBox wishlistCardsBox = new VBox(10);
    private final VBox libraryCardsBox = new VBox(10);

    //User profile items
    private final Label usernameLabel = new Label();
    private final Label bioLabel = new Label();
    private final ImageView profileImageView = new ImageView();
    private final Button editProfileButton = new Button("Edit");

    private String newBio;
    private String newImageUrl;
    private UserProfileController controller;

    public UserProfileView() {
        this.setSpacing(20);
        this.setPadding(new Insets(20));
        newBio = "";
        newImageUrl = "";

        // Nav bar
        HBox navBar = new HBox(10, homeButton, profileButton);

        // Profile info section
        profileImageView.setFitWidth(100);
        profileImageView.setFitHeight(100);
        profileImageView.setPreserveRatio(false);


        VBox textInfo = new VBox(5, usernameLabel, bioLabel, editProfileButton);
        textInfo.setPadding(new Insets(5));

        HBox profileHeader = new HBox(15, profileImageView, textInfo);
        profileHeader.setPadding(new Insets(10));
        profileHeader.setStyle("-fx-background-color: #f2f2f2; -fx-border-color: #ccc; -fx-border-radius: 5; -fx-background-radius: 5;");

        // Wishlist section
        Label wishlistLabel = new Label("Your Wishlist:");
        wishlistCardsBox.setPadding(new Insets(10));

        ScrollPane wishlistScroll = new ScrollPane(wishlistCardsBox);
        wishlistScroll.setFitToWidth(true);
        wishlistScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        wishlistScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        VBox.setVgrow(wishlistScroll, Priority.ALWAYS);

        wishlistContainer.getChildren().addAll(wishlistLabel, wishlistScroll);
        VBox.setVgrow(wishlistContainer, Priority.ALWAYS);

        // Library section
        Label libraryLabel = new Label("Your Library:");
        libraryCardsBox.setPadding(new Insets(10));

        ScrollPane libraryScroll = new ScrollPane(libraryCardsBox);
        libraryScroll.setFitToWidth(true);
        libraryScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        libraryScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        VBox.setVgrow(libraryScroll, Priority.ALWAYS);

        libraryContainer.getChildren().addAll(libraryLabel, libraryScroll);
        VBox.setVgrow(libraryContainer, Priority.ALWAYS);

        // Wishlist and Library side-by-side
        HBox contentBox = new HBox(20, wishlistContainer, libraryContainer);
        HBox.setHgrow(wishlistContainer, Priority.ALWAYS);
        HBox.setHgrow(libraryContainer, Priority.ALWAYS);

        wishlistContainer.setPrefWidth(400);
        libraryContainer.setPrefWidth(400);
        VBox.setVgrow(contentBox, Priority.ALWAYS);

        // Final layout
        this.getChildren().addAll(navBar, profileHeader, contentBox);
    }

    public void setWishlistCards(List<javafx.scene.Node> cards) {
        wishlistCardsBox.getChildren().setAll(cards);
    }

    public void setLibraryCards(List<javafx.scene.Node> cards) {
        libraryCardsBox.getChildren().setAll(cards);
    }

    public Button getHomeButton() {
        return homeButton;
    }

    public Button getProfileButton() {
        return profileButton;
    }

    @Override
    public String getName() {
        return "userProfile";
    }

    @Override
    public Parent getView() {
        return this;
    }

    @Override
    public void onShow() {
    }

    public void setUsername(String username) {
        usernameLabel.setText(username);
    }



    public void setBio(String bio) {
        bioLabel.setText(bio);
    }

    public void setProfileImage(String imageUrl) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                profileImageView.setImage(new Image(imageUrl, true));
            } catch (Exception e) {
                System.err.println("Failed to load profile image: " + imageUrl);
            }
        }
    }

    public Button getEditProfileButton() {
        return editProfileButton;
    }

    public void showEditProfilePopup(String currentBio, String currentImageUrl) {
        Stage popupStage = new Stage();
        popupStage.setTitle("Edit Profile");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));

        Label bioLabel = new Label("Bio:");
        TextField bioField = new TextField(currentBio);

        Label imageLabel = new Label("Profile Image URL:");
        TextField imageField = new TextField(currentImageUrl);

        HBox buttonBox = new HBox(10);
        Button saveButton = new Button("Save");
        Button cancelButton = new Button("Cancel");
        buttonBox.getChildren().addAll(saveButton, cancelButton);

        layout.getChildren().addAll(bioLabel, bioField, imageLabel, imageField, buttonBox);

        Scene scene = new Scene(layout, 350, 200);
        popupStage.setScene(scene);
        popupStage.initModality(Modality.APPLICATION_MODAL);

        saveButton.setOnAction(new EditProfileSaveHandler(bioField, imageField, popupStage, controller));

        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                popupStage.close();
            }
        });

        popupStage.showAndWait();
    }

    public String getNewBio(){
        return this.newBio;
    }

    public String getNewImageUrl(){
        return this.newImageUrl;
    }


    public void setController(UserProfileController userProfileController) {
        this.controller = userProfileController;
    }
}




