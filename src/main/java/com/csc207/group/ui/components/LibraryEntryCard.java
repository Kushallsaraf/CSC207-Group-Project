//package ui.components;
//
//import javafx.geometry.Insets;
//import javafx.scene.control.Button;
//import javafx.scene.control.Hyperlink;
//import javafx.scene.layout.VBox;
//import model.LibraryEntry;
//import model.Review;
//
//public class LibraryEntryCard extends VBox {
//
//    private final Button removeButton;
//    private final Hyperlink viewReviewLink;
//
//    public LibraryEntryCard(LibraryEntry entry) {
//        GamePreviewCard baseCard = new GamePreviewCard(entry);
//
//        this.removeButton = baseCard.getRemoveButton(); // reuse the remove button
//        this.removeButton.setText("Remove from Library");
//        this.removeButton.setUserData(entry.getGameid());
//
//        this.getChildren().add(baseCard);
//
//        Review review = entry.getUserReview();
//        if (review != null) {
//            this.viewReviewLink = new Hyperlink("View Review");
//            this.viewReviewLink.setUserData(review); // controller can use this
//            this.getChildren().add(viewReviewLink);
//        } else {
//            this.viewReviewLink = null;
//        }
//
//        this.setSpacing(10);
//        this.setPadding(new Insets(10));
//        this.setStyle("-fx-border-color: lightgray; -fx-background-color: #f4f4f4;");
//    }
//
//    public Button getRemoveButton() {
//        return this.removeButton;
//    }
//
//    public Hyperlink getViewReviewLink() {
//        return this.viewReviewLink;
//    }
//}

