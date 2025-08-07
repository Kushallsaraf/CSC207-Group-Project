package com.csc207.group.model;

import java.util.List;

public class Game {
    private String name;
    private List<String> genres;
    private List <String> developer;
    private double critic_rating;
    private double rating_count;
    private List <String> platforms;
    private String cover_image;
    private String age_rating;
    private String release_date;
    private List <Integer> DLCs;
    private List <Review> reviews;


    public Game() {}

    public void setName(String name) {this.name = name;}

    public void setGenres(List<String> genres) {this.genres = genres;}

    public void setDeveloper(List<String> developer) {this.developer = developer;}

    public void setCritic_rating(double critic_rating) {
        this.critic_rating = critic_rating;
    }

    public void setRating_count(double rating_count) {this.rating_count = rating_count;}
    public void setPlatforms(List<String> platforms) {this.platforms = platforms;}
    public void setCover_image(String cover_image) {this.cover_image = cover_image;}
    public void setAge_rating(String age_rating) {this.age_rating = age_rating;}
    public void setRelease_date(String release_date) {this.release_date = release_date;}
    public void setDLCs(List <Integer> DLCs) {this.DLCs = DLCs;}


    public void appendReview(Review review) {
        reviews.add(review);
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
        return "Game Details: " + name + ", Genres: " + genres + ", Developer: " + developer + ", Critic Rating: " + critic_rating + ", Rating Count: " + rating_count + ", Platforms: " + platforms + ", Cover Image: " + cover_image + ", Age Rating: " + age_rating + ", Release Date: " + release_date + ", DLCs: " + DLCs;
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

    public List <Integer> getDLCs() {
        return DLCs;
    }

    public List <Review> getReviews() {
        return reviews;
    }
}