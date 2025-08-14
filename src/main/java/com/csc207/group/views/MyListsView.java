package views;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MyListsView {

    private static final int VIEW_SPACING = 30;
    private static final int LIST_CARD_SPACING = 20;
    private static final int LIST_CARD_PADDING_TOP_BOTTOM = 50;
    private static final String STYLE_VIEW = "-fx-padding: 20; -fx-background-color: #222;";
    private static final String STYLE_HEADER =
            "-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: white;";
    private static final String STYLE_SUBHEADER =
            "-fx-font-size: 16px; -fx-text-fill: #ccc;";
    private static final String STYLE_PLACEHOLDER =
            "-fx-font-size: 18px; -fx-text-fill: #888;";
    private static final String STYLE_COMING_SOON = "-fx-fill: #666;";

    private final VBox view;

    public MyListsView() {
        view = new VBox(VIEW_SPACING);
        view.setStyle(STYLE_VIEW);
        view.setAlignment(Pos.TOP_CENTER);

        Label headerLabel = new Label("My Lists");
        headerLabel.setStyle(STYLE_HEADER);

        Label subHeaderLabel = new Label(
                "Organize your games into custom lists. Share your favorites, "
                        + "track your backlog, or create a wishlist."
        );
        subHeaderLabel.setStyle(STYLE_SUBHEADER);

        VBox listCardContainer = new VBox(LIST_CARD_SPACING);
        listCardContainer.setAlignment(Pos.CENTER);
        listCardContainer.setStyle("-fx-padding: " + LIST_CARD_PADDING_TOP_BOTTOM + " 0;");

        Label placeholder = new Label("Your game lists will appear here.");
        placeholder.setStyle(STYLE_PLACEHOLDER);

        Text comingSoon = new Text("Feature coming soon!");
        comingSoon.setStyle(STYLE_COMING_SOON);

        listCardContainer.getChildren().addAll(placeholder, comingSoon);
        view.getChildren().addAll(headerLabel, subHeaderLabel, listCardContainer);
    }

    /**
     * Returns the root pane for this view.
     * Subclasses overriding this must ensure the returned Pane is non-null
     * and contains all necessary content for this view.
     *
     * @return the root VBox containing the view layout
     */
    public Pane getView() {
        return view;
    }
}
