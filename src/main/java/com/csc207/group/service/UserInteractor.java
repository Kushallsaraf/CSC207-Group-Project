package com.csc207.group.service;

import com.csc207.group.auth.UserDataHandler;
import com.csc207.group.model.GamePreview;
import com.csc207.group.model.Review;
import com.csc207.group.model.User;

import java.util.List;

public class UserInteractor {
    private final User user;
    private final UserDataHandler dataHandler;

    public UserInteractor(User user, UserDataHandler dataHandler) {
        this.user = user;
        this.dataHandler = dataHandler;
    }

    public String getReviewerProfilePicture(String username){
        return dataHandler.getUser(username).getProfilePictureURL();

    }

    public User getUserByUsername(String username){
        return dataHandler.getUser(username);
    }
    public User getUser(){
        return this.user;
    }

    public boolean addToWishlist(int gameId) {
        if (!user.getWishlist().contains(gameId)) {
            user.getWishlist().add(gameId);
            dataHandler.saveUser(user);
            return true;
        }
        return false;
    }

    public boolean removeFromWishlist(int gameId) {

        user.getWishlist().remove(Integer.valueOf(gameId));
        dataHandler.saveUser(user);
        return true;
    }

    public boolean addToLibrary(int gameId) {
        if (!user.getLibrary().contains(gameId)) {
            user.getLibrary().add(gameId);
            dataHandler.saveUser(user);
            return true;
        }

        return false;
    }

    public void voidMakeReview(int gameid, String content, double rating){
        Review review = new Review(user.getUsername(), content, gameid, rating);
        this.user.getReviews().put(gameid, review);
        dataHandler.saveUser(user);

    }
    public void leaveOrUpdateReview(int gameId, String content, double rating) {
        Review review = user.getReviews().get(gameId);

        if (review != null) {
            review.editReview(content, rating);

        } else {
            review = new Review(user.getUsername(), content, gameId, rating);
            user.getReviews().put(gameId, review);
        }
        dataHandler.saveUser(user);
    }

    public boolean removeReview(int gameId) {

        Review removed =  user.getReviews().remove(gameId);
        dataHandler.saveUser(user);
        return removed != null;
    }

    public boolean hasReviewed(int gameId) {
        return user.getReviews().containsKey(gameId);
    }

    public boolean isInWishlist(int gameId) {
        return user.getWishlist().contains(gameId);
    }

    public boolean isInLibrary(int gameId) {
        return user.getLibrary().contains(gameId);
    }

    public boolean removeFromLibrary(int gameid) {

        user.getLibrary().remove(Integer.valueOf(gameid));
        dataHandler.saveUser(user);
        return user.getLibrary().contains(gameid);


    }

    public void editBio(String updatedBio) {
        this.user.setBio(updatedBio);
        dataHandler.saveUser(user);
    }

    public void editProfilePicture(String updatedProfilePictureUrl){
        this.user.setProfilePictureURL(updatedProfilePictureUrl);
        dataHandler.saveUser(user);

    }

    public void saveUser(User user){
        dataHandler.saveUser(user);
    }



}
