package com.csc207.group.views;

import java.util.List;
import java.util.function.Consumer;

import com.csc207.group.model.News;
import com.csc207.group.ui.components.NewsCard;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class NewsView {
    private static final int VIEW_SPACING = 30;
    private static final int GRID_HGAP = 25;
    private static final int GRID_VGAP = 25;
    private static final int TOOLTIP_FONT_SIZE = 12;
    private static final int MAX_COLUMNS = 3;

    private VBox view;
    private Consumer<String> onArticleClick;

    public NewsView(List<News> newsArticles, Consumer<String> onArticleClick) {
        this.onArticleClick = onArticleClick;

        view = new VBox(VIEW_SPACING);
        view.setStyle("-fx-padding: 20; -fx-background-color: #222;");
        view.setAlignment(Pos.TOP_CENTER);

        Label headerLabel = new Label("News");
        headerLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label subHeaderLabel = new Label("Here you can find all the news about your favourite games.");
        subHeaderLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #ccc;");

        GridPane newsGrid = createNewsGrid(newsArticles);

        view.getChildren().addAll(headerLabel, subHeaderLabel, newsGrid);
    }

    private GridPane createNewsGrid(List<News> newsArticles) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(GRID_HGAP);
        gridPane.setVgap(GRID_VGAP);
        gridPane.setAlignment(Pos.CENTER);

        int col = 0;
        int row = 0;
        for (News article : newsArticles) {
            // Create the card with the correct image URL
            NewsCard card = new NewsCard(article.getTitle(), article.getImageUrl());
            Node cardView = card.getView();

            // Set the click listener for the card
            cardView.setOnMouseClicked(event -> onArticleClick.accept(article.getUrl()));
            cardView.setCursor(Cursor.HAND);

            // --- NEW: Add a tooltip on hover ---
            // 1. Create the text for the tooltip
            String tooltipText = "Author: " + article.getAuthor() + "\nPublished: " + article.getDate();

            // 2. Create the Tooltip object
            Tooltip tooltip = new Tooltip(tooltipText);
            tooltip.setFont(new Font(TOOLTIP_FONT_SIZE));
            tooltip.setStyle("-fx-background-color: #333; -fx-text-fill: white; -fx-background-radius: 5;");

            // 3. Install the tooltip on the card's view
            Tooltip.install(cardView, tooltip);
            // --- END of new code ---

            gridPane.add(cardView, col, row);
            col++;
            if (col == MAX_COLUMNS) {
                col = 0;
                row++;
            }
        }
        return gridPane;
    }
    /**
     * Returns the main view node for this NewsView.
     * Subclasses may override this to provide a modified layout,
     * but must ensure that the returned Pane remains non-null and
     * contains the full content of the NewsView.
     *
     * @return the VBox representing the view
     */

    public Pane getView() {
        return view;
    }
}
