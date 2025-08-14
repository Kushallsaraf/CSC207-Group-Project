package com.csc207.group.service;

import com.csc207.group.auth.UserDataHandler;
import com.csc207.group.model.Review;
import com.csc207.group.model.User;

public final class UserInteractor {
    private final User user;
    private final UserDataHandler dataHandler;

    public UserInteractor(User user, UserDataHandler dataHandler) {
        this.user = user;
        this.dataHandler = dataHandler;
    }

    public void followUser(String targetUsername) {

        if (!this.user.isFollowing(targetUsername)) {

            this.user.follow(targetUsername);
            User targetUser = dataHandler.getUser(targetUsername);
            targetUser.addToFollowers(user.getUsername());
            dataHandler.saveUser(user);
            dataHandler.saveUser(targetUser);

        }

    }

    /**
     * Gets reviewer profile picture.
     * @param username username of reviewer.
     * @return Returns the url of the user's profile picture.
     */
    public String getReviewerProfilePicture(String username) {
        return dataHandler.getUser(username).getProfilePictureUrl();

    }

    /**
     * Gets user by username.
     * @param username username of user.
     * @return User object.
     */
    public User getUserByUsername(String username) {
        return dataHandler.getUser(username);
    }

    public User getUser() {
        return this.user;
    }

    /**
     * Adds to wishlist.
     * @param gameId gameId of game to add to wishlist.
     * @return return whether game was added to wishlist.
     */
    public boolean addToWishlist(int gameId) {
        if (!user.getWishlist().contains(gameId)) {
            user.getWishlist().add(gameId);
            dataHandler.saveUser(user);
            return true;
        }
        return false;
    }

    /**
     * Removes game from wishlist.
     * @param gameId id of game that is being removed from wishlist.
     * @return whether game was removed from wishlist
     */
    public boolean removeFromWishlist(int gameId) {

        user.getWishlist().remove(Integer.valueOf(gameId));
        dataHandler.saveUser(user);
        return true;
    }

    /**
     * Adds game to library.
     * @param gameId id of the game.
     * @return whether game was added to library.
     */
    public boolean addToLibrary(int gameId) {
        if (!user.getLibrary().contains(gameId)) {
            user.getLibrary().add(gameId);
            dataHandler.saveUser(user);
            return true;
        }

        return false;
    }

    /**
     * Makes a review.
     * @param gameid id of the game to make a review.
     * @param content content of the review.
     * @param rating The rating in the review.
     */
    public void voidMakeReview(int gameid, String content, double rating) {
        Review review = new Review(user.getUsername(), content, gameid, rating);
        this.user.getReviews().put(gameid, review);
        dataHandler.saveUser(user);

    }

    /**
     * Leaves or updates review.
     * @param gameId id of game.
     * @param content content of review
     * @param rating rating of review
     */
    public void leaveOrUpdateReview(int gameId, String content, double rating) {
        if (hasReviewed(gameId)) {
            Review review = user.getReviews().get(gameId);
            review.editReview(content, rating);
        }
        Review review = user.getReviews().get(gameId);

        if (review != null) {
            review.editReview(content, rating);

        }
        else {
            review = new Review(user.getUsername(), content, gameId, rating);
            user.getReviews().put(gameId, review);
        }
        dataHandler.saveUser(user);
    }

    /**
     * Removes a review.
     * @param gameId game id.
     * @return if review was removed.
     */
    public boolean removeReview(int gameId) {

        Review removed = user.getReviews().remove(gameId);
        dataHandler.saveUser(user);
        return removed != null;
    }

    /**
     * Checks if a user has reviewed a game.
     * @param gameId id of game.
     * @return whether user has reviewed game with gameId.
     */
    public boolean hasReviewed(int gameId) {
        return user.getReviews().containsKey(gameId);
    }

    /**
     * Checks if a game is in users wishlist.
     * @param gameId id of game.
     * @return whether game is in users wishlist.
     */
    public boolean isInWishlist(int gameId) {
        return user.getWishlist().contains(gameId);
    }

    /**
     * Checks if game is in users library.
     * @param gameId game id.
     * @return whether game is in users library.
     */
    public boolean isInLibrary(int gameId) {
        return user.getLibrary().contains(gameId);
    }

    /**
     * Removes game from library.
     * @param gameid id of game.
     * @return returns if game is in users library.
     */
    public boolean removeFromLibrary(int gameid) {

        user.getLibrary().remove(Integer.valueOf(gameid));
        dataHandler.saveUser(user);
        return user.getLibrary().contains(gameid);
    }

    /**
     * Editing user bio.
     * @param updatedBio the new bio.
     */
    public void editBio(String updatedBio) {
        this.user.setBio(updatedBio);
        dataHandler.saveUser(user);
    }

    /**
     * Edits profile picture.
     * @param updatedProfilePictureUrl new profile picture url.
     */
    public void editProfilePicture(String updatedProfilePictureUrl) {
        this.user.setProfilePictureUrl(updatedProfilePictureUrl);
        dataHandler.saveUser(user);

    }

    /**
     * Saves user in data handler.
     * @param givenUser to save.
     */
    public void saveUser(User givenUser) {
        dataHandler.saveUser(givenUser);
    }

}
