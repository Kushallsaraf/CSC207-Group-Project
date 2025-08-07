package model;

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


    public User(String username, String hashedPassword){
        this.hashedPassword = hashedPassword;
        this.username = username;
        this.reviews = new HashMap<>();
        this.wishlist = new ArrayList<>();
        this.library = new ArrayList<>();
        this.bio = "";
        this.profilePictureURL = null;


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
}
