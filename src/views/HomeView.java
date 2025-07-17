package views;

import components.GameCard;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class HomeView {

    private VBox view;

    public HomeView() {
        view = new VBox(20);
        view.setStyle("-fx-padding: 20; -fx-background-color: #222;");
        view.setAlignment(Pos.TOP_CENTER);

        // Welcome Text
        Label welcomeLabel = new Label("WELCOME TO GAME CENTRAL!");
        welcomeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label subLabel = new Label("Start searching below to find new games.");
        subLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #ccc;");

        // Search Bar
        TextField searchField = new TextField();
        searchField.setPromptText("Search for games...");
        searchField.setMaxWidth(400);
        searchField.setStyle("-fx-font-size: 14px; -fx-padding: 8;");

        // "Best RPGs" Section
        Label rpgHeader = new Label("Here are some of the Best Recent RPGs");
        rpgHeader.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");

        // Game Grid
        GridPane gameGrid = createGameGrid();

        view.getChildren().addAll(welcomeLabel, subLabel, searchField, rpgHeader, gameGrid);
    }

    /**
     * Creates a grid of game cards.
     * @return GridPane populated with game cards.
     */
    private GridPane createGameGrid() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.setAlignment(Pos.CENTER);

        // Add game cards (placeholders)
        gridPane.add(new GameCard("Clair Obscur: Expedition 33", "Sandfall Interactive", "https://placehold.co/150x200/2a2a2a/ffffff?text=Clair+Obscur").getView(), 0, 0);
        gridPane.add(new GameCard("The Witcher III", "CD Projekt Red", "https://placehold.co/150x200/2a2a2a/ffffff?text=Witcher+III").getView(), 1, 0);
        gridPane.add(new GameCard("Skyrim", "Bethesda", "https://placehold.co/150x200/2a2a2a/ffffff?text=Skyrim").getView(), 2, 0);
        gridPane.add(new GameCard("CyberPunk 2077", "CD Projekt Red", "https://placehold.co/150x200/2a2a2a/ffffff?text=CyberPunk").getView(), 3, 0);
        gridPane.add(new GameCard("Elden Ring", "FromSoftware", "https://placehold.co/150x200/2a2a2a/ffffff?text=Elden+Ring").getView(), 4, 0);
        gridPane.add(new GameCard("Baldur's Gate 3", "Larian Studios", "https://placehold.co/150x200/2a2a2a/ffffff?text=Baldur's+Gate").getView(), 5, 0);

        return gridPane;
    }

    public Pane getView() {
        return view;
    }
}