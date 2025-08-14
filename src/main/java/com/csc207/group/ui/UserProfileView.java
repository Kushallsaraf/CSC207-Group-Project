package com.csc207.group.ui;

import javafx.scene.control.Button;

public class UserProfileView extends AbstractProfileView {

    private final Button followButton = new Button("Follow");

    public UserProfileView() {
        super();
        textInfo.getChildren().add(followButton);
    }

    public Button getFollowButton() {
        return followButton;
    }

    public void setFollowButtonText(String status) {
        this.followButton.setText(status);
    }
}
