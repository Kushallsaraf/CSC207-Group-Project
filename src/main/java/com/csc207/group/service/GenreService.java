package com.csc207.group.service;

import com.csc207.group.data_access.RAWGApiClient;
import com.csc207.group.model.Game;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class GenreService {
    public List<Game> getGamesByGenre(String genre) throws Exception {
        Map<String, Object> response = RAWGApiClient.getGamesByGenre(genre);
        List<Game> games = new ArrayList<>();

        List<Map<String, Object>> result = (List<Map<String, Object>>) response.get("results");

        for (Map<String, Object> game : result) {
            List<String> genres = new ArrayList<>();
            if (game.get("genres") != null) {
                List<Map<String, Object>> genreList = (List<Map<String, Object>>) game.get("genres");
                for (Map<String, Object> g : genreList) {
                    genres.add((String) g.get("name"));
                }
                games.add(new Game(game.get("name").toString(), genres, "Unknown"));
            }
        }
        return games;
    }
}

