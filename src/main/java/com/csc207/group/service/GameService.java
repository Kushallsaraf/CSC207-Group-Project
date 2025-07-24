package com.csc207.group.service;

import com.csc207.group.model.Game;
import data_access.IGDBApiClient;
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
        System.out.println(json);

        if (array.isEmpty()) return -1;

        JSONObject firstMatch = array.getJSONObject(0);
        return firstMatch.getInt("id");
    }

    public Game getGameById(int id) {
        JsonNode json = apiClient.getGameDetailsById(id);
        JSONArray array = json.getArray();

        if (array.isEmpty()) return null;

        JSONObject gameJson = array.getJSONObject(0);
        String name = gameJson.optString("name", "Unknown");

        List<String> genres = new ArrayList<>();
        if (gameJson.has("genres")) {
            JSONArray genreArray = gameJson.getJSONArray("genres");
            for (int i = 0; i < genreArray.length(); i++) {
                genres.add("Genre ID: " + genreArray.getInt(i));
            }
        }

        String developer = "Developer ID(s): ";
        if (gameJson.has("involved_companies")) {
            JSONArray devArray = gameJson.getJSONArray("involved_companies");
            for (int i = 0; i < devArray.length(); i++) {
                developer += devArray.getInt(i) + " ";
            }
        }

        return new Game(name, genres, developer);
    }
}