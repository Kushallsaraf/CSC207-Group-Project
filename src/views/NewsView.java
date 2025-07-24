package views;

import components.NewsCard;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class NewsView {
    private VBox view;

    public NewsView() {
        view = new VBox(30);
        // Changed to dark background to match the app theme
        view.setStyle("-fx-padding: 20; -fx-background-color: #222;");
        view.setAlignment(Pos.TOP_CENTER);

        // Header Text
        Label headerLabel = new Label("News");
        // Changed text fill to white
        headerLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label subHeaderLabel = new Label("Here you can find all the news about your favourite games.");
        // Changed text fill to light gray
        subHeaderLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #ccc;");

        // Grid for News Cards (No changes needed for the cards themselves)
        GridPane newsGrid = createNewsGrid();

        view.getChildren().addAll(headerLabel, subHeaderLabel, newsGrid);
    }

    /**
     * Creates a grid of news cards.
     * @return GridPane populated with news cards.
     */
    private GridPane createNewsGrid() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(25);
        gridPane.setVgap(25);
        gridPane.setAlignment(Pos.CENTER);

        gridPane.add(new NewsCard("Clair Obscur Expedition 33 on Sale on PS Store", "https://placehold.co/300x170/2a2a2a/ffffff?text=News+1").getView(), 0, 0);
        gridPane.add(new NewsCard("Donkey Kong Bananza Releases for Switch 2", "https://placehold.co/300x170/e84118/ffffff?text=News+2").getView(), 1, 0);
        gridPane.add(new NewsCard("Gran Turismo 7 Gets a New Update July 23", "https://placehold.co/300x170/00a8ff/ffffff?text=News+3").getView(), 2, 0);
        gridPane.add(new NewsCard("New Pokemon Z+A Switch 2 Bundle Announced", "https://placehold.co/300x170/f5f6fa/000000?text=News+4").getView(), 0, 1);
        gridPane.add(new NewsCard("Assassin's Creed Shadows Hits 5 Million Players", "https://placehold.co/300x170/c23616/ffffff?text=News+5").getView(), 1, 1);
        gridPane.add(new NewsCard("Cyberpunk 2077 added to PS Plus Catalog", "https://placehold.co/300x170/fbc531/000000?text=News+6").getView(), 2, 1);

        return gridPane;
    }

    public Pane getView() {
        return view;
    }
}