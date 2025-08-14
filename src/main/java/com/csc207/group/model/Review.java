package com.csc207.group.model;

public final class Review {
    private String userId;
    private String content;
    private int gameId;
    private double rating;

    public Review(String userId, String content, int gameId, double rating) {

        this.content = content;
        this.gameId = gameId;
        this.rating = rating;
        this.userId = userId;
    }

    /**
     * Editing a review.
     * @param newContent The new content of a review.
     * @param newRating The new rating of the review.
     */
    public void editReview(String newContent, double newRating) {
        this.content = newContent;
        this.rating = newRating;

    }

    public int getGameId() {
        return gameId;
    }

    public String getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    public double getRating() {
        return rating;
    }
}
