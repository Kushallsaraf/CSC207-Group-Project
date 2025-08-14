package com.csc207.group.interface_adapter;

public class FollowUserRequestModel {
    private final String targetUsername;

    public FollowUserRequestModel(String targetUsername) {
        this.targetUsername = targetUsername;
    }

    public String getTargetUsername() {
        return targetUsername;
    }
}
