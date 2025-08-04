public class Review {
    private String username;
    private String review;
    public Review(String username, String reviews) {
        this.username = username;
        this.review = reviews;
    }
    public String[] getReview() {
        String[] reviews = {this.username,this.review};
        return reviews;
    }
}
