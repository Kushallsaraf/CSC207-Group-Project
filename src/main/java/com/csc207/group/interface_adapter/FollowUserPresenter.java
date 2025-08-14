package com.csc207.group.interface_adapter;

public class FollowUserPresenter implements FollowUserOutputBoundary {
    private final FollowUserViewModel viewModel = new FollowUserViewModel();

    @Override
    public void present(FollowUserResponseModel response) {
        // Always pass through updated counts
        viewModel.setFollowersCount(response.getFollowersCount());
        viewModel.setFollowingCount(response.getFollowingCount());

        // Button label policy: show "Unfollow" iff user is following; otherwise "Follow"
        String code = response.getStatusCode();
        String buttonLabel;

        if ("OK".equals(code)) {
            // Follow succeeded → now following
            buttonLabel = "Unfollow";
        } else if ("ALREADY".equals(code)) {
            // Already following
            buttonLabel = "Unfollow";
        } else if ("SELF".equals(code)) {
            buttonLabel = "Follow";
        } else if ("NOT_FOUND".equals(code)) {
            buttonLabel = "Follow";
        } else {
            // "ERROR" or any other unexpected code
            buttonLabel = "Follow";
        }

        viewModel.setButtonLabel(buttonLabel);
    }

    public FollowUserViewModel getViewModel() {
        return viewModel;
    }
}

