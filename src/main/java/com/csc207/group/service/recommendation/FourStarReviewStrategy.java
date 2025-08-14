package com.csc207.group.service.recommendation;

import com.csc207.group.model.GameRecommendation;
import com.csc207.group.model.Review;
import com.csc207.group.model.User;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class FourStarReviewStrategy implements RecommendationStrategy {
    private RecommendationService service;
    private List<Integer> fourStarGames;
    private User user;


    public FourStarReviewStrategy(RecommendationService service, User user){
        this.service = service;
        this.user = user;
        this.fourStarGames = getGamesByRatingRange(4.0, 5.0);

    }
    @Override
    public List<GameRecommendation> getRecommendations() {
        return service.getRecommendationsByIds(getTopFiveSimilarFromFourStars());

    }

    /**
     * returns a list of gameid's, the ids of recommendations based on 5 star reviews
     * @return
     */
    private List<Integer> getTopFiveSimilarFromFourStars() {
        Set<Integer> collected = new LinkedHashSet<>(); // maintains order, no duplicates

        for (Integer gameId : fourStarGames) {
            List<Integer> similar = service.getSimilarGameIds(gameId);
            for (Integer simId : similar) {
                if (collected.size() >= 10) {
                    return new ArrayList<>(collected);
                }
                collected.add(simId);
            }
        }

        return new ArrayList<>(collected); // May be < 5 if not enough similar games
    }

    private List<Integer> getGamesByRatingRange(double lowerBound, double upperBound) {
        List<Integer> games = new ArrayList<>();
        if (user.getReviews() != null) {
            for (Review review : user.getReviews().values()) {
                double rating = review.getRating();
                if (rating >= lowerBound && rating < upperBound) {
                    games.add(review.getGameId());
                }
            }
        }
        return games;
    }
}
