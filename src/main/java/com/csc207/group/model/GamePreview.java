package com.csc207.group.model;

import javafx.scene.image.Image;

public class GamePreview {
    private final String title;
    private final int year;
    private final Image coverImage;
    private final int gameid;
    private final String description;
    // Added from the second version

    public GamePreview(String title, int year, Image coverImage, int gameid, String description) {
        this.title = title;
        this.year = year;
        this.coverImage = coverImage;
        this.gameid = gameid;
        this.description = description;
    }

    // --- Getters ---

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public Image getCoverImage() {
        return coverImage;
    }

    public int getGameid() {
        return gameid;
    }

    public String getDescription() {
        return description;
    }
}
