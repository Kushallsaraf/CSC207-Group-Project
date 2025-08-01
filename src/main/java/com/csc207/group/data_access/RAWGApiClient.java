package com.csc207.group.data_access;
import com.csc207.group.model.Achievement;
import com.csc207.group.model.GameStore;
import com.csc207.group.service.AchievementService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.Map;
import java.util.List;


public class RAWGApiClient {
    private static final String API_KEY = "e2ddd48d917c48e9938b54095923c9ba";
    private static final String BASE_URL = "https://api.rawg.io/api/";


    static HttpClient client = HttpClient.newHttpClient();

    /*
    public static final Map<String, Integer> STORES_MAPPED_TO_IDS;

    static {
        try {
            STORES_MAPPED_TO_IDS = getStoreMappedToID();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    */

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

    // this is for getting screenshots (not game covers)
    /*
    public static Map<String, Object> getScreenshotsForGame(String gameID) throws Exception {
        // so basically the api documentation notes -> https://api.rawg.io/api/games/{game_pk}/screenshots
        // this is the url for our http request
        // also note gameID is what is used in the code here in place of gamepk
        // this url variable is the endpoint the get request is sent to
        String url = BASE_URL + "games/" + gameID + "/screenshots" + "?key=" + API_KEY
                + "&page_size=10";
        // takes in the url for the get request as a uri object
        // .GET method tells java it's a get request (for reading data)
        // .build ends the request and builds it
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();
        // bodyhandlers -> tells java to treat response as a string
        HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
        // we use the object mapper class to convert json into java objects
        ObjectMapper mapper = new ObjectMapper();
        // now we make a map for the results part of the json which has a dict(map) of screenshots
        //return mapper.readValue(getResponse.body(), Map.class);
        throw new UnsupportedOperationException("Not implemented yet.");
    }

     */

    // need to add game rating system -> need a request for that as well.

    public static List<Achievement> getGameAchievements(String gameID) throws Exception {
        // note this get has no query parameters
        String url = BASE_URL + "games/" + gameID + "/achievements" + "?key="
                + API_KEY;

        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI (url))
                .GET()
                .build();

        HttpResponse<String> getResponse = client.send(getRequest, HttpResponse
                .BodyHandlers.ofString());

        // ObjectMapper mapper = new ObjectMapper();
        // decided to create an Achievement class to return the items as a list of achievement objects
        // and for it to be easier to lay them out in the ui
        // return (List<Map<String, Object>>) achievementsJson.get("results");
        return AchievementService.parseAchievementsFromJson(getResponse.body());
    }

    // since get stores for game doesn't give us game name. decided to make another method that
    // instead gets all store details, it has no arguments so it has a constant result
    // which i will store in a qunique constant
    /*
    private static Map<String, Integer> getStoreMappedToID() throws Exception {
            // TODO: fix the error here
            String url = BASE_URL + "/stores" + "?key=" + API_KEY;

            // creating request
            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(new URI (url))
                    .GET()
                    .build();

            // response
            HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> mapOfResponse = mapper.readValue(getResponse.body(), Map.class);
            List<Map<String, Object>> resultsFromResponse = (List<Map<String, Object>>) mapOfResponse.get("results");

            // now -> new map to store the data we return
            Map<String, Integer> storeDetails = new HashMap<>();

            // now we iterate through and parse each store's id and name to add to the map
            for(Map<String, Object> result : resultsFromResponse) {
                String storeName = (String) result.get("name");
                Integer id = (Integer) result.get("id");
                storeDetails.put(storeName, id);
            }
            //return storeDetails;
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public static List<GameStore> getStoresForGame(String gameID) throws Exception {
        String url = BASE_URL + "games/" + gameID + "/stores" + "?key=" + API_KEY;

        // create the request
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI (url))
                .GET()
                .build();

        HttpResponse<String> getResponse = client.send(getRequest, HttpResponse
                .BodyHandlers.ofString());
        // still need to finish implmementation
    }
    */


}
