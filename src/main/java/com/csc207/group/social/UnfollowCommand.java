package com.csc207.group.social;

import com.csc207.group.interface_adapter.UserInteractorInputBoundary;
import com.csc207.group.model.User;
import com.csc207.group.service.UserInteractor;

public class UnfollowCommand implements Command{
    private final UserInteractorInputBoundary interactor;
    private final String targetUsername;

    public UnfollowCommand(UserInteractorInputBoundary interactor, String targetUsername) {
        this.interactor = interactor;
        this.targetUsername = targetUsername;
    }

    @Override
    public void execute() {
        User loggedIn = interactor.getUser();
        User target = interactor.getUserByUsername(targetUsername);

        if (!loggedIn.isFollowing(targetUsername)) {
            return;
        }

        loggedIn.unfollow(targetUsername);
        target.removeFromFollowers(loggedIn.getUsername());

        interactor.saveUser(loggedIn);
        interactor.saveUser(target);

    }


}
