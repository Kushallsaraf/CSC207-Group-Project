package com.csc207.group.service;

import com.csc207.group.cache.IgdbFirebaseApiCache;
import com.csc207.group.data_access.IgdbApiClient;
import com.csc207.group.model.Dlc;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

public final class DlcService {
    private final IgdbApiClient apiClient = new IgdbApiClient(new IgdbFirebaseApiCache());

    /**
     * Gets dlc of a game by id.
     * @param id the id of the game.
     * @return returns a Dlc object.
     */
    public Dlc getDlcById(int id) {
        JsonNode json = apiClient.getGameDetailsById(id);
        JSONArray array = json.getArray();

        // Setting name
        JSONObject obj = array.getJSONObject(0);
        String name = obj.optString("name", "None");

        // Setting description
        String description = obj.optString("summary", "None");

        // Setting cover image url
        String coverPage = "";
        if (obj.has("cover")) {
            coverPage = new GameService().getCoverPageById(obj.optInt("cover", 0));
        }
        else {
            coverPage = "No cover page";
        }

        return new Dlc(name, description, coverPage);
    }
}
