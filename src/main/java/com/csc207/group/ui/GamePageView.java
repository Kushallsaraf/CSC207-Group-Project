package com.csc207.group.ui;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class GamePageView implements View {

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
    private final VBox reviewsContainer;     // holds review nodes
    private final ScrollPane reviewsScroll;  // scrolls vertically

    public GamePageView() {
        root = new StackPane();
        content = new VBox(12);
        content.setPadding(new Insets(16));

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
        reviewArea.setPrefRowCount(5);

        // Reviews section UI
        reviewsHeaderLabel = new Label("Reviews");
        reviewsHeaderLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        reviewsContainer = new VBox(8);
        reviewsContainer.setPadding(new Insets(8, 0, 0, 0));

        reviewsScroll = new ScrollPane(reviewsContainer);
        reviewsScroll.setFitToWidth(true);
        reviewsScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        reviewsScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        reviewsScroll.setPadding(new Insets(0));
        // Let the scroll area grow to fill remaining space in VBox
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

    public void setTitle(String name) {
        mainLabel.setText("this is the gamepage of " + name);
    }

    public TextArea getReviewArea(){
        return this.reviewArea;
    }

    public TextField getRatingField(){
        return this.ratingField;
    }

    public void clearReviewFields() {
        this.ratingField.clear();
        this.reviewArea.clear();
    }

    /**
     * Populates the reviews section with nodes provided by the controller.
     * Existing nodes are cleared before adding the new ones.
     */
    public void setReviewNodes(List<Node> reviewNodes) {
        this.reviewsContainer.getChildren().clear();
        if (reviewNodes != null && !reviewNodes.isEmpty()) {
            this.reviewsContainer.getChildren().addAll(reviewNodes);
        } else {
            this.reviewsContainer.getChildren().add(new Label("No reviews yet."));
        }
    }

    public Button getSubmitReviewButton(){
        return this.submitReviewButton;
    }
}


