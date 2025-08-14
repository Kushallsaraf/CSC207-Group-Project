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

/** Builds the common aspects of UserProfileView and PersonalProfileView. */
public abstract class AbstractProfileView extends VBox implements View {

    private static final int SPACING_MAIN = 20;
    private static final int PROFILE_IMAGE_SIZE = 100;
    private static final int PADDING_SMALL = 5;
    private static final int PADDING_MEDIUM = 10;
    private static final int PADDING_LARGE = 15;
    private static final int WIDTH_SCROLL = 400;
    private static final int HEIGHT_SCROLL = 500;
    private static final int POPUP_SPACING = 8;
    private static final String BG_COLOR = "-fx-background: #1E1E1E; -fx-background-color: #1E1E1E;";

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

    /** Constructs the profile view layout. */
    public AbstractProfileView() {
        this.setSpacing(SPACING_MAIN);
        this.setStyle("-fx-background-color: #121212; -fx-padding: 20;");

        // Profile header
        profileImageView.setFitWidth(PROFILE_IMAGE_SIZE);
        profileImageView.setFitHeight(PROFILE_IMAGE_SIZE);
        profileImageView.setPreserveRatio(false);
        statsBox.setPadding(new Insets(PADDING_SMALL, 0, 0, 0));

        usernameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");
        bioLabel.setStyle("-fx-text-fill: #B3B3B3;");
        followersLabel.setStyle("-fx-text-fill: #B3B3B3;");
        followingLabel.setStyle("-fx-text-fill: #B3B3B3;");

        textInfo.getChildren().addAll(usernameLabel, bioLabel, statsBox);
        textInfo.setPadding(new Insets(PADDING_SMALL));

        HBox profileHeader = new HBox(PADDING_LARGE, profileImageView, textInfo);
        profileHeader.setPadding(new Insets(PADDING_MEDIUM));
        profileHeader.setStyle("-fx-background-color: #1E1E1E; -fx-border-color: #333333; -fx-border-radius: 5; -fx-background-radius: 5;");

        // Wishlist
        Label wishlistLabel = new Label("Wishlist:");
        wishlistLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
        wishlistCardsBox.setPadding(new Insets(PADDING_MEDIUM));
        wishlistCardsBox.setStyle(BG_COLOR);
        ScrollPane wishlistScroll = new ScrollPane(wishlistCardsBox);
        wishlistScroll.setFitToWidth(true);
        wishlistScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        wishlistScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        wishlistScroll.setStyle(BG_COLOR);
        VBox.setVgrow(wishlistScroll, Priority.ALWAYS);
        wishlistContainer.getChildren().addAll(wishlistLabel, wishlistScroll);
        VBox.setVgrow(wishlistContainer, Priority.ALWAYS);

        // Library
        Label libraryLabel = new Label("Library:");
        libraryLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
        libraryCardsBox.setPadding(new Insets(PADDING_MEDIUM));
        libraryCardsBox.setStyle(BG_COLOR);
        ScrollPane libraryScroll = new ScrollPane(libraryCardsBox);
        libraryScroll.setFitToWidth(true);
        libraryScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        libraryScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        libraryScroll.setStyle(BG_COLOR);
        VBox.setVgrow(libraryScroll, Priority.ALWAYS);
        libraryContainer.getChildren().addAll(libraryLabel, libraryScroll);
        VBox.setVgrow(libraryContainer, Priority.ALWAYS);

        // Side-by-side
        final HBox contentBox = new HBox(SPACING_MAIN, wishlistContainer, libraryContainer);
        HBox.setHgrow(wishlistContainer, Priority.ALWAYS);
        HBox.setHgrow(libraryContainer, Priority.ALWAYS);
        wishlistContainer.setPrefWidth(WIDTH_SCROLL);
        libraryContainer.setPrefWidth(WIDTH_SCROLL);
        VBox.setVgrow(contentBox, Priority.ALWAYS);

        // Final layout
        this.getChildren().addAll(profileHeader, contentBox);
    }

    // ---- Shared API ----

    /**
     * Sets the wishlist cards safely.
     * @param cards the list of nodes to display in wishlist
     */
    public void setWishlistCards(List<javafx.scene.Node> cards) {
        wishlistCardsBox.getChildren().setAll(cards);
    }

    /**
     * Sets the library cards safely.
     * @param cards the list of nodes to display in library
     */
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
    public void onShow() {

    }

    /**
     * Sets the username.
     * @param username the user's username
     */
    public void setUsername(String username) {
        usernameLabel.setText(username);
    }

    /**
     * Sets the bio.
     * @param bio the user's bio
     */
    public void setBio(String bio) {
        bioLabel.setText(bio);
    }

    /**
     * Sets the profile image from URL.
     * @param imageUrl the URL of the image
     */
    public void setProfileImage(String imageUrl) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                profileImageView.setImage(new Image(imageUrl, true));
            }
            catch (Exception ex) {
                System.err.println("Failed to load profile image: " + imageUrl);
            }
        }
    }

    /**
     * Sets the followers count.
     * @param count number of followers
     */
    public void setFollowersCount(int count) {
        followersLabel.setText("Followers: " + count);
    }

    /**
     * Sets the following count.
     * @param count number of following
     */
    public void setFollowingCount(int count) {
        followingLabel.setText("Following: " + count);
    }

    // ---- Overloaded methods ----

    /**
     * Sets followers count and associates with username.
     * @param count followers count
     * @param username the username
     */
    public void setFollowersCount(int count, String username) {
        followersLabel.setText("Followers: " + count);
        followersLabel.setUserData(username);
    }

    /**
     * Sets following count and associates with username.
     * @param count following count
     * @param username the username
     */
    public void setFollowingCount(int count, String username) {
        followingLabel.setText("Following: " + count);
        followingLabel.setUserData(username);
    }

    // ---- Getters ----

    /**
     * @return the followers label
     */
    public Label getFollowersLabel() {
        return followersLabel;
    }

    /**
     * @return the following label
     */
    public Label getFollowingLabel() {
        return followingLabel;
    }

    /**
     * Shows a popup with a scrollable list of user nodes.
     * @param title the popup title
     * @param userNodes the list of nodes to display
     */
    public void showUsersPopup(String title, List<javafx.scene.Node> userNodes) {
        Stage popupStage = new Stage();
        popupStage.setTitle(title);
        popupStage.initModality(Modality.APPLICATION_MODAL);

        VBox listBox = new VBox(POPUP_SPACING);
        listBox.setPadding(new Insets(PADDING_MEDIUM));
        if (userNodes != null) {
            listBox.getChildren().setAll(userNodes);
        }

        ScrollPane scroll = new ScrollPane(listBox);
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setStyle(BG_COLOR);

        VBox root = new VBox(PADDING_MEDIUM, scroll);
        root.setPadding(new Insets(PADDING_MEDIUM));
        root.setStyle("-fx-background-color: #121212;");

        Scene scene = new Scene(root, HEIGHT_SCROLL, WIDTH_SCROLL);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }
}
