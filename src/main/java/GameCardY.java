import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GameCardY {
    private VBox card;
    public GameCardY(String name) {
        card = new VBox(10);
        Image img =  new Image("https://www.psu.com/wp/wp-content/uploads/2020/09/The-Witcher-3-Wild-Hunt-PS4-Wallpaper-1920x1080_29.png",true);
        ImageView frame = new ImageView(img);
        frame.setFitHeight(200);
        frame.setFitWidth(150);
        frame.setPreserveRatio(true);

        Label title = new Label("Witcher 3");
        Label sum = new Label(name);
        title.setFont(Font.font("Bahnschrift", FontWeight.BOLD, 18));
        sum.setFont(Font.font("Bahnschrift", FontWeight.NORMAL, 12));
        title.setStyle("-fx-text-fill: #666");
        sum.setStyle("-fx-text-fill: #666");
        card.getChildren().addAll(frame,title,sum);
        card.setAlignment(Pos.TOP_CENTER);
        card.setStyle("-fx-background-color: #222; -fx-border-color: Black; -fx-border-width: 6; -fx-border-radius: 20; -fx-background-radius: 20");
        card.setPrefSize(150,250);
    }
    public VBox getCard() {
        return card;
    }
}
