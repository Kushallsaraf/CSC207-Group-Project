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

    public JsonNode getDevelopersById(int id) {
        String body ="fields *; where id = " + id + ";";

        HttpResponse<JsonNode> jsonResponse = Unirest.post("https://api.igdb.com/v4/involved_companies")
                .header("Client-ID", CLIENT_ID)
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .header("Accept", "application/json")
                .body(body)
                .asJson();
        if (jsonResponse.getStatus() != 200) {
            throw new RuntimeException("Franchises fetch failed with status: " + jsonResponse.getStatus());
        }

        String body2 = "fields *; where id = " + jsonResponse.getBody().getArray().getJSONObject(0).get("company") + ";";

        HttpResponse<JsonNode> finalResponse = Unirest.post("https://api.igdb.com/v4/companies")
                .header("Client-ID", CLIENT_ID)
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .header("Accept", "application/json")
                .body(body2)
                .asJson();

        return finalResponse.getBody();
    }

    public JsonNode getPlatformsById(int id) {
        String body ="fields *; where id = " + id + ";";
        HttpResponse<JsonNode> jsonResponse = Unirest.post("https://api.igdb.com/v4/platforms")
                .header("Client-ID", CLIENT_ID)
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .header("Accept", "application/json")
                .body(body)
                .asJson();

        return jsonResponse.getBody();
    }

    public JsonNode getCoverArtById(int id) {
        String body ="fields *; where id = " + id + ";";
        HttpResponse<JsonNode> jsonResponse = Unirest.post("https://api.igdb.com/v4/covers")
                .header("Client-ID", CLIENT_ID)
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .header("Accept", "application/json")
                .body(body)
                .asJson();

        return jsonResponse.getBody();
    }

    public JsonNode getAgeRatingById(int id) {
        String body ="fields *; where id = " + id + ";";
        HttpResponse<JsonNode> jsonResponse = Unirest.post("https://api.igdb.com/v4/age_ratings")
                .header("Client-ID", CLIENT_ID)
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .header("Accept", "application/json")
                .body(body)
                .asJson();

        int id2 = jsonResponse.getBody().getArray().getJSONObject(0).getInt("category");
        String body2 = "fields *; where id = " + id2 + ";";
        HttpResponse<JsonNode> finalResponse = Unirest.post("https://api.igdb.com/v4/age_rating_categories")
                .header("Client-ID", CLIENT_ID)
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .header("Accept", "application/json")
                .body(body2)
                .asJson();

        return finalResponse.getBody();
    }

    public JsonNode getReleaseDateById(int id) {
        String body ="fields *; where id = " + id + ";";
        HttpResponse<JsonNode> jsonResponse = Unirest.post("https://api.igdb.com/v4/release_dates")
                .header("Client-ID", CLIENT_ID)
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .header("Accept", "application/json")
                .body(body)
                .asJson();

        return jsonResponse.getBody();
    }

    public void shutdown() {
        Unirest.shutDown();
    }
}
