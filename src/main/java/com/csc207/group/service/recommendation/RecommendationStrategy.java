package com.csc207.group.service.recommendation;

import com.csc207.group.model.GameRecommendation;
import com.csc207.group.model.User;

import java.util.List;

public interface RecommendationStrategy {

    List<GameRecommendation> getRecommendations();
}
