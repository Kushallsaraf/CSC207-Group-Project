package com.csc207.group.ui;

import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Represents the view for a game's page, including review submission and display of reviews.
 * Designed for extension if needed.
 */
public class GamePageView implements View {

    private static final int CONSTANT_1 = 8;
    private static final int CONSTANT_2 = 16;
    private static final int CONSTANT_3 = 12;
    private static final int CONSTANT_4 = 5;

    private final StackPane root;
    private final VBox content;
    private final Label mainLabel;
    private final Button submitReviewButton;

    // Simple review inputs
    private final Label ratingLabel;
    private final TextField ratingField;
    private final Label reviewLabel;
    private final TextArea reviewArea;

    // Reviews section
    private final Label reviewsHeaderLabel;
    private final VBox reviewsContainer;
    private final ScrollPane reviewsScroll;

    /**
     * Constructs a new GamePageView and sets up the UI components.
     */
    public GamePageView() {
        root = new StackPane();
        content = new VBox(CONSTANT_3);
        content.setPadding(new Insets(CONSTANT_2));

        mainLabel = new Label("This is the gamepage");
        mainLabel.setStyle("-fx-font-size: 24px;");

        submitReviewButton = new Button("Submit Review");

        ratingLabel = new Label("Your Rating (1–5):");
        ratingField = new TextField();
        ratingField.setPromptText("Enter a number from 1 to 5");

        reviewLabel = new Label("Your Review:");
        reviewArea = new TextArea();
        reviewArea.setPromptText("Write your review here...");
        reviewArea.setWrapText(true);
        reviewArea.setPrefRowCount(CONSTANT_4);

        // Reviews section UI
        reviewsHeaderLabel = new Label("Reviews");
        reviewsHeaderLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        reviewsContainer = new VBox(CONSTANT_1);
        reviewsContainer.setPadding(new Insets(CONSTANT_1, 0, 0, 0));

        reviewsScroll = new ScrollPane(reviewsContainer);
        reviewsScroll.setFitToWidth(true);
        reviewsScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        reviewsScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        reviewsScroll.setPadding(new Insets(0));
        VBox.setVgrow(reviewsScroll, Priority.ALWAYS);

        content.getChildren().addAll(
                mainLabel,
                ratingLabel,
                ratingField,
                reviewLabel,
                reviewArea,
                submitReviewButton,
                new Separator(),
                reviewsHeaderLabel,
                reviewsScroll
        );

        root.getChildren().add(content);
    }

    @Override
    public String getName() {
        return "Hello View";
    }

    @Override
    public Parent getView() {
        return root;
    }

    @Override
    public void onShow() {
        // No special action needed for demo
    }

    /**
     * Updates the main label to display the game's name.
     *
     * @param name the name of the game
     */
    public void setTitle(String name) {
        mainLabel.setText("This is the gamepage of " + name);
    }

    /**
     * Returns the TextArea for entering a review.
     *
     * @return the review TextArea
     */
    public TextArea getReviewArea() {
        return this.reviewArea;
    }

    /**
     * Returns the TextField for entering a rating.
     *
     * @return the rating TextField
     */
    public TextField getRatingField() {
        return this.ratingField;
    }

    /**
     * Clears both the rating field and review area.
     */
    public void clearReviewFields() {
        this.ratingField.clear();
        this.reviewArea.clear();
    }

    /**
     * Populates the reviews section with nodes provided by the controller.
     * Existing nodes are cleared before adding the new ones.
     *
     * @param reviewNodes list of nodes representing individual reviews
     */
    public void setReviewNodes(List<Node> reviewNodes) {
        this.reviewsContainer.getChildren().clear();
        if (reviewNodes != null && !reviewNodes.isEmpty()) {
            this.reviewsContainer.getChildren().addAll(reviewNodes);
        }
        else {
            this.reviewsContainer.getChildren().add(new Label("No reviews yet."));
        }
    }

    /**
     * Returns the button for submitting a review.
     *
     * @return the submit review Button
     */
    public Button getSubmitReviewButton() {
        return this.submitReviewButton;
    }
}
