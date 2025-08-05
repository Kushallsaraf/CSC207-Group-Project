package com.csc207.group.model;

public class GamePreview {
    private String name;
    private String description;
    private String coverImage;

    public GamePreview(String name, String description, String coverImage) {
        this.name = name;
        this.description = description;
        this.coverImage = coverImage;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getCoverImage() {
        return coverImage;
    }

}
