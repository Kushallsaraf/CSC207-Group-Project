//package ui.components;
//
//import javafx.geometry.Insets;
//import javafx.scene.Scene;
//import javafx.scene.control.Label;
//import javafx.scene.layout.VBox;
//import javafx.stage.Modality;
//import javafx.stage.Stage;
//import model.Review;
//
//public class ReviewView {
//    public static void showReviewPopup(Review review) {
//        Stage popup = new Stage();
//        popup.initModality(Modality.APPLICATION_MODAL);
//        popup.setTitle("Your Review");
//
//        Label ratingLabel = new Label("Rating: " + review.getRating() + " stars");
//        Label contentLabel = new Label(review.getContent());
//        contentLabel.setWrapText(true);
//
//        VBox layout = new VBox(10, ratingLabel, contentLabel);
//        layout.setPadding(new Insets(15));
//        layout.setStyle("-fx-background-color: #ffffff;");
//
//        Scene scene = new Scene(layout, 300, 150);
//        popup.setScene(scene);
//        popup.showAndWait();
//    }
//}

