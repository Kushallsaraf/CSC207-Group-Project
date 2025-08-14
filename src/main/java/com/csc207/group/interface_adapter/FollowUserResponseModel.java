package com.csc207.group.interface_adapter;

public class FollowUserResponseModel {

    private final boolean success;       // true if follow applied, false if failed
    private final String statusCode;     // "OK", "ALREADY", "SELF", "NOT_FOUND", "ERROR"
    private final String targetUsername;
    private final int followersCount;    // updated count for target
    private final int followingCount;    // updated count for current user

    public FollowUserResponseModel(boolean success,
                                   String statusCode,
                                   String targetUsername,
                                   int followersCount,
                                   int followingCount) {
        this.success = success;
        this.statusCode = statusCode;
        this.targetUsername = targetUsername;
        this.followersCount = followersCount;
        this.followingCount = followingCount;
    }

    public boolean isSuccess() { return success; }
    public String getStatusCode() { return statusCode; }
    public String getTargetUsername() { return targetUsername; }
    public int getFollowersCount() { return followersCount; }
    public int getFollowingCount() { return followingCount; }
}
