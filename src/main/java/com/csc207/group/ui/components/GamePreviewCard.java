//package ui.components;
//
//import javafx.geometry.Insets;
//import javafx.scene.control.Button;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.scene.text.Text;
//import model.GamePreview;
//
//public class GamePreviewCard extends HBox {
//
//    private final Button removeButton;
//    private GamePreview preview;
//
//    public GamePreviewCard(GamePreview preview) {
//        ImageView imageView = new ImageView(preview.getCoverImage());
//        imageView.setFitWidth(75);
//        imageView.setFitHeight(100);
//        imageView.setPreserveRatio(true);
//        this.preview = preview;
//
//        Text title = new Text(preview.getTitle() + " (" + preview.getYear() + ")");
//
//        this.removeButton = new Button("Remove from Wishlist");
//        this.removeButton.setUserData(preview.getGameid());
//
//        VBox infoBox = new VBox();
//        infoBox.setSpacing(5);
//        infoBox.getChildren().addAll(title, removeButton);
//
//        this.getChildren().addAll(imageView, infoBox);
//        this.setSpacing(10);
//        this.setPadding(new Insets(10));
//        this.setStyle("-fx-border-color: gray; -fx-background-color: #f0f0f0;");
//    }
//
//    public Button getRemoveButton() {
//        return this.removeButton;
//    }
//    public GamePreview getPreview(){
//        return this.preview;
//    }
//}

