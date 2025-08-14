package com.csc207.group.social;

import com.csc207.group.interface_adapter.FollowUserPresenter;
import com.csc207.group.interface_adapter.FollowUserRequestModel;
import com.csc207.group.interface_adapter.FollowUserViewModel;
import com.csc207.group.interface_adapter.UserInteractorInputBoundary;

/** Intended for future offline functionality for follower system
 *
 */
public class FollowCommand implements Command {
    private final UserInteractorInputBoundary interactor;   // use case API
    private final FollowUserRequestModel request;       // input data

    public FollowCommand(UserInteractorInputBoundary interactor,
                             FollowUserRequestModel request) {
        this.interactor = interactor;
        this.request = request;
    }

    @Override
    public void execute() {
        interactor.executeFollow(request);
    }
}
