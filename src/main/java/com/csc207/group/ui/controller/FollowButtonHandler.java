package com.csc207.group.ui.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class FollowButtonHandler implements EventHandler<ActionEvent> {


    private final UserProfileController userProfileController;

    public FollowButtonHandler(UserProfileController userProfileController){
        this.userProfileController = userProfileController;
    }

    @Override
    public void handle(ActionEvent event) {
        Button b = (Button) event.getSource();
        this.userProfileController.handleFollowButtonClick(b.getText());
    }
}


