package com.csc207.group.model;

import javafx.scene.image.Image;

/**
 * The model for what we want to store in a user's library which does not require
 * every aspect of a Game.
 */
public final class LibraryEntry extends GamePreview {
    private Review userReview;

    // The constructor now includes a 'description' parameter
    public LibraryEntry(String title, int year, Image coverImage, int gameid, String description) {
        // The description is passed to the parent 'GamePreview' constructor
        super(title, year, coverImage, gameid, description);
    }

    public void setUserReview(Review review) {
        this.userReview = review;
    }

    public Review getUserReview() {
        return this.userReview;
    }

    /**
     * Edits user review.
     * @param content Content of the review
     * @param starRating The star rating of the review
     */
    public void editUserReview(String content, double starRating) {
        this.userReview.editReview(content, starRating);
    }
}
