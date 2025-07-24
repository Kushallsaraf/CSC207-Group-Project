package com.csc207.group.model;

import java.util.List;

public class Game {
    private String name;
    private List<String> genres;
    private String developer;

    public Game(String name, List<String> genres, String developer) {
        this.name = name;
        this.genres = genres;
        this.developer = developer;
    }

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
        return "Game Details: " + name + ", Genres: " + genres + ", Developer: " + developer;
    }
}