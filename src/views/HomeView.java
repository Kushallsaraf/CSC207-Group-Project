package views;

import components.GameCard;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class HomeView {

    private VBox view;
    // Field to store the handler
    private EventHandler<MouseEvent> cardClickHandler;

    // Modified constructor to accept the handler
    public HomeView(EventHandler<MouseEvent> cardClickHandler) {
        this.cardClickHandler = cardClickHandler;

        view = new VBox(20);
        view.setStyle("-fx-padding: 20; -fx-background-color: #222;");
        view.setAlignment(Pos.TOP_CENTER);

        Label welcomeLabel = new Label("WELCOME TO GAME CENTRAL!");
        welcomeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label subLabel = new Label("Start searching below to find new games.");
        subLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #ccc;");

        TextField searchField = new TextField();
        searchField.setPromptText("Search for games...");
        searchField.setMaxWidth(400);
        searchField.setStyle("-fx-font-size: 14px; -fx-padding: 8;");

        Label rpgHeader = new Label("Here are some of the Best Recent RPGs");
        rpgHeader.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");

        // This method will now use the handler
        GridPane gameGrid = createGameGrid();

        view.getChildren().addAll(welcomeLabel, subLabel, searchField, rpgHeader, gameGrid);
    }

    private GridPane createGameGrid() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.setAlignment(Pos.CENTER);

        // Pass the cardClickHandler to each GameCard instance
        gridPane.add(new GameCard("Clair Obscur: Expedition 33", "Sandfall Interactive", "https://placehold.co/150x200/2a2a2a/ffffff?text=Clair+Obscur", cardClickHandler).getView(), 0, 0);
        gridPane.add(new GameCard("The Witcher III", "CD Projekt Red", "https://placehold.co/150x200/2a2a2a/ffffff?text=Witcher+III", cardClickHandler).getView(), 1, 0);
        gridPane.add(new GameCard("Skyrim", "Bethesda", "https://placehold.co/150x200/2a2a2a/ffffff?text=Skyrim", cardClickHandler).getView(), 2, 0);
        gridPane.add(new GameCard("CyberPunk 2077", "CD Projekt Red", "https://placehold.co/150x200/2a2a2a/ffffff?text=CyberPunk", cardClickHandler).getView(), 3, 0);
        gridPane.add(new GameCard("Elden Ring", "FromSoftware", "https://placehold.co/150x200/2a2a2a/ffffff?text=Elden+Ring", cardClickHandler).getView(), 4, 0);
        gridPane.add(new GameCard("Baldur's Gate 3", "Larian Studios", "https://placehold.co/150x200/2a2a2a/ffffff?text=Baldur's+Gate", cardClickHandler).getView(), 5, 0);

        return gridPane;
    }

    public Pane getView() {
        return view;
    }
}