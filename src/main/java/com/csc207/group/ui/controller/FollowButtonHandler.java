package com.csc207.group.ui.controller;

import com.csc207.group.model.User;
import com.csc207.group.service.UserInteractor;
import com.csc207.group.social.FollowCommand;
import com.csc207.group.social.UnfollowCommand;
import com.csc207.group.ui.UserProfileView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class FollowButtonHandler implements EventHandler<ActionEvent> {

    private final UserInteractor userInteractor;
    private final User loggedInUser;
    private final String targetUsername;
    private final UserProfileView view;

    public FollowButtonHandler(UserInteractor userInteractor, User loggedInUser,
                               String targetUsername, UserProfileView view) {
        this.userInteractor = userInteractor;
        this.loggedInUser = loggedInUser;
        this.targetUsername = targetUsername;
        this.view = view;
    }

    @Override
    public void handle(ActionEvent event) {
        if (loggedInUser.isFollowing(targetUsername)) {
            new UnfollowCommand(userInteractor, targetUsername).execute();
            view.setFollowButtonText("Follow");
        }
        else {
            new FollowCommand(userInteractor, targetUsername).execute();
            view.setFollowButtonText("Unfollow");
        }

        // Refresh follower count
        User updatedTarget = userInteractor.getUserByUsername(targetUsername);
        view.setFollowersCount(updatedTarget.getFollowers().size());
    }
}
