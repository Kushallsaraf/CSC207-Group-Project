package com.csc207.group.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Screenshot {

    @JsonProperty("id")
    private int imageID;

    @JsonProperty("image")
    private String imageURL;

    @JsonProperty("width")
    private int imageWidth;

    @JsonProperty("height")
    private int imageHeight;

    @JsonProperty("is_deleted")
    private boolean isVisible;

    public Screenshot() {}

    public Screenshot(int imageID, String imageURL, int imageWidth, int imageHeight, boolean isVisible) {

        this.imageID = imageID;
        this.imageURL = imageURL;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.isVisible = isVisible;

    }

    public int getImageID() { return imageID; }

    public int getImageWidth() { return imageWidth; }

    public int getImageHeight() { return imageHeight; }

    public String getImageURL() { return imageURL; }

    public boolean isVisible() { return isVisible; }








}
