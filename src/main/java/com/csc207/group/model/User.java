package com.csc207.group.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {

    private String username;
    private String hashedPassword;
    private List<Integer> wishlist;
    private Map<Integer, Review> reviews;
    private List<Integer> library;
    private String bio;
    private String profilePictureURL;
    private List<String> followers;
    private List<String> following;


    public User(String username, String hashedPassword){
        this.hashedPassword = hashedPassword;
        this.username = username;
        this.reviews = new HashMap<>();
        this.wishlist = new ArrayList<>();
        this.library = new ArrayList<>();
        this.bio = "";
        this.profilePictureURL = null;
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

    public List<Integer> getAllGames(){
        List<Integer> gameIds = new ArrayList<>();
        for (Review review : this.getReviews().values()) {
            gameIds.add(review.getGameid());
        }
        return gameIds;
    }

    public List<Integer> getWishlist() {
        return this.wishlist;
    }


    public List<Integer> getLibrary() {
        return library;
    }

    public void setBio(String content){
        this.bio = content;
    }

    public String getBio(){
        return this.bio;
    }


    public void setProfilePictureURL(String profilePictureURL) {
        this.profilePictureURL = profilePictureURL;
    }

    public String getProfilePictureURL(){
        return this.profilePictureURL;
    }

    public void follow(String username){
        if (!isFollowing(username)){
        this.following.add(username);}
    }

    public void unfollow(String username){
        if (isFollowing(username)){
        this.following.remove(username);}

    }

    public boolean isFollowing(String username){
        return this.following.contains(username);
    }

    public void addToFollowers(String username) {
        if (!followers.contains(username)) {
            followers.add(username);
        }
    }

    public void removeFromFollowers(String username) {
        followers.remove(username);
    }

    public void setFollowers(List<String> followers){
        this.followers = followers;
    }

    public void setFollowing(List<String> following){
        this.following = following;
    }

    public List<String> getFollowers(){
        return this.followers;
    }

    public List<String> getFollowing(){
        return this.following;
    }

    public int getNumberOfFollowers(){
        return this.followers.size();
    }

    public int getNumberOfFollowing(){
        return this.following.size();
    }


}
