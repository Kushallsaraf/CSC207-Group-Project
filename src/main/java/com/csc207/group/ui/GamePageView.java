package com.csc207.group.ui;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class GamePageView implements View {
    private final StackPane root;

    public GamePageView() {
        root = new StackPane();
        Label helloLabel = new Label("This is the gamepage");
        helloLabel.setStyle("-fx-font-size: 24px;");
        root.getChildren().add(helloLabel);
    }

    @Override
    public String getName() {
        return "Hello View";
    }

    @Override
    public Parent getView() {
        return root;
    }

    @Override
    public void onShow() {
        // No special action needed when shown, but could log or refresh content here.
    }

}
