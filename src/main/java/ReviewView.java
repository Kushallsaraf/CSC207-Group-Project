import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ReviewView {
    private VBox vbox;
    public ReviewView() {
        vbox = new VBox(10);
        vbox.setStyle("-fx-padding: 20; -fx-background-color: #222;");

        Label title = new Label("Game Reviews");
        title.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label subtitle = new Label("See what others have to say, and tell others what you think!");
        subtitle.setStyle("-fx-font-size: 16px; -fx-text-fill: #ccc;");
    }
}
