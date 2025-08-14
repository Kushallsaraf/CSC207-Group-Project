package com.csc207.group.ui;

import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/** Builds the common aspects of UserProfileView and PersonalProfileView */

public class AbstractProfileView extends VBox implements View {

    private static final int CONSTANT_1 = 20;
    private static final int CONSTANT_2 = 100;
    private static final int CONSTANT_3 = 5;
    private static final int CONSTANT_4 = 10;
    private static final int CONSTANT_5 = 15;
    private static final int CONSTANT_6 = 400;
    private static final int CONSTANT_7 = 500;
    private static final int CONSTANT_8 = 380;
    private static final int CONSTANT_9 = 8;

    // Containers
    private final VBox wishlistContainer = new VBox(10);
    private final VBox libraryContainer = new VBox(10);
    private final VBox wishlistCardsBox = new VBox(10);
    private final VBox libraryCardsBox = new VBox(10);

    // Profile items
    protected final Label usernameLabel = new Label();
    protected final Label bioLabel = new Label();
    protected final ImageView profileImageView = new ImageView();
    protected final Label followersLabel = new Label("Followers: 0");
    protected final Label followingLabel = new Label("Following: 0");
    protected final HBox statsBox = new HBox(15, followersLabel, followingLabel);

    // Let subclasses add actions (e.g., Edit button) after construction
    protected final VBox textInfo = new VBox(5);

    public AbstractProfileView() {
        this.setSpacing(CONSTANT_1);
        this.setStyle("-fx-background-color: #121212; -fx-padding: 20;");

        // Profile header
        profileImageView.setFitWidth(CONSTANT_2);
        profileImageView.setFitHeight(CONSTANT_2);
        profileImageView.setPreserveRatio(false);
        statsBox.setPadding(new Insets(CONSTANT_3, 0, 0, 0));

        usernameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");
        bioLabel.setStyle("-fx-text-fill: #B3B3B3;");
        followersLabel.setStyle("-fx-text-fill: #B3B3B3;");
        followingLabel.setStyle("-fx-text-fill: #B3B3B3;");

        textInfo.getChildren().addAll(usernameLabel, bioLabel, statsBox);
        textInfo.setPadding(new Insets(CONSTANT_3));

        HBox profileHeader = new HBox(CONSTANT_5, profileImageView, textInfo);
        profileHeader.setPadding(new Insets(CONSTANT_4));
        profileHeader.setStyle("-fx-background-color: #1E1E1E; -fx-border-color: #333333; -fx-border-radius: 5; -fx-background-radius: 5;");

        // Wishlist
        Label wishlistLabel = new Label("Wishlist:");
        wishlistLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
        wishlistCardsBox.setPadding(new Insets(CONSTANT_4));
        wishlistCardsBox.setStyle("-fx-background-color: #1E1E1E;");
        ScrollPane wishlistScroll = new ScrollPane(wishlistCardsBox);
        wishlistScroll.setFitToWidth(true);
        wishlistScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        wishlistScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        wishlistScroll.setStyle("-fx-background: #1E1E1E; -fx-background-color: #1E1E1E;");
        VBox.setVgrow(wishlistScroll, Priority.ALWAYS);
        wishlistContainer.getChildren().addAll(wishlistLabel, wishlistScroll);
        VBox.setVgrow(wishlistContainer, Priority.ALWAYS);

        // Library
        Label libraryLabel = new Label("Library:");
        libraryLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
        libraryCardsBox.setPadding(new Insets(CONSTANT_4));
        libraryCardsBox.setStyle("-fx-background-color: #1E1E1E;");
        ScrollPane libraryScroll = new ScrollPane(libraryCardsBox);
        libraryScroll.setFitToWidth(true);
        libraryScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        libraryScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        libraryScroll.setStyle("-fx-background: #1E1E1E; -fx-background-color: #1E1E1E;");
        VBox.setVgrow(libraryScroll, Priority.ALWAYS);
        libraryContainer.getChildren().addAll(libraryLabel, libraryScroll);
        VBox.setVgrow(libraryContainer, Priority.ALWAYS);

        // Side-by-side
        HBox contentBox = new HBox(CONSTANT_1, wishlistContainer, libraryContainer);
        HBox.setHgrow(wishlistContainer, Priority.ALWAYS);
        HBox.setHgrow(libraryContainer, Priority.ALWAYS);
        wishlistContainer.setPrefWidth(CONSTANT_6);
        libraryContainer.setPrefWidth(CONSTANT_6);
        VBox.setVgrow(contentBox, Priority.ALWAYS);

        // Final layout
        this.getChildren().addAll(profileHeader, contentBox);
    }

    // ---- Shared API ----
    public void setWishlistCards(List<javafx.scene.Node> cards) {
        wishlistCardsBox.getChildren().setAll(cards);
    }

    public void setLibraryCards(List<javafx.scene.Node> cards) {
        libraryCardsBox.getChildren().setAll(cards);
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
    public void onShow() { }

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
            }
            catch (Exception e) {
                System.err.println("Failed to load profile image: " + imageUrl);
            }
        }
    }

    public void setFollowersCount(int count) {
        followersLabel.setText("Followers: " + count);
    }

    public void setFollowingCount(int count) {
        followingLabel.setText("Following: " + count);
    }

    // ---- New getters for labels ----
    public Label getFollowersLabel() {
        return followersLabel;
    }

    public Label getFollowingLabel() {
        return followingLabel;
    }

    // ---- Popup to show a scrollable list of user nodes ----
    public void showUsersPopup(String title, List<javafx.scene.Node> userNodes) {
        Stage popupStage = new Stage();
        popupStage.setTitle(title);
        popupStage.initModality(Modality.APPLICATION_MODAL);

        VBox listBox = new VBox(CONSTANT_9);
        listBox.setPadding(new Insets(CONSTANT_4));
        if (userNodes != null) {
            listBox.getChildren().setAll(userNodes);
        }

        ScrollPane scroll = new ScrollPane(listBox);
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setStyle("-fx-background: #1E1E1E; -fx-background-color: #1E1E1E;");

        VBox root = new VBox(CONSTANT_4, scroll);
        root.setPadding(new Insets(CONSTANT_4));
        root.setStyle("-fx-background-color: #121212;");

        Scene scene = new Scene(root, CONSTANT_8, CONSTANT_7);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }

    public void setFollowersCount(int count, String username) {
        followersLabel.setText("Followers: " + count);
        followersLabel.setUserData(username);
    }

    public void setFollowingCount(int count, String username) {
        followingLabel.setText("Following: " + count);
        followingLabel.setUserData(username);
    }
}
