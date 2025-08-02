import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

import static javafx.application.Application.launch;

public class WishList {
    public void start(Stage primaryStage) throws FileNotFoundException {
        VBox screen = new VBox(10);
        screen.setStyle("-fx-padding: 20; -fx-background-color: #222;");
//        Label wishlist = new Label("Wish List");
//        wishlist.setFont(Font.font("Bahnschrift", FontWeight.BOLD, 30));
//        wishlist.setTextFill(Color.WHITE);
//        wishlist.setStyle("-fx-background: transparent");
// Header
        Label headerLabel = new Label("My Lists");
        headerLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: white;");

        // Description
        Label subHeaderLabel = new Label("Organize your games into custom lists. Share your favorites, track your backlog, or create a wishlist.");
        subHeaderLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #ccc;");

        String[] arr = {"a","b","c","d","e","f","g","h","i","j","k","l"};
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        for (int i = 0; i < arr.length; i++){
            GameCardY card =  new GameCardY(arr[i]);
            VBox wishlistItem = card.getCard();
            grid.add(wishlistItem,i%5, i/5);
        }
        ScrollPane scroll = new ScrollPane(grid);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: #222");
        grid.setStyle("-fx-background-color: #222");
        scroll.setPadding(new Insets(20));

        screen.getChildren().addAll(headerLabel,subHeaderLabel,scroll);
        screen.setAlignment(Pos.TOP_CENTER);
//        screen.setStyle("-fx-background-color: #222");

        Scene scene = new Scene(screen,1280,720);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Wish List");
        primaryStage.show();
        }

    public static void main(String[] args) {
        launch(args);
    }
    }
