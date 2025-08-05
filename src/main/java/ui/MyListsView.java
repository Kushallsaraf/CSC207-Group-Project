package ui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MyListsView {

    private VBox view;

    public MyListsView() {
        view = new VBox(30);
        view.setStyle("-fx-padding: 20; -fx-background-color: #222;");
        view.setAlignment(Pos.TOP_CENTER);

        // Header
        Label headerLabel = new Label("My Lists");
        headerLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: white;");

        // Description
        Label subHeaderLabel = new Label("Organize your games into custom lists. Share your favorites, track your backlog, or create a wishlist.");
        subHeaderLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #ccc;");

        // Placeholder for where list cards will go
        VBox listCardContainer = new VBox(20);
        listCardContainer.setAlignment(Pos.CENTER);
        listCardContainer.setStyle("-fx-padding: 50 0;"); // Add some vertical space
        Label placeholder = new Label("Your game lists will appear here.");
        placeholder.setStyle("-fx-font-size: 18px; -fx-text-fill: #888;");
        Text comingSoon = new Text("Feature coming soon!");
        comingSoon.setStyle("-fx-fill: #666;");

        listCardContainer.getChildren().addAll(placeholder, comingSoon);

        view.getChildren().addAll(headerLabel, subHeaderLabel, listCardContainer);
    }

    public Pane getView() {
        return view;
    }
}