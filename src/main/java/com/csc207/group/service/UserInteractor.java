package com.csc207.group.service;

import com.csc207.group.auth.UserRepository;
import com.csc207.group.interface_adapter.FollowUserOutputBoundary;
import com.csc207.group.interface_adapter.FollowUserRequestModel;
import com.csc207.group.interface_adapter.FollowUserResponseModel;
import com.csc207.group.interface_adapter.UserInteractorInputBoundary;
import com.csc207.group.model.Review;
import com.csc207.group.model.User;

public class UserInteractor implements UserInteractorInputBoundary {
    private final User user;
    private final UserRepository userRepository;
    private final FollowUserOutputBoundary output;

    public UserInteractor(User user, UserRepository userRepository, FollowUserOutputBoundary output) {
        this.user = user;
        this.userRepository = userRepository;
        this.output = output;
    }

    public String getReviewerProfilePicture(String username){
        return userRepository.getUser(username).getProfilePictureURL();

    }

    public void executeFollow(FollowUserRequestModel followUserRequestModel){

        if (!this.user.isFollowing(followUserRequestModel.getTargetUsername())){//check if user is already following

        this.user.follow(followUserRequestModel.getTargetUsername());
        User targetUser = userRepository.getUser(followUserRequestModel.getTargetUsername());
        targetUser.addToFollowers(user.getUsername());
        userRepository.saveUser(user); //update user info on database
        userRepository.saveUser(targetUser);
        output.present(new FollowUserResponseModel(true, "OK",
                followUserRequestModel.getTargetUsername(), targetUser.getNumberOfFollowers(),
                targetUser.getNumberOfFollowing()));


        }

    }

    public User getUserByUsername(String username){
        return userRepository.getUser(username);
    }
    public User getUser(){
        return this.user;
    }

    public boolean addToWishlist(int gameId) {
        if (!user.getWishlist().contains(gameId)) {
            user.getWishlist().add(gameId);
            userRepository.saveUser(user);
            return true;
        }
        return false;
    }

    public boolean removeFromWishlist(int gameId) {

        user.getWishlist().remove(Integer.valueOf(gameId));
        userRepository.saveUser(user);
        return true;
    }

    public boolean addToLibrary(int gameId) {
        if (!user.getLibrary().contains(gameId)) {
            user.getLibrary().add(gameId);
            userRepository.saveUser(user);
            return true;
        }

        return false;
    }

    public void voidMakeReview(int gameid, String content, double rating){
        Review review = new Review(user.getUsername(), content, gameid, rating);
        this.user.getReviews().put(gameid, review);
        userRepository.saveUser(user);

    }
    public void leaveOrUpdateReview(int gameId, String content, double rating) {
        if (hasReviewed(gameId)){
            user.getReviews().get(gameId).editReview(content, rating);
        }
        Review review = user.getReviews().get(gameId);

        if (review != null) {
            review.editReview(content, rating);

        } else {
            review = new Review(user.getUsername(), content, gameId, rating);
            user.getReviews().put(gameId, review);
        }
        userRepository.saveUser(user);
    }

    public boolean removeReview(int gameId) {

        Review removed =  user.getReviews().remove(gameId);
        userRepository.saveUser(user);
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
        userRepository.saveUser(user);
        return user.getLibrary().contains(gameid);


    }

    public void editBio(String updatedBio) {
        this.user.setBio(updatedBio);
        userRepository.saveUser(user);
    }

    public void editProfilePicture(String updatedProfilePictureUrl){
        this.user.setProfilePictureURL(updatedProfilePictureUrl);
        userRepository.saveUser(user);

    }

    public void saveUser(User user){
        userRepository.saveUser(user);
    }

}
