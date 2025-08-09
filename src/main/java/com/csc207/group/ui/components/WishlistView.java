//package ui.components;
//
//import javafx.scene.Node;
//import javafx.scene.control.Label;
//import javafx.scene.control.ScrollPane;
//import javafx.scene.layout.VBox;
//
//import java.util.List;
//
//public class WishlistView {
//
//    private final VBox layout;
//    private final ScrollPane scrollPane;
//
//    public WishlistView() {
//        layout = new VBox(10);
//        layout.setFillWidth(true);
//        layout.setPrefWidth(350); // adjust as needed
//
//        scrollPane = new ScrollPane(layout);
//        scrollPane.setFitToWidth(true);
//        scrollPane.setPrefHeight(500); // adjust based on your UI layout
//    }
//
//    public void updateEntries(List<GamePreviewCard> cards) {
//        layout.getChildren().clear();
//
//        if (cards == null || cards.isEmpty()) {
//            layout.getChildren().add(new Label("Your wishlist is empty."));
//            return;
//        }
//
//        for (GamePreviewCard card : cards) {
//            layout.getChildren().add(card);
//        }
//    }
//
//    public Node getView() {
//        return scrollPane;
//    }
//}

