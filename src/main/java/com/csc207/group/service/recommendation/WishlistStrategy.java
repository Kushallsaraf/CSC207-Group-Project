package com.csc207.group.service.recommendation;

import com.csc207.group.model.GameRecommendation;
import com.csc207.group.model.User;

import java.util.List;

public class WishlistStrategy implements RecommendationStrategy{

    private User user;
    private RecommendationService recommendationService;

    public WishlistStrategy(RecommendationService recommendationService, User user){
        this.user = user;
        this.recommendationService = recommendationService;
    }


    @Override
    public List<GameRecommendation> getRecommendations() {
        List<GameRecommendation> recs = recommendationService.getRecommendationsByIds(user.getWishlist());
        return recs.subList(0, Math.min(recs.size(), 10));
    }
}
