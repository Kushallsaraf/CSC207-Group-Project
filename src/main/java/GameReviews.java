import java.util.List;

public class GameReviews {
    private List<Review> reviews;

    private  List<Review> getReviews() {
        return reviews;
    }
    private void addReview(Review review){
        this.reviews.add(review);
    }
}
