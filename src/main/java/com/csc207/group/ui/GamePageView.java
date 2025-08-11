package com.csc207.group.ui;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class GamePageView implements View {

    private final StackPane root;
    private final VBox content;
    private final Label mainLabel;

    // Simple review inputs
    private final Label ratingLabel;
    private final TextField ratingField;
    private final Label reviewLabel;
    private final TextArea reviewArea;

    public GamePageView() {
        root = new StackPane();
        content = new VBox(12);
        content.setPadding(new Insets(16));

        mainLabel = new Label("This is the gamepage");
        mainLabel.setStyle("-fx-font-size: 24px;");

        ratingLabel = new Label("Your Rating (1–5):");
        ratingField = new TextField();
        ratingField.setPromptText("Enter a number from 1 to 5");

        reviewLabel = new Label("Your Review:");
        reviewArea = new TextArea();
        reviewArea.setPromptText("Write your review here...");
        reviewArea.setWrapText(true);
        reviewArea.setPrefRowCount(5);

        content.getChildren().addAll(
                mainLabel,
                ratingLabel,
                ratingField,
                reviewLabel,
                reviewArea
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
}

