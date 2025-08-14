package com.csc207.group.service.recommendation;

import com.csc207.group.model.GameRecommendation;
import com.csc207.group.model.Review;
import com.csc207.group.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**Recommendation Strategy based
 * a random game from the user's best reviewed games, returning
 * results based on that game's genre. For example, if a user
 * rated Halo: Reach a 5 star, this strategy recommends based off that
 * game's genre(s).
 *
 */
public class GenreStrategy implements RecommendationStrategy{
    private RecommendationService service;
    private List<Integer> fiveStarGames;
    private List<Integer> fourStarGames;
    private User user;


    public GenreStrategy(RecommendationService service, User user){
        this.service = service;
        this.user = user;
        this.fiveStarGames = getGamesByRatingRange(5.0, 5.1);
        this.fourStarGames = getGamesByRatingRange(4.0, 5.0);

    }
    @Override
    public List<GameRecommendation> getRecommendations() {
        Integer referenceGameId = null;
        if (!fiveStarGames.isEmpty()) {
            referenceGameId = pickRandom(fiveStarGames);
        } else if (!fourStarGames.isEmpty()) {
            referenceGameId = pickRandom(fourStarGames);
        } else {
            // no four or five star reviews
            return new ArrayList<>();
        }

        //get the genre ids for that game
        List<Integer> genreIds= service.getGameGenreIds(referenceGameId);
        return service.getRecommendationsByGenres(genreIds, 10);



    }

    private List<Integer> getGamesByRatingRange(double lowerBound, double upperBound) {
        List<Integer> games = new ArrayList<>();
        if (user.getReviews() != null) {
            for (Review review : user.getReviews().values()) {
                double rating = review.getRating();
                if (rating >= lowerBound && rating < upperBound) {
                    games.add(review.getGameid());
                }
            }
        }
        return games;
    }

    private Integer pickRandom(List<Integer> ids) {
        int idx = ThreadLocalRandom.current().nextInt(ids.size());
        return ids.get(idx);
    }

}
