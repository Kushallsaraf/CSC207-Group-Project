import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class ReviewCard {
    private VBox card;
    public VBox ReviewCard(Review review) {
        card = new VBox();
        card.setPrefSize(1600,300);
        card.setAlignment(Pos.BASELINE_LEFT);

        Label user = new Label(review.getReviewAuth());
        Label areview = new Label(review.getReview());
        user.setFont(Font.font("Bahnschrift", FontWeight.BOLD, 14));
        areview.setFont(Font.font("Bahnschrift", FontWeight.NORMAL, 12));
        user.setStyle("-fx-text-fill: #666;");
        areview.setStyle("-fx-text-fill: #666;");
        card.getChildren().addAll(user,areview);
        card.setStyle("-fx-background-color: #222; -fx-border-color: Black; -fx-border-width: 6; -fx-border-radius: 20; -fx-background-radius: 20");
        return card;
    }
}
