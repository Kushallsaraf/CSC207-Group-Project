package com.csc207.group.ui.controller;

import com.csc207.group.social.FollowCommand;
import com.csc207.group.social.UnfollowCommand;
import com.csc207.group.service.UserInteractor;
import com.csc207.group.model.User;
import com.csc207.group.ui.UserProfileView;
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


