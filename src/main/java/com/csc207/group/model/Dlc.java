package com.csc207.group.model;

public final class Dlc {
    private String name;
    private String description;
    private String coverImage;

    public Dlc(String name, String description, String coverImage) {
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
