package com.csc207.group.service.recommendation;



import com.csc207.group.model.GameRecommendation;
import com.csc207.group.model.Recommendation;
import com.csc207.group.model.Review;
import com.csc207.group.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**A recommendation. To be used by the frontend to populate recommendation entries
 *
 */
public class RecommendationEngine {
    private static final String WISHLIST_MESSAGE = "You might also be interested in:";
    private static final String FIVE_STAR_MESSAGE = "Based on the games you love:";
    private static final String FOUR_STAR_MESSAGE = "Based on the games you like:";
    private static final String GENRE_STRATEGY = "Based on your favourite genres:";
    private static final String DEFAULT_MESSAGE = "Explore the best games:";

    private List<GameRecommendation> recommendations;
    private Map<String, RecommendationStrategy> strategies;
    private User user;
    private RecommendationService service;


    public RecommendationEngine(RecommendationService service, User user){

        this.user = user;
        this.service = service;
        this.strategies = new HashMap<>();
        getAvailableStrategies();
    }

    public Recommendation generateRecommendation(){
        List<String> keys = new ArrayList<>();
        keys.add(WISHLIST_MESSAGE);
        keys.add(FIVE_STAR_MESSAGE);
        keys.add(FOUR_STAR_MESSAGE);
        keys.add(GENRE_STRATEGY);
        keys.add(DEFAULT_MESSAGE);

        // Randomize order
        java.util.Collections.shuffle(keys);

        // Pick the first available strategy
        for (String key : keys) {
            if (this.strategies.containsKey(key)) {
                return getRecommendation(key);
            }
        }

        return getRecommendation(DEFAULT_MESSAGE);


    }

    private Recommendation getRecommendation(String key){
        List<GameRecommendation> recs = this.strategies.get(key).getRecommendations();
        return new Recommendation(recs, key);

    }



    private void getAvailableStrategies() {
        if (!this.user.getWishlist().isEmpty()){
            this.strategies.put(RecommendationEngine.WISHLIST_MESSAGE, new WishlistStrategy(service, user));

        }


        List<GameRecommendation> fiveStarGames = service.getRecommendationsByIds(getGamesByRatingRange
                (5.0, 5.1));
        List<GameRecommendation> fourStarGames = service.getRecommendationsByIds(getGamesByRatingRange
                (4.0, 5.0));
        if (!fiveStarGames.isEmpty()){
            this.strategies.put(FIVE_STAR_MESSAGE, new FiveStarReviewStrategy(service ,user));
        }
        if (!fourStarGames.isEmpty()){

            this.strategies.put(FOUR_STAR_MESSAGE, new FourStarReviewStrategy(service, user));
        }

        if (!fiveStarGames.isEmpty() || !fourStarGames.isEmpty()){
            this.strategies.put(GENRE_STRATEGY, new GenreStrategy(service, user));
        }

        this.strategies.put(DEFAULT_MESSAGE, new DefaultStrategy(service, user));

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

//    public static void main(String[] args) {
//        User user = new User("John", "1234");
//        user.getWishlist().add(19560);
//        user.getWishlist().add(7348);
//        user.getWishlist().add(1985);
//        user.getWishlist().add(375);
//        user.getWishlist().add(21073);
//        Review a = new Review("John", "great game", 73, 5.0);
//        Review b = new Review("John", "good game", 140839, 4.0);
//        Review c = new Review("John", "decent game", 75, 5.0);
//        Review d = new Review("John", "bad game", 74, 4.0);
//        Review e = new Review("John", "meh game", 2155, 2.0);
//        user.getReviews().put(73, a);
//        user.getReviews().put(140839, b);
//        user.getReviews().put(75, c);
//        user.getReviews().put(74, d);
//        user.getReviews().put(2155, e);
//        RecommendationService service = new RecommendationService();
//
//        RecommendationEngine engine = new RecommendationEngine(service, user);
//        Recommendation r = engine.generateRecommendation();
//        System.out.println("TESTING MESSAGE: " + r.getMessage());
//        List<GameRecommendation> lst = r.getRecommendations();
//        for (GameRecommendation rec: lst){
//            System.out.println(rec.getTitle());
//        }







    }

