package com.csc207.group.ui;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class GamePageView implements View {
    private final StackPane root;
    private final Label mainLabel;

    public GamePageView() {
        root = new StackPane();
        mainLabel = new Label("This is the gamepage");
        mainLabel.setStyle("-fx-font-size: 24px;");
        root.getChildren().add(mainLabel);
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

    public void setTitle(String name) {
        mainLabel.setText("this is the gamepage of " +name);


    }
}
