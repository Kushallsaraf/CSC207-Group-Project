package ui;

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

    public HomeView() {
        searchField = new TextField();
        searchField.setPromptText("Search for a game...");

        searchButton = new Button("Search");

        HBox searchBar = new HBox(10, searchField, searchButton);
        searchBar.setPadding(new Insets(15));
        searchBar.setAlignment(Pos.CENTER);

        resultsContainer = new VBox(15);
        resultsContainer.setPadding(new Insets(15));

        ScrollPane scrollPane = new ScrollPane(resultsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(400);

        layout = new VBox(20, searchBar, scrollPane);
        layout.setPadding(new Insets(20));
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
            resultsContainer.getChildren().add(new Label("No results found."));
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


