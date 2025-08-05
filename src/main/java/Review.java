public class Review {
    String username;
    String review;
    public Review(String username, String reviews) {
        this.username = username;
        this.review = reviews;
    }
    public String getReviewAuth() {
        return this.username;
    }
    public String getReview() {
        return this.review;
    }
}
