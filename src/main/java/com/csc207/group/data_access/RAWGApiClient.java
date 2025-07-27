package com.csc207.group.data_access;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.util.Map;

public class RAWGApiClient {
    private static final String API_KEY = "e2ddd48d917c48e9938b54095923c9ba";
    private static final String BASE_URL = "https://api.rawg.io/api/";

    static HttpClient client = HttpClient.newHttpClient();

    public static Map<String, Object> getGamesByGenre(String genre) throws Exception{
        String url = BASE_URL + "games?key=" + API_KEY + "&genres=" + genre + "&page_size=40";

        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();

        HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(getResponse.body(), Map.class);
    }
}
