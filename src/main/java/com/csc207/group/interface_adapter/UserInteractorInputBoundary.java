package com.csc207.group.interface_adapter;

import com.csc207.group.model.User;

public interface UserInteractorInputBoundary {

    String getReviewerProfilePicture(String username);

    void executeFollow(FollowUserRequestModel followUserRequestModel);

    User getUserByUsername(String username);

    User getUser();

    boolean addToWishlist(int gameId);

    boolean removeFromWishlist(int gameId);

    boolean addToLibrary(int gameId);

    void voidMakeReview(int gameid, String content, double rating);

    void leaveOrUpdateReview(int gameId, String content, double rating);

    boolean removeReview(int gameId);

    boolean hasReviewed(int gameId);

    boolean isInWishlist(int gameId);

    boolean isInLibrary(int gameId);

    boolean removeFromLibrary(int gameid);

    void editBio(String updatedBio);

    void editProfilePicture(String updatedProfilePictureUrl);

    void saveUser(User user);
}

