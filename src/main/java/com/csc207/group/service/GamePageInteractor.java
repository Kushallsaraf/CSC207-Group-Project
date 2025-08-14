package com.csc207.group.service;

import com.csc207.group.cache.FirebaseRestClient;
import com.csc207.group.data_access.FirebaseGameDataHandler;
import com.csc207.group.data_access.GameDataHandler;
import com.csc207.group.model.Game;
import com.csc207.group.model.Review;

public final class GamePageInteractor {
    private final UserInteractor userInteractor;
    private final GameDataHandler dataHandler;
    // to store user reviews under users

    public GamePageInteractor(UserInteractor userInteractor) {
        this.userInteractor = userInteractor;
        this.dataHandler = new FirebaseGameDataHandler(new FirebaseRestClient());
    }

    /**
     * Gets the profile url.
     * @param username the username of the user whos profile's url you require.
     * @return the url as a string.
     */
    public String getProfileUrl(String username) {
        return userInteractor.getReviewerProfilePicture(username);
    }

    /**
     * Saves a review.
     * @param rating Rating for the review.
     * @param content Content for the review.
     * @param gameid Gameid of the game being reviewed.
     * @param game The game object of the game being reviewed.
     */
    public void saveReview(double rating, String content, int gameid, Game game) {

        if (userInteractor.getUser().getAllGames().contains(gameid)) {
            System.out.println("Only 1 review allowed");
        }
        else {
            userInteractor.leaveOrUpdateReview(gameid, content, rating);
            Review review = new Review(userInteractor.getUser().getUsername(), content, gameid, rating);
            game.appendReview(review);
        }

        dataHandler.saveGameData(gameid, game);

    }
}
