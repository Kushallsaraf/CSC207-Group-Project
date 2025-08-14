package com.csc207.group.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class User {

    private String username;
    private String hashedPassword;
    private List<Integer> wishlist;
    private Map<Integer, Review> reviews;
    private List<Integer> library;
    private String bio;
    private String profilePictureUrl;
    private List<String> followers;
    private List<String> following;

    public User(String username, String hashedPassword) {
        this.hashedPassword = hashedPassword;
        this.username = username;
        this.reviews = new HashMap<>();
        this.wishlist = new ArrayList<>();
        this.library = new ArrayList<>();
        this.bio = "";
        this.profilePictureUrl = null;
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();

    }

    public String getUsername() {
        return username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public Map<Integer, Review> getReviews() {
        return this.reviews;
        
    }

    /**
     * Get all games.
     * @return all games.
     */
    public List<Integer> getAllGames() {
        List<Integer> gameIds = new ArrayList<>();
        for (Review review : this.getReviews().values()) {
            gameIds.add(review.getGameId());
        }
        return gameIds;
    }

    public List<Integer> getWishlist() {
        return this.wishlist;
    }

    public List<Integer> getLibrary() {
        return library;
    }

    public void setBio(String content) {
        this.bio = content;
    }

    public String getBio() {
        return this.bio;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getProfilePictureUrl() {
        return this.profilePictureUrl;
    }

    /**
     * User follows another user.
     * @param usernameOfOtherUser Username of the other user this user wants to follow.
     */
    public void follow(String usernameOfOtherUser) {
        if (!isFollowing(usernameOfOtherUser)) {
            this.following.add(usernameOfOtherUser);
        }
    }

    /**
     * Unfollows a user.
     * @param usernameOfOtherUser username of the user that is unfollowed.
     */
    public void unfollow(String usernameOfOtherUser) {
        if (isFollowing(usernameOfOtherUser)) {
            this.following.remove(usernameOfOtherUser);
        }

    }

    /**
     * Checks if a user is following another user.
     * @param usernameOfOtherUser username of the other user that is being checked.
     * @return whether the user follows the other user.
     */
    public boolean isFollowing(String usernameOfOtherUser) {
        return this.following.contains(usernameOfOtherUser);
    }

    /**
     * Adds a username to followers.
     * @param usernameOfOtherUser Username that is added to followers.
     */
    public void addToFollowers(String usernameOfOtherUser) {
        if (!followers.contains(usernameOfOtherUser)) {
            followers.add(usernameOfOtherUser);
        }
    }

    /**
     * Removes username from followers.
     * @param usernameOfOtherUser username of user to remove from followers.
     */
    public void removeFromFollowers(String usernameOfOtherUser) {
        followers.remove(usernameOfOtherUser);
    }

    public void setFollowers(List<String> followers) {
        this.followers = followers;
    }

    public void setFollowing(List<String> following) {
        this.following = following;
    }

    public List<String> getFollowers() {
        return this.followers;
    }

    public List<String> getFollowing() {
        return this.following;
    }

    public int getNumberOfFollowers() {
        return this.followers.size();
    }

    public int getNumberOfFollowing() {
        return this.following.size();
    }

}
