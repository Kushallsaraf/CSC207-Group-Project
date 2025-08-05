package service;

import model.Review;
import model.User;

public class UserInteractor {
    private final User user;

    public UserInteractor(User user) {
        this.user = user;
    }

    // 1. Add to wishlist
    public boolean addToWishlist(int gameId) {
        if (!user.getWishlist().contains(gameId)) {
            user.getWishlist().add(gameId);
            return true;
        }
        return false;
    }

    // 2. Remove from wishlist
    public boolean removeFromWishlist(int gameId) {
        return user.getWishlist().remove(Integer.valueOf(gameId));
    }

    // 3. Add to library
    public boolean addToLibrary(int gameId) {
        if (!user.getLibrary().contains(gameId)) {
            user.getLibrary().add(gameId);
            return true;
        }
        return false;
    }

    // 4. Leave or update a review
    public void voidMakeReview(int gameid, String content, double rating){
        Review review = new Review(user.getUsername(), content, gameid, rating);
        this.user.getReviews().put(gameid, review);

    }
    public void leaveOrUpdateReview(int gameId, String content, double rating) {
        Review review = user.getReviews().get(gameId);

        if (review != null) {
            review.editReview(content, rating);
        } else {
            review = new Review(user.getUsername(), content, gameId, rating);
            user.getReviews().put(gameId, review);
        }
    }

    // 5. Remove review
    public boolean removeReview(int gameId) {
        return user.getReviews().remove(gameId) != null;
    }

    // 6. Check if a game is reviewed
    public boolean hasReviewed(int gameId) {
        return user.getReviews().containsKey(gameId);
    }

    // 7. Check if a game is in wishlist
    public boolean isInWishlist(int gameId) {
        return user.getWishlist().contains(gameId);
    }

    // 8. Check if a game is in library
    public boolean isInLibrary(int gameId) {
        return user.getLibrary().contains(gameId);
    }
}
