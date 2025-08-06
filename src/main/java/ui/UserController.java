package ui;
import data_access.FirebaseUserDataHandler;
import model.User;
import service.UserInteractor;

public class UserController {


    private final FirebaseUserDataHandler firebaseHandler;
    private User user;
    private UserInteractor interactor;

    public UserController(FirebaseUserDataHandler firebaseHandler) {
        this.firebaseHandler = firebaseHandler;
    }

    // Load user from Firebase (login)
    public boolean loadUser(String username) {
        User loaded = firebaseHandler.getUser(username);
        if (loaded != null) {
            this.user = loaded;
            this.interactor = new UserInteractor(user, firebaseHandler);
            return true;
        }
        return false;
    }



    // Add game to wishlist
    public boolean addToWishlist(int gameId) {
        boolean added = interactor.addToWishlist(gameId);
        save();
        return added;
    }

    // Remove game from wishlist
    public boolean removeFromWishlist(int gameId) {
        boolean removed = interactor.removeFromWishlist(gameId);
        save();
        return removed;
    }

    // Add game to library
    public boolean addToLibrary(int gameId) {
        boolean added = interactor.addToLibrary(gameId);
        save();
        return added;
    }

    // Leave or update a review
    public void reviewGame(int gameId, String content, double rating) {
        interactor.leaveOrUpdateReview(gameId, content, rating);
        save();
    }

    // Remove review
    public boolean deleteReview(int gameId) {
        boolean removed = interactor.removeReview(gameId);
        save();
        return removed;
    }

    // Save user data to Firebase
    private void save() {
        // You can enhance this with try-catch if Firebase write is risky
        firebaseHandler.saveUser(user);  // You need to implement this method
    }

    // Get current user object (read-only)
    public User getUser() {
        return user;
    }


}
