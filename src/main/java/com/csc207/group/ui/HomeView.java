package com.csc207.group.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.List;

public class HomeView implements View {

    private final VBox layout;
    private final VBox resultsContainer;
    private final TextField searchField;
    private final Button searchButton;

    public HomeView() {
        // Main container with dark theme
        layout = new VBox(20);
        layout.setStyle("-fx-padding: 20; -fx-background-color: #222;");
        layout.setAlignment(Pos.TOP_CENTER);

        // Welcome copy
        Label welcomeLabel = new Label("WELCOME TO GAME CENTRAL!");
        welcomeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label subLabel = new Label("Start searching below to find new games.");
        subLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #ccc;");

        // --- Search Bar ---
        HBox searchBar = new HBox(10);
        searchBar.setAlignment(Pos.CENTER);

        searchField = new TextField();
        searchField.setPromptText("Search...");
        searchField.setStyle("-fx-font-size: 14px; -fx-pref-width: 350px;");

        searchButton = new Button("Search");
        searchButton.setStyle("-fx-background-color: #444; -fx-text-fill: white;");

        searchBar.getChildren().addAll(searchField, searchButton);

        // --- Results Container ---
        resultsContainer = new VBox(15);
        resultsContainer.setPadding(new Insets(15));

        ScrollPane scrollPane = new ScrollPane(resultsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: #333;");
        scrollPane.setPrefHeight(400);

        // Layout
        layout.getChildren().addAll(welcomeLabel, subLabel, searchBar, scrollPane);
    }

    // Hooks for controller
    public void setSearchButtonHandler(EventHandler<ActionEvent> handler) {
        searchButton.setOnAction(handler);
    }

    public String getSearchQuery() {
        return searchField.getText().trim();
    }

    public void clearResults() {
        resultsContainer.getChildren().clear();
    }

    public void addResult(Node n) {
        resultsContainer.getChildren().add(n);
    }

    public void displayGameResults(List<Node> resultNodes) {
        resultsContainer.getChildren().clear();

        if (resultNodes == null || resultNodes.isEmpty()) {
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

    public void setRecommendations(List<Node> recNodes, String message) {
        // Remove any existing recommendation section first
        // This ensures we don't keep stacking old sections
        layout.getChildren().removeIf(node -> node.getUserData() != null
                && "recommendations-section".equals(node.getUserData()));

        // Section container
        VBox section = new VBox(10);
        section.setUserData("recommendations-section"); // marker for easy removal
        section.setAlignment(Pos.TOP_LEFT);

        // Title label
        Label title = new Label(message);
        title.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");

        // Horizontal container for recommendation cards
        HBox recContainer = new HBox(10);
        recContainer.setPadding(new Insets(10));
        recContainer.setAlignment(Pos.CENTER_LEFT);

        if (recNodes != null && !recNodes.isEmpty()) {
            recContainer.getChildren().addAll(recNodes);
        } else {
            Label noRec = new Label("No recommendations available.");
            noRec.setStyle("-fx-text-fill: #ccc; -fx-font-size: 14px;");
            recContainer.getChildren().add(noRec);
        }

        // ScrollPane for horizontal scrolling
        ScrollPane scrollPane = new ScrollPane(recContainer);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
        scrollPane.setPannable(true);

        section.getChildren().addAll(title, scrollPane);

        // Add it at the bottom of the main layout
        layout.getChildren().add(section);
    }




}
