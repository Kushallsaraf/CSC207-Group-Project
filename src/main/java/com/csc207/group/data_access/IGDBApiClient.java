package com.csc207.group.data_access;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

public class IGDBApiClient {

    private static final String CLIENT_ID = "8iht4bl3vlgdgy2j6jtp9l63ky4ee1";
    private static final String ACCESS_TOKEN = "r7scsy47par3y00mi8wha41bayl6fm";
    private static final String BASE_URL = "https://api.igdb.com/v4/";

    public JsonNode searchGamesByName(String gameName) {
        String body = "search \"" + gameName + "\"; fields id; limit 5;";

        HttpResponse <JsonNode> response = Unirest.post(BASE_URL + "games")
                .header("Client-ID", CLIENT_ID)
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .header("Accept", "application/json")
                .body(body)
                .asJson();

        if (response.getStatus() != 200) {
            throw new RuntimeException("Search failed with status: " + response.getStatus());
        }
        return response.getBody();
    }

    public JsonNode getGameDetailsById(int id) {
        String body = "fields *; where id = " + id + ";";

        HttpResponse<JsonNode> response = Unirest.post(BASE_URL + "games")
                .header("Client-ID", CLIENT_ID)
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .header("Accept", "application/json")
                .body(body)
                .asJson();

        if (response.getStatus() != 200) {
            throw new RuntimeException("Details fetch failed with status: " + response.getStatus());
        }
        return response.getBody();
    }

    public JsonNode getGenresById(int id) {
        String body ="fields *; where id = " + id + ";";

        HttpResponse<JsonNode> response = Unirest.post("https://api.igdb.com/v4/genres")
                .header("Client-ID", CLIENT_ID)
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .header("Accept", "application/json")
                .body(body)
                .asJson();
        if (response.getStatus() != 200) {
            throw new RuntimeException("Genres fetch failed with status: " + response.getStatus());
        }
        return response.getBody();
    }

    public void shutdown() {
        Unirest.shutDown();
    }
}
