package com.csc207.group.service;

import com.csc207.group.model.Game;
import com.csc207.group.data_access.IGDBApiClient;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GameService {

    private final IGDBApiClient apiClient = new IGDBApiClient();

    public int getGameIdByName(String name) {
        JsonNode json = apiClient.searchGamesByName(name);
        JSONArray array = json.getArray();

        if (array.isEmpty()) return -1;

        JSONObject firstMatch = array.getJSONObject(1);
        return firstMatch.getInt("id");
    }

    public Game getGameById(int id) {
        JsonNode json = apiClient.getGameDetailsById(id);
        JSONArray array = json.getArray();

        if (array.isEmpty()) return null;

        JSONObject gameJson = array.getJSONObject(0);
        System.out.println(gameJson);
        String name = gameJson.optString("name", "Unknown");

        List<String> genres = new ArrayList<>();
        if (gameJson.has("genres")) {
            JSONArray genreArray = gameJson.getJSONArray("genres");
            for (int i = 0; i < genreArray.length(); i++) {
                genres.add(String.valueOf(genreArray.getInt(i)));
            }
        }

        StringBuilder developer = new StringBuilder("Developer ID(s): ");
        if (gameJson.has("involved_companies")) {
            JSONArray devArray = gameJson.getJSONArray("involved_companies");
            for (int i = 0; i < devArray.length(); i++) {
                developer.append(devArray.getInt(i)).append(" ");
            }
        }

        double aggregate_rating = gameJson.optDouble("rating", 0);
        double rating_count = gameJson.optDouble("rating_count", 0);

        Game gameobj = new Game();
        gameobj.setName(name);
        gameobj.setGenres(genres);
        gameobj.setDeveloper(developer.toString());
        gameobj.setCritic_rating(aggregate_rating);
        gameobj.setRating_count(rating_count);

        return gameobj;
    }
}