package ui;

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

        gridPane.add(new NewsCard("Clair Obscur Expedition 33 on Sale on PS Store", "https://image.api.playstation.com/vulcan/ap/rnd/202412/1011/7764db359824f321c21c26f3bc428cbc9e7b4827f09c4a0c.jpg").getView(), 0, 0);
        gridPane.add(new NewsCard("Donkey Kong Bananza Releases for Switch 2", "https://assets.nintendo.com/image/upload/ar_16:9,c_lpad,w_930/b_white/f_auto/q_auto/ncom/software/switch-2/70010000096809/7d96d2b6161401c27be0df3fe57ca2e06db1263c4323c566c9269eb270c75dc1").getView(), 1, 0);
        gridPane.add(new NewsCard("Gran Turismo 7 Gets a New Update July 23", "https://blog.playstation.com/tachyon/2028/07/71fa27157895c40201a6903e8afbc70bdc3485a4.png?resize=1088%2C612&crop_strategy=smart").getView(), 2, 0);
        gridPane.add(new NewsCard("New Pokemon Z+A Switch 2 Bundle Announced", "https://assets.nintendo.com/image/upload/ar_16:9,c_lpad,w_1240/b_white/f_auto/q_auto/ncom/My%20Nintendo%20Store/EN-US/Nintendo%20Switch%202/Hardware/122173-nintendo-switch-2-pokemon-legends-z-a-nintendo-switch-2-edition-bundle-2000x2000").getView(), 0, 1);
        gridPane.add(new NewsCard("Assassin's Creed Shadows Hits 5 Million Players", "https://cdn.wccftech.com/wp-content/uploads/2025/07/AC-Shadows-5-Million-HD-scaled.jpeg").getView(), 1, 1);
        gridPane.add(new NewsCard("Cyberpunk 2077 added to PS Plus Catalog", "https://www.cdprojekt.com/en/wp-content/uploads-en/2025/07/game-catalog-16x9-cp77-en-scaled.jpg").getView(), 2, 1);

        return gridPane;
    }

    public Pane getView() {
        return view;
    }
}