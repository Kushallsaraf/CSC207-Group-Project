package data_access;

import Cache.Endpoints;
import Cache.IGDBFirebaseAPICache;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

public class IGDBApiClient {
    private IGDBFirebaseAPICache cache;

    private static final String CLIENT_ID = "8iht4bl3vlgdgy2j6jtp9l63ky4ee1";
    private static final String ACCESS_TOKEN = "r7scsy47par3y00mi8wha41bayl6fm";
    private static final String BASE_URL = "https://api.igdb.com/v4/";




    public IGDBApiClient(IGDBFirebaseAPICache cache){
        this.cache = cache;

    }
    public JsonNode searchGamesByName(String gameName) {
        // checks if cache has the request stored already
        if (this.cache.hasRequest(Endpoints.IGDB_GAMES_BY_NAME, gameName)){
            System.out.println("request already in cache, returning value");
             return new JsonNode(this.cache.getResponse(Endpoints.IGDB_GAMES_BY_NAME, gameName));
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

        cache.cacheJsonNode(Endpoints.IGDB_GAMES_BY_NAME, gameName, response.getBody());
        return response.getBody();
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

