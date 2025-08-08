package com.csc207.group.service;


import com.csc207.group.cache.IGDBFirebaseAPICache;
import com.csc207.group.data_access.IGDBApiClient;
import com.csc207.group.model.GamePreview;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GamePreviewService {
    private final IGDBApiClient apiClient = new IGDBApiClient(new IGDBFirebaseAPICache());

    public List<Integer> getGameByName(String Name) {
        JsonNode response = apiClient.searchGamesByName(Name);
        JSONArray game_ids = response.getArray();
        List<Integer> game_id_list = new ArrayList<>();
        for (int i = 0; i < game_ids.length(); i++) {
            game_id_list.add(game_ids.getInt(i));
        }
        return game_id_list;
    }

    public GamePreview getGameById(int id) {
        JsonNode response = apiClient.getGameDetailsById(id);
        JSONArray array = response.getArray();

        // setting name
        JSONObject gameJson = array.getJSONObject(0);
        String name = gameJson.optString("name", "Unknown");

        // setting cover image url
        String cover_page = "";
        if (gameJson.has("cover")) {
            cover_page = new GameService().getCoverPageById(gameJson.optInt("cover", 0));
        }
        else {
            cover_page = "No cover page";
        }

        // setting description of the game
        String summary = gameJson.optString("summary", "None");

        return new GamePreview(name, cover_page, summary);
    }
}

