package com.csc207.group.service;

import com.csc207.group.cache.IGDBFirebaseAPICache;
import com.csc207.group.data_access.IGDBApiClient;
import com.csc207.group.model.DLC;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

public class DLCService {
    private final IGDBApiClient apiClient = new IGDBApiClient(new IGDBFirebaseAPICache());

    public DLC getDLCById(int id) {
        JsonNode json = apiClient.getGameDetailsById(id);
        JSONArray array = json.getArray();

        // Setting name
        JSONObject obj = array.getJSONObject(0);
        String name = obj.optString("name", "None");

        // Setting description
        String description = obj.optString("summary", "None");

        // Setting cover image url
        String cover_page = "";
        if (obj.has("cover")) {
            cover_page = new GameService().getCoverPageById(obj.optInt("cover", 0));
        }
        else {
            cover_page = "No cover page";
        }

        return new DLC(name, description, cover_page);
    }
}
