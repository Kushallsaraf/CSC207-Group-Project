package com.csc207.group.View;

import components.GameCard;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class HomeView {

    private VBox view;
    private EventHandler<MouseEvent> cardClickHandler;
    private Label userLabel;  // New label to show the username

    public HomeView(EventHandler<MouseEvent> cardClickHandler) {
        this.cardClickHandler = cardClickHandler;

        view = new VBox(20);
        view.setStyle("-fx-padding: 20; -fx-background-color: #222;");
        view.setAlignment(Pos.TOP_CENTER);

        Label welcomeLabel = new Label("WELCOME TO GAME CENTRAL!");
        welcomeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label subLabel = new Label("Start searching below to find new games.");
        subLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #ccc;");

        userLabel = new Label();  // Initially empty
        userLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #00ffff;");

        Label rpgHeader = new Label("Here are some of the Best Recent RPGs");
        rpgHeader.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");

        GridPane gameGrid = createGameGrid();

        view.getChildren().addAll(welcomeLabel, userLabel, subLabel, rpgHeader, gameGrid);
    }

    private GridPane createGameGrid() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.setAlignment(Pos.CENTER);

        gridPane.add(new GameCard("Clair Obscur: Expedition 33", "Sandfall Interactive", "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcSrUsfBxfPMYJN_au0TpmDpM-B7UKz0y-g4orgNCpcbjuddHyitTPb5BMPaYnbIxv6p_Tm0", cardClickHandler).getView(), 0, 0);
        gridPane.add(new GameCard("The Witcher III", "CD Projekt Red", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTgOPtH8lO6v8aRGGNpdEpaJgtR5GEO1UlnPv33E4-9hyPDQHa7", cardClickHandler).getView(), 1, 0);
        gridPane.add(new GameCard("Skyrim", "Bethesda", "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcTlOmOTidO6ZLe8s4dhVR9f1G8-fKT5RpQrBr5rprMr9PrzLba9", cardClickHandler).getView(), 2, 0);
        gridPane.add(new GameCard("CyberPunk 2077", "CD Projekt Red", "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcSM0vv4cTvXHOMfYfmXSTPaasKG5HoqiDj4hlDizehQgBSBqzYX", cardClickHandler).getView(), 3, 0);
        gridPane.add(new GameCard("Elden Ring", "FromSoftware", "https://m.media-amazon.com/images/M/MV5BZGQxMjYyOTUtNjYyMC00NzdmLWI4YmYtMDhiODU3Njc5ZDJkXkEyXkFqcGc@._V1_QL75_UX190_CR0,2,190,281_.jpg", cardClickHandler).getView(), 4, 0);
        gridPane.add(new GameCard("Baldur's Gate 3", "Larian Studios", "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcRmSeTi7xVt1FOA_NaILhSLFVvEI_VSCcGY1A6jPeI1H8ReA9E3Sq4kV18_qSAnSKkxs2lV", cardClickHandler).getView(), 5, 0);

        return gridPane;
    }

    public void setUsername(String username) {
        userLabel.setText("Logged in as: " + username);
    }

    public Pane getView() {
        return view;
    }
}
