package data_access;

import Cache.FilePaths;
import Cache.IGDBRequestCache;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

import java.io.FileWriter;
import java.io.IOException;

public class IGDBApiClient {
    private IGDBRequestCache cache;

    private static final String CLIENT_ID = "8iht4bl3vlgdgy2j6jtp9l63ky4ee1";
    private static final String ACCESS_TOKEN = "r7scsy47par3y00mi8wha41bayl6fm";
    private static final String BASE_URL = "https://api.igdb.com/v4/";

    //Request types
    public static final String GAMES_BY_NAME_REQUEST = "games-by-name"; //for searchGamesByName(String gameName)

    //Response types

    public static final String JSON_NODE_UNIREST = "json-node-unirest";

    public IGDBApiClient(IGDBRequestCache cache){
        this.cache = cache;

    }
    public JsonNode searchGamesByName(String gameName) {
        // checks if cache has the request stored already
        if (this.cache.hasRequest(GAMES_BY_NAME_REQUEST, gameName)){
            System.out.println("request already in cache, returning value");
             return new JsonNode(this.cache.getResponse(GAMES_BY_NAME_REQUEST, gameName, JSON_NODE_UNIREST));
         }


        String body = "search \"" + gameName + "\"; fields id; limit 5;";

        HttpResponse<JsonNode> response = Unirest.post(BASE_URL + "games")
                .header("Client-ID", CLIENT_ID)
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .header("Accept", "application/json")
                .body(body)
                .asJson();

        if (response.getStatus() != 200) {
            throw new RuntimeException("Search failed with status: " + response.getStatus());
        }
        System.out.println("request not cached, calling API");
        return cache.cacheResponse(GAMES_BY_NAME_REQUEST, gameName,
                response.getBody());
    }

    public JsonNode getGameDetailsById(int id) {
        String body = "fields name, genres, involved_companies; where id = " + id + ";";

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

    public void shutdown() {
        Unirest.shutDown();
    }








}

