public class Review {
    String username;
    String review;
    Double rating;
    public Review(String username, String reviews,  Double rating) {
        this.username = username;
        this.review = reviews;
        this.rating = rating;
    }
    public String getReviewAuth() {
        return this.username;
    }
    public String getReview() {
        return this.review;
    }
    public Double getRating() {
        return this.rating;
    }
}
