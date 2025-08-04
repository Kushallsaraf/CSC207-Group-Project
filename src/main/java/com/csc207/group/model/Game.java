package com.csc207.group.model;

import java.util.List;

public class Game {
    private String name;
    private List<String> genres;
    private String developer;
    private double critic_rating;
    private double rating_count;

//    public Game(String name, List<String> genres, String developer, double critic_rating, double rating_count) {
//        this.name = name;
//        this.genres = genres;
//        this.developer = developer;
//        this.critic_rating = critic_rating;
//        this.rating_count = rating_count;
//    }

    public Game() {}

    public void setName(String name) {this.name = name;}

    public void setGenres(List<String> genres) {this.genres = genres;}

    public void setDeveloper(String developer) {this.developer = developer;}

    public void setCritic_rating(double critic_rating) {
        this.critic_rating = critic_rating;
    }

    public void setRating_count(double rating_count) {this.rating_count = rating_count;}


    public String getName() {
        return name;
    }

    public List<String> getGenres() {
        return genres;
    }

    public String getDeveloper() {
        return developer;
    }

    @Override
    public String toString() {
        return "Game Details: " + name + ", Genres: " + genres + ", Developer: " + developer + ", Critic Rating: " + critic_rating + ", Rating Count: " + rating_count;
    }

    public double getCritic_rating() {
        return critic_rating;
    }

    public double getRating_count() {
        return rating_count;
    }
}