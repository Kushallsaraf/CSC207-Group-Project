package com.csc207.group.service;

import com.csc207.group.cache.IgdbFirebaseApiCache;
import com.csc207.group.data_access.IgdbApiClient;
import com.csc207.group.model.GamePreview;
import javafx.scene.image.Image;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class GamePreviewService {
    private final IgdbApiClient apiClient = new IgdbApiClient(new IgdbFirebaseApiCache());

    public List<Integer> getGameByName(String name) {
        JsonNode response = apiClient.searchGamesByName(name);
        JSONArray gameIdsArray = response.getArray();
        List<Integer> gameIdList = new ArrayList<>();
        for (int i = 0; i < gameIdsArray.length(); i++) {
            // Assuming the JSON objects have an "id" field
            gameIdList.add(gameIdsArray.getJSONObject(i).getInt("id"));
        }
        return gameIdList;
    }

    public GamePreview getGameById(int id) {
        JsonNode response = apiClient.getGameDetailsById(id);
        JSONArray array = response.getArray();

        if (array.isEmpty()) {
            return null;
        }

        JSONObject gameJson = array.getJSONObject(0);

        // Extract all necessary data
        String name = gameJson.optString("name", "Unknown");
        int year = extractReleaseYear(gameJson);
        Image coverImage = extractCoverImage(gameJson);
        String summary = gameJson.optString("summary", "No description available.");

        // Create the GamePreview object with the correct parameters
        return new GamePreview(name, year, coverImage, id, summary);
    }

    // Helper method to get the release year from the timestamp
    private int extractReleaseYear(JSONObject game) {
        if (!game.has("first_release_date")) return -1;
        long timestamp = game.getLong("first_release_date");
        ZonedDateTime dateTime = Instant.ofEpochSecond(timestamp).atZone(ZoneId.systemDefault());
        return dateTime.getYear();
    }

    // Helper method to get the cover image from its ID and URL
    private Image extractCoverImage(JSONObject gameData) {
        if (!gameData.has("cover")) return null; // Or return a placeholder image

        int coverId = gameData.getInt("cover");
        JsonNode coverJson = apiClient.getCoverArtById(coverId);
        JSONArray coverArray = coverJson.getArray();

        if (coverArray.isEmpty()) return null;

        JSONObject coverObj = coverArray.getJSONObject(0);
        if (!coverObj.has("url")) return null;

        String url = coverObj.getString("url");
        if (url.startsWith("//")) {
            url = "https:" + url;
        }
        return new Image(url, true);
    }
}