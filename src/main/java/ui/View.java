package ui;

import javafx.scene.Parent;

public interface View {
    String getName();           // e.g. "home", "login"
    Parent getView();           // JavaFX root node
    void onShow();
}
