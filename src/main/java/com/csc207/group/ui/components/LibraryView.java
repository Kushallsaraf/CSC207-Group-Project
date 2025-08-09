//package ui.components;
//
//import javafx.scene.Node;
//import javafx.scene.control.Label;
//import javafx.scene.control.ScrollPane;
//import javafx.scene.layout.VBox;
//
//import java.util.List;
//
//public class LibraryView {
//
//    private final VBox layout;
//    private final ScrollPane scrollPane;
//
//    public LibraryView() {
//        layout = new VBox(10);
//        layout.setFillWidth(true);
//        layout.setPrefWidth(350); // Adjust as needed
//
//        scrollPane = new ScrollPane(layout);
//        scrollPane.setFitToWidth(true);
//        scrollPane.setPrefHeight(500); // Adjust height based on your layout
//    }
//
//    public void updateEntries(List<LibraryEntryCard> cards) {
//        layout.getChildren().clear();
//
//        if (cards == null || cards.isEmpty()) {
//            layout.getChildren().add(new Label("Your library is empty."));
//            return;
//        }
//
//        for (LibraryEntryCard card : cards) {
//            layout.getChildren().add(card);
//        }
//    }
//
//    public Node getView() {
//        return scrollPane;
//    }
//}
//

