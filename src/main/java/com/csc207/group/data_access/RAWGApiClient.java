package com.csc207.group.data_access;
import cache.RAWGFirebaseAPICache;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.util.Map;

public class RAWGApiClient {
    private RAWGFirebaseAPICache cache;
    private static final String API_KEY = "e2ddd48d917c48e9938b54095923c9ba";
    private static final String BASE_URL = "https://api.rawg.io/api/";

    static HttpClient client = HttpClient.newHttpClient();


    public RAWGApiClient(RAWGFirebaseAPICache cache){
        this.cache = cache;
    }
    public Map<String, Object> getGamesByGenre(String genre) throws Exception{
        String endpoint = "games_by_genre";
        String cacheKey = genre;

        // Check cache
        if (cache.hasRequest(endpoint, cacheKey)) {
            System.out.println("request found in cache");
            String raw = cache.getResponse(endpoint, cacheKey);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(raw, Map.class);
        }

        // Not in cache: perform API call
        String url = BASE_URL + "games?key=" + API_KEY + "&genres=" + genre + "&page_size=40";
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();

        HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());

        if (getResponse.statusCode() != 200) {
            throw new RuntimeException("API call failed with status: " + getResponse.statusCode());
        }

        String rawBody = getResponse.body();
        System.out.println("request not found in cache, calling API...");
        cache.cacheString(endpoint, cacheKey, rawBody);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(rawBody, Map.class);

    }
}
