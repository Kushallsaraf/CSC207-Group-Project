package com.csc207.group.ui.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class FollowButtonHandler implements EventHandler<ActionEvent> {


    private final UserProfileController userProfileController;

    public FollowButtonHandler(UserProfileController userProfileController){
        this.userProfileController = userProfileController;
    }

    @Override
    public void handle(ActionEvent event) {
        this.userProfileController.handleFollowButtonClick();
    }
}


