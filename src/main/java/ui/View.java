package ui;

import javafx.scene.Parent;

public interface View {
    String getName();
    Parent getView();
    void onShow();
}
