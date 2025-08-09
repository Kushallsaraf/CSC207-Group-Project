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
}
