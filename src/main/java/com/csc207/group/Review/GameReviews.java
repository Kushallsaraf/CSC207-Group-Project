package com.csc207.group.Review;

import java.util.ArrayList;
import java.util.List;

public class GameReviews {
    public List<Review> reviews;

    public GameReviews() {
        this.reviews = new ArrayList<>();
    }
    public   List<Review> getReviews() {
        return reviews;
    }
    public void addReview(Review review){
        this.reviews.add(review);
    }
}
