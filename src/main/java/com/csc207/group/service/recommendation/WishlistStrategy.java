package com.csc207.group.service.recommendation;

import com.csc207.group.model.GameRecommendation;
import com.csc207.group.model.User;

import java.util.ArrayList;
import java.util.List;

public class WishlistStrategy implements RecommendationStrategy{

    private User user;
    private RecommendationService recommendationService;
    private List<Integer> recIds;

    public WishlistStrategy(RecommendationService recommendationService, User user){
        this.user = user;
        this.recommendationService = recommendationService;
        this.recIds = new ArrayList<>();
    }


    @Override
    public List<GameRecommendation> getRecommendations() {
        for (Integer item: user.getWishlist()){
            recIds.addAll(recommendationService.getSimilarGameIds(item));
        }
        List<GameRecommendation> recs = recommendationService.getRecommendationsByIds(recIds);
        return recs.subList(0, Math.min(recs.size(), 10));
    }
}
