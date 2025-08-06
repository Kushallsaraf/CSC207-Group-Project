package service;

import auth.UserDataHandler;
import model.Review;
import model.User;

public class UserInteractor {
    private final User user;
    private final UserDataHandler dataHandler;

    public UserInteractor(User user, auth.UserDataHandler dataHandler) {
        this.user = user;
        this.dataHandler = dataHandler;
    }

    // 1. Add to wishlist
    public boolean addToWishlist(int gameId) {
        if (!user.getWishlist().contains(gameId)) {
            user.getWishlist().add(gameId);
            dataHandler.saveUser(user);
            return true;
        }
        return false;
    }

    // 2. Remove from wishlist
    public boolean removeFromWishlist(int gameId) {

        user.getWishlist().remove(Integer.valueOf(gameId));
        dataHandler.saveUser(user);
        return true;
    }

    // 3. Add to library
    public boolean addToLibrary(int gameId) {
        if (!user.getLibrary().contains(gameId)) {
            user.getLibrary().add(gameId);
            dataHandler.saveUser(user);
            return true;
        }

        return false;
    }

    // 4. Leave or update a review
    public void voidMakeReview(int gameid, String content, double rating){
        Review review = new Review(user.getUsername(), content, gameid, rating);
        this.user.getReviews().put(gameid, review);
        dataHandler.saveUser(user);

    }
    public void leaveOrUpdateReview(int gameId, String content, double rating) {
        Review review = user.getReviews().get(gameId);

        if (review != null) {
            review.editReview(content, rating);
        } else {
            review = new Review(user.getUsername(), content, gameId, rating);
            user.getReviews().put(gameId, review);
        }
        dataHandler.saveUser(user);
    }

    // 5. Remove review
    public boolean removeReview(int gameId) {

        Review removed =  user.getReviews().remove(gameId);
        dataHandler.saveUser(user);
        return removed != null;
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

    public boolean removeFromLibrary(int gameid) {

        user.getLibrary().remove(Integer.valueOf(gameid));
        dataHandler.saveUser(user);
        return user.getLibrary().contains(gameid);


    }
}
