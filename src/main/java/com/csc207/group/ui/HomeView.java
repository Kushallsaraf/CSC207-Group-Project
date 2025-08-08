package com.csc207.group.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.List;

public class HomeView implements View {

    private final VBox layout;
    private final VBox resultsContainer;
    private final TextField searchField;
    private final Button searchButton;
    // Removed homeButton and profileButton

    public HomeView() {
        // Main container with dark theme
        layout = new VBox(20);
        layout.setStyle("-fx-padding: 20; -fx-background-color: #222;");
        layout.setAlignment(Pos.TOP_CENTER);

        // Welcome Message
        Label welcomeLabel = new Label("WELCOME TO GAME CENTRAL!");
        welcomeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label subLabel = new Label("Start searching below to find new games.");
        subLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #ccc;");

        // The Search Bar is no longer in the top nav, so it remains here.
        // If you want it in the top nav, remove it from here.

        // Results Container
        resultsContainer = new VBox(15);
        resultsContainer.setPadding(new Insets(15));
        ScrollPane scrollPane = new ScrollPane(resultsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: #333;");
        scrollPane.setPrefHeight(400);

        // Add all components to the layout (NavBar is removed)
        layout.getChildren().addAll(welcomeLabel, subLabel, scrollPane);
    }

    public void setSearchButtonHandler(javafx.event.EventHandler<javafx.event.ActionEvent> handler) {
        searchButton.setOnAction(handler);
    }

    public String getSearchQuery() {
        return searchField.getText().trim();
    }

    public void displayGameResults(List<Node> resultNodes) {
        resultsContainer.getChildren().clear();

        if (resultNodes.isEmpty()) {
            Label noResultsLabel = new Label("No results found.");
            noResultsLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
            resultsContainer.getChildren().add(noResultsLabel);
            return;
        }

        resultsContainer.getChildren().addAll(resultNodes);
    }

    @Override
    public String getName() {
        return "home";
    }

    @Override
    public Parent getView() {
        return layout;
    }

    @Override
    public void onShow() {
        searchField.clear();
        resultsContainer.getChildren().clear();
    }
}