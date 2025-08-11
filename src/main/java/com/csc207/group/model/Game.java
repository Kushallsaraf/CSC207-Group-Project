package com.csc207.group.model;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private String name;
    private List<String> genres;
    private List<String> developer;
    private double critic_rating;
    private double rating_count;
    private List<String> platforms;
    private String cover_image;
    private String age_rating;
    private String release_date;
    private List<Integer> DLCs;
    private List<Review> reviews;
    private int gameid;

    public Game(int gameid) {
        initializeDefaults();
        this.gameid = gameid;
    }

    public Game() {
        initializeDefaults();
    }

    /**
     * Ensures all fields have safe default values so null pointer issues are avoided.
     */
    private void initializeDefaults() {
        this.name = "";
        this.genres = new ArrayList<String>();
        this.developer = new ArrayList<String>();
        this.critic_rating = 0.0;
        this.rating_count = 0.0;
        this.platforms = new ArrayList<String>();
        this.cover_image = "";
        this.age_rating = "";
        this.release_date = "";
        this.DLCs = new ArrayList<Integer>();
        this.reviews = new ArrayList<Review>();
        this.gameid = 0;
    }

    public void setGameid(int gameid) {
        this.gameid = gameid;
    }

    public int getGameid() {
        return this.gameid;
    }

    public void setName(String name) {
        this.name = name != null ? name : "";
    }

    public void setGenres(List<String> genres) {
        this.genres = genres != null ? genres : new ArrayList<String>();
    }

    public void setDeveloper(List<String> developer) {
        this.developer = developer != null ? developer : new ArrayList<String>();
    }

    public void setCritic_rating(double critic_rating) {
        this.critic_rating = critic_rating;
    }

    public void setRating_count(double rating_count) {
        this.rating_count = rating_count;
    }

    public void setPlatforms(List<String> platforms) {
        this.platforms = platforms != null ? platforms : new ArrayList<String>();
    }

    public void setCover_image(String cover_image) {
        this.cover_image = cover_image != null ? cover_image : "";
    }

    public void setAge_rating(String age_rating) {
        this.age_rating = age_rating != null ? age_rating : "";
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date != null ? release_date : "";
    }

    public void setDLCs(List<Integer> DLCs) {
        this.DLCs = DLCs != null ? DLCs : new ArrayList<Integer>();
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews != null ? reviews : new ArrayList<Review>();
    }

    public void appendReview(Review review) {
        if (review != null) {
            reviews.add(review);
        }
    }

    public String getName() {
        return name;
    }

    public List<String> getGenres() {
        return genres;
    }

    public List<String> getDeveloper() {
        return developer;
    }

    @Override
    public String toString() {
        return "Game Details: " + name +
                ", Genres: " + genres +
                ", Developer: " + developer +
                ", Critic Rating: " + critic_rating +
                ", Rating Count: " + rating_count +
                ", Platforms: " + platforms +
                ", Cover Image: " + cover_image +
                ", Age Rating: " + age_rating +
                ", Release Date: " + release_date +
                ", DLCs: " + DLCs;
    }

    public double getCritic_rating() {
        return critic_rating;
    }

    public double getRating_count() {
        return rating_count;
    }

    public List<String> getPlatforms() {
        return platforms;
    }

    public String getCover_image() {
        return cover_image;
    }

    public String getAge_rating() {
        return age_rating;
    }

    public String getRelease_date() {
        return release_date;
    }

    public List<Integer> getDLCs() {
        return DLCs;
    }

    public List<Review> getReviews() {
        return reviews;
    }
}
