package com.csc207.group.ui;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.List;

/**Builds the common aspects of UserProfileView and PersonalProfileView
 *
 */
public class AbstractProfileView extends VBox implements View {

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
        this.setSpacing(20);
        this.setPadding(new Insets(20));

        // Profile header
        profileImageView.setFitWidth(100);
        profileImageView.setFitHeight(100);
        profileImageView.setPreserveRatio(false);
        statsBox.setPadding(new Insets(5, 0, 0, 0));

        textInfo.getChildren().addAll(usernameLabel, bioLabel, statsBox);
        textInfo.setPadding(new Insets(5));

        HBox profileHeader = new HBox(15, profileImageView, textInfo);
        profileHeader.setPadding(new Insets(10));
        profileHeader.setStyle("-fx-background-color: #f2f2f2; -fx-border-color: #ccc; -fx-border-radius: 5; -fx-background-radius: 5;");

        // Wishlist
        Label wishlistLabel = new Label("Wishlist:");
        wishlistCardsBox.setPadding(new Insets(10));
        ScrollPane wishlistScroll = new ScrollPane(wishlistCardsBox);
        wishlistScroll.setFitToWidth(true);
        wishlistScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        wishlistScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        VBox.setVgrow(wishlistScroll, Priority.ALWAYS);
        wishlistContainer.getChildren().addAll(wishlistLabel, wishlistScroll);
        VBox.setVgrow(wishlistContainer, Priority.ALWAYS);

        // Library
        Label libraryLabel = new Label("Library:");
        libraryCardsBox.setPadding(new Insets(10));
        ScrollPane libraryScroll = new ScrollPane(libraryCardsBox);
        libraryScroll.setFitToWidth(true);
        libraryScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        libraryScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        VBox.setVgrow(libraryScroll, Priority.ALWAYS);
        libraryContainer.getChildren().addAll(libraryLabel, libraryScroll);
        VBox.setVgrow(libraryContainer, Priority.ALWAYS);

        // Side-by-side
        HBox contentBox = new HBox(20, wishlistContainer, libraryContainer);
        HBox.setHgrow(wishlistContainer, Priority.ALWAYS);
        HBox.setHgrow(libraryContainer, Priority.ALWAYS);
        wishlistContainer.setPrefWidth(400);
        libraryContainer.setPrefWidth(400);
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
            } catch (Exception e) {
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
}
