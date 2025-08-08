package com.csc207.group.model;

public class DLC {
    private String name;
    private String description;
    private String cover_image;

    public DLC(String name, String description, String cover_image) {
        this.name = name;
        this.description = description;
        this.cover_image = cover_image;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getCover_image() {
        return cover_image;
    }
}
