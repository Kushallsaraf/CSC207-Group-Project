package com.csc207.group.service;

import com.csc207.group.cache.IGDBFirebaseAPICache;
import com.csc207.group.cache.RAWGFirebaseAPICache;
import com.csc207.group.data_access.IGDBApiClient;
import com.csc207.group.data_access.RAWGApiClient;
import com.csc207.group.model.Game;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class GenreService {
    private final RAWGApiClient client = new RAWGApiClient(new RAWGFirebaseAPICache());
    public List<Game> getGamesByGenre(String genre) throws Exception {
        Map<String, Object> response = client.getGamesByGenre(genre);
        List<Game> games = new ArrayList<>();

        List<Map<String, Object>> result = (List<Map<String, Object>>) response.get("results");

        for (Map<String, Object> game : result) {
            List<String> genres = new ArrayList<>();
            if (game.get("genres") != null) {
                List<Map<String, Object>> genreList = (List<Map<String, Object>>) game.get("genres");
                for (Map<String, Object> g : genreList) {
                    genres.add((String) g.get("name"));
                }
                Game g = new Game();
                g.setGenres(genres);
                g.setName((String) game.get("name"));
                if (game.containsKey("id")) {
                    g.setGameid((Integer) game.get("id"));
                }
                games.add(g);
            }
        }
        return games;
    }

    public String getGenresById(String id) {
        int IntId = Integer.parseInt(id);
        final IGDBApiClient apiClient = new IGDBApiClient(new IGDBFirebaseAPICache());
        JsonNode response = apiClient.getGenresById(IntId);
        JSONArray array = response.getArray();
        JSONObject genreObject = array.getJSONObject(0);
        return genreObject.get("name").toString();
    }
}