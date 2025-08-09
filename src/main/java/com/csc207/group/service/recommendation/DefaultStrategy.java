package com.csc207.group.service.recommendation;

import com.csc207.group.model.GameRecommendation;
import com.csc207.group.model.User;

import java.util.Collections;
import java.util.List;

/**Recommends based on popularity
 *
 */
public class DefaultStrategy implements RecommendationStrategy{
    private RecommendationService recommendationService;
    private User user;
    public DefaultStrategy(RecommendationService recommendationService, User user){
        this.recommendationService = recommendationService;
        this.user = user;


    }


    @Override
    public List<GameRecommendation> getRecommendations() {
        List<GameRecommendation> allPopular = this.recommendationService.parsePopularGames();

        Collections.shuffle(allPopular); // randomize order
        return allPopular.subList(0, Math.min(allPopular.size(), 10));
    }
}
