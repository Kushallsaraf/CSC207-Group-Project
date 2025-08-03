package views;

import components.GameCard;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class DeveloperView {

    private VBox view;

    public DeveloperView(String developerName, EventHandler<MouseEvent> cardClickHandler) {
        view = new VBox(20);
        view.setStyle("-fx-padding: 20; -fx-background-color: #222;");
        view.setAlignment(Pos.TOP_CENTER);

        Label headerLabel = new Label("Games by " + developerName);
        headerLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        GridPane gameGrid = createGameGrid(developerName, cardClickHandler);

        view.getChildren().addAll(headerLabel, gameGrid);
    }

    private GridPane createGameGrid(String developerName, EventHandler<MouseEvent> cardClickHandler) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.setAlignment(Pos.CENTER);

        // Here you would typically fetch games by the developer from a database or API.
        // For this example, we'll hardcode a few games for a specific developer.
        if ("CD Projekt Red".equals(developerName)) {
            gridPane.add(new GameCard("The Witcher III", "CD Projekt Red", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTgOPtH8lO6v8aRGGNpdEpaJgtR5GEO1UlnPv33E4-9hyPDQHa7", cardClickHandler).getView(), 0, 0);
            gridPane.add(new GameCard("CyberPunk 2077", "CD Projekt Red", "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcSM0vv4cTvXHOMfYfmXSTPaasKG5HoqiDj4hlDizehQgBSBqzYX", cardClickHandler).getView(), 1, 0);
        }
        // You can add more developers here
        // else if ("Bethesda".equals(developerName)) { ... }


        return gridPane;
    }

    public Pane getView() {
        return view;
    }
}