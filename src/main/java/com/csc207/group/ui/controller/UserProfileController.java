package com.csc207.group.ui.controller;

import com.csc207.group.model.User;
import com.csc207.group.service.UserInteractor;
import com.csc207.group.social.FollowCommand;
import com.csc207.group.social.UnfollowCommand;
import com.csc207.group.ui.UserProfileView;

public class UserProfileController {

    private final UserInteractor userInteractor;
    private final UserProfileView view;
    private final String targetUsername; //

    public UserProfileController(UserInteractor userInteractor, UserProfileView view, String targetUsername) {
        this.userInteractor = userInteractor;
        this.view = view;
        this.targetUsername = targetUsername;

        initialize();
    }

    private void initialize() {
        User targetUser = userInteractor.getUserByUsername(targetUsername);
        User loggedInUser = userInteractor.getUser();

        // Set up view with initial data
        view.setUsername(targetUser.getUsername());
        view.setBio(targetUser.getBio());
        view.setProfileImage(targetUser.getProfilePictureURL());
        view.setFollowersCount(targetUser.getFollowers().size());
        view.setFollowingCount(targetUser.getFollowing().size());

        // Decide initial follow button state
        if (loggedInUser.isFollowing(targetUsername)) {
            view.getFollowButton().setText("Unfollow");
        } else {
            view.getFollowButton().setText("Follow");
        }



        view.getFollowButton().setOnAction(new FollowButtonHandler(userInteractor, loggedInUser, targetUsername, view));
    }


}
