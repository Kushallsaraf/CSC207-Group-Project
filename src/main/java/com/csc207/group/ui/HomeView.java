package com.csc207.group.ui;

import java.util.List;

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
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Represents the home view for the Game Central application.
 * Allows searching for games, displaying results, and showing recommendations.
 * Designed for extension if needed.
 */
public class HomeView implements View {

    private static final int CONSTANT_1 = 10;
    private static final int CONSTANT_2 = 15;
    private static final int CONSTANT_3 = 20;
    private static final int CONSTANT_4 = 400;

    private final VBox layout;
    private final VBox resultsContainer;
    private final TextField searchField;
    private final Button searchButton;
    private final Button genreSearchButton;

    /**
     * Constructs the home view UI.
     */
    public HomeView() {
        layout = new VBox(CONSTANT_3);
        layout.setStyle("-fx-padding: 20; -fx-background-color: #222;");
        layout.setAlignment(Pos.TOP_CENTER);

        Label welcomeLabel = new Label("WELCOME TO GAME CENTRAL!");
        welcomeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label subLabel = new Label("Start searching below to find new games.");
        subLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #ccc;");

        // Search bar
        HBox searchBar = new HBox(CONSTANT_1);
        searchBar.setAlignment(Pos.CENTER);

        searchField = new TextField();
        searchField.setPromptText("Search...");
        searchField.setStyle("-fx-font-size: 14px; -fx-pref-width: 350px;");

        searchButton = new Button("Search");
        searchButton.setStyle("-fx-background-color: #444; -fx-text-fill: white;");

        genreSearchButton = new Button("Search by Genre");
        genreSearchButton.setStyle("-fx-background-color: #444; -fx-text-fill: white;");

        searchBar.getChildren().addAll(searchField, searchButton, genreSearchButton);

        // Press Enter to fire search
        searchField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                searchButton.fire();
            }
        });

        // Results container
        resultsContainer = new VBox(CONSTANT_2);
        resultsContainer.setPadding(new Insets(CONSTANT_2));

        ScrollPane scrollPane = new ScrollPane(resultsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: #333;");
        scrollPane.setPrefHeight(CONSTANT_4);

        layout.getChildren().addAll(welcomeLabel, subLabel, searchBar, scrollPane);
    }

    /**
     * Sets the event handler for the search button.
     *
     * @param handler the ActionEvent handler
     */
    public void setSearchButtonHandler(EventHandler<ActionEvent> handler) {
        searchButton.setOnAction(handler);
    }

    /**
     * Sets the event handler for the genre search button.
     *
     * @param handler the ActionEvent handler
     */
    public void setGenreSearchButtonHandler(EventHandler<ActionEvent> handler) {
        genreSearchButton.setOnAction(handler);
    }

    /**
     * Returns the current search query from the search field.
     *
     * @return the search query as a String
     */
    public String getSearchQuery() {
        return searchField.getText().trim();
    }

    /**
     * Clears all game results currently displayed.
     */
    public void clearResults() {
        resultsContainer.getChildren().clear();
    }

    /**
     * Adds a single game result node to the results container.
     *
     * @param node the Node representing a game result
     */
    public void addResult(Node node) {
        resultsContainer.getChildren().add(node);
    }

    /**
     * Displays a list of game result nodes. If the list is empty or null, shows a "No results" message.
     *
     * @param resultNodes the list of result Nodes
     */
    public void displayGameResults(List<Node> resultNodes) {
        resultsContainer.getChildren().clear();

        if (resultNodes == null || resultNodes.isEmpty()) {
            Label noResultsLabel = new Label("No results found.");
            noResultsLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
            resultsContainer.getChildren().add(noResultsLabel);
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

    /**
     * Displays a recommendation section at the bottom of the home view.
     *
     * @param recNodes list of recommendation nodes to display
     * @param message  title message for the recommendation section
     */
    public void setRecommendations(List<Node> recNodes, String message) {
        layout.getChildren().removeIf(node -> {
            Object userData = node.getUserData();
            return userData != null && "recommendations-section".equals(userData);
        });

        VBox section = new VBox(CONSTANT_1);
        section.setUserData("recommendations-section");
        section.setAlignment(Pos.TOP_LEFT);

        Label title = new Label(message);
        title.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");

        HBox recContainer = new HBox(CONSTANT_1);
        recContainer.setPadding(new Insets(CONSTANT_1));
        recContainer.setAlignment(Pos.CENTER_LEFT);

        if (recNodes != null && !recNodes.isEmpty()) {
            recContainer.getChildren().addAll(recNodes);
        }
        else {
            Label noRec = new Label("No recommendations available.");
            noRec.setStyle("-fx-text-fill: #ccc; -fx-font-size: 14px;");
            recContainer.getChildren().add(noRec);
        }

        ScrollPane scrollPane = new ScrollPane(recContainer);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
        scrollPane.setPannable(true);

        section.getChildren().addAll(title, scrollPane);
        layout.getChildren().add(section);
    }
}
