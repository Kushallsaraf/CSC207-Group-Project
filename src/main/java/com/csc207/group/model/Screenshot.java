package com.csc207.group.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class Screenshot {

    @JsonProperty("id")
    private int imageID;

    @JsonProperty("image")
    private String imageUrl;

    @JsonProperty("width")
    private int imageWidth;

    @JsonProperty("height")
    private int imageHeight;

    @JsonProperty("is_deleted")
    private boolean isVisible;

    public Screenshot() {

    }

    public Screenshot(int imageID, String imageUrl, int imageWidth, int imageHeight, boolean isVisible) {

        this.imageID = imageID;
        this.imageUrl = imageUrl;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.isVisible = isVisible;

    }

    public int getImageID() {
        return imageID;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isVisible() {
        return isVisible;
    }

}
