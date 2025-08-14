package com.csc207.group.model;

import java.util.ArrayList;
import java.util.List;

public final class Game {
    private String name;
    private List<String> genres;
    private List<String> developer;
    private double criticRating;
    private double ratingCount;
    private List<String> platforms;
    private String coverImage;
    private String ageRating;
    private String releaseDate;
    private List<Integer> downloadableContent;
    private List<Review> reviews;
    private String description;
    // adding a new attribute

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
        this.criticRating = 0.0;
        this.ratingCount = 0.0;
        this.platforms = new ArrayList<String>();
        this.coverImage = "";
        this.ageRating = "";
        this.releaseDate = "";
        this.downloadableContent = new ArrayList<Integer>();
        this.reviews = new ArrayList<Review>();
        this.gameid = 0;
    }

    public void setGameid(int gameid) {
        this.gameid = gameid;
    }

    public int getGameid() {
        return this.gameid;
    }

    /**
     * Sets name for a game.
     * @param name the name.
     */
    public void setName(String name) {
        if (name != null) {
            this.name = name;
        }
        else {
            this.name = "";
        }
    }

    /**
     * Sets the genres for a game.
     * @param genres list of genres.
     */
    public void setGenres(List<String> genres) {
        if (genres != null) {
            this.genres = genres;
        }
        else {
            this.genres = new ArrayList<String>();
        }
    }

    /**
     * Sets the developer for a given game.
     * @param developer developer list
     */
    public void setDeveloper(List<String> developer) {
        if (developer != null) {
            this.developer = developer;
        }
        else {
            this.developer = new ArrayList<String>();
        }
    }

    public void setCriticRating(double criticRating) {
        this.criticRating = criticRating;
    }

    public void setRatingCount(double ratingCount) {
        this.ratingCount = ratingCount;
    }

    /**
     * Sets the platforms for a given game.
     * @param platforms the platforms.
     */
    public void setPlatforms(List<String> platforms) {
        if (platforms != null) {
            this.platforms = platforms;
        }
        else {
            this.platforms = new ArrayList<String>();
        }
    }

    public void setDescription(String description) {
        this.description = description;
    }
    // added a new setter for description

    /**
     * Sets cover image for a game.
     * @param coverImage The cover image.
     */
    public void setCoverImage(String coverImage) {
        if (coverImage != null) {
            this.coverImage = coverImage;
        }
        else {
            this.coverImage = "";
        }
    }

    /**
     * Sets age rating for a game.
     * @param ageRating the age rating.
     */
    public void setAgeRating(String ageRating) {
        if (ageRating != null) {
            this.ageRating = ageRating;
        }
        else {
            this.ageRating = "";
        }
    }

    /**
     * Sets release date.
     * @param releaseDate The release date.
     */
    public void setReleaseDate(String releaseDate) {
        if (releaseDate != null) {
            this.releaseDate = releaseDate;
        }
        else {
            this.releaseDate = "";
        }
    }

    /**
     * Sets Downloadable Content for a game.
     * @param downloadableContent The downloadable content.
     */
    public void setDownloadableContent(List<Integer> downloadableContent) {
        if (downloadableContent != null) {
            this.downloadableContent = downloadableContent;
        }
        else {
            this.downloadableContent = new ArrayList<Integer>();
        }
    }

    /**
     * Sets Reviews parameter.
     * @param reviews reviews list.
     */
    public void setReviews(List<Review> reviews) {
        if (reviews != null) {
            this.reviews = reviews;
        }
        else {
            this.reviews = new ArrayList<Review>();
        }
    }

    /**
     * Appends reviews to the review attribute.
     * @param review a review.
     */
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
        return "Game Details: " + name
                + ", Genres: " + genres
                + ", Developer: " + developer
                + ", Critic Rating: " + criticRating
                + ", Rating Count: " + ratingCount
                + ", Platforms: " + platforms
                + ", Cover Image: " + coverImage
                + ", Age Rating: " + ageRating
                + ", Release Date: " + releaseDate
                + ", DLCs: " + downloadableContent;
    }

    public double getCriticRating() {
        return criticRating;
    }

    public double getRatingCount() {
        return ratingCount;
    }

    public List<String> getPlatforms() {
        return platforms;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public String getAgeRating() {
        return ageRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public List<Integer> getDownloadableContent() {
        return downloadableContent;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public String getDescription() {
        return description;
    }
    // added a new getter for description

}
