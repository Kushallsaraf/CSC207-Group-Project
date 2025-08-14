package com.csc207.group.model;

import java.util.List;

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
