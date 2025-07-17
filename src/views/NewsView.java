package views;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class NewsView {
    private StackPane view;

    public NewsView() {
        view = new StackPane();
        view.setAlignment(Pos.CENTER);
        view.setStyle("-fx-background-color: #222;");
        Label content = new Label("News Page - Content coming soon!");
        content.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");
        view.getChildren().add(content);
    }

    public Pane getView() {
        return view;
    }

}
