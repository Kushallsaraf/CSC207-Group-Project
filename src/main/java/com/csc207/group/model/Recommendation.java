package com.csc207.group.model;

import com.csc207.group.service.recommendation.RecommendationService;
import com.csc207.group.service.recommendation.RecommendationStrategy;
import com.csc207.group.service.recommendation.WishlistStrategy;

import java.util.List;
import java.util.Map;

/**A recommendation. To be used by the frontend to populate recommendation entries
 *
 */
public class Recommendation {

    private List<GameRecommendation> recommendations;
    private String message;


    public Recommendation(List<GameRecommendation> recommendations, String message){
        this.recommendations = recommendations;
        this.message = message;

    }

    public List<GameRecommendation> getRecommendations() {
        return recommendations;
    }

    public String getMessage() {
        return message;
    }
}
