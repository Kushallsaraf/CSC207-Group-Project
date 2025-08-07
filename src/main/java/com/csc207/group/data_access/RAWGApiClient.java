package com.csc207.group.data_access;
import Cache.Endpoints;
import Cache.RAWGFirebaseAPICache;
import com.csc207.group.model.Achievement;
import com.csc207.group.model.GameStore;
import com.csc207.group.model.Screenshot;
import com.csc207.group.service.AchievementService;
import com.csc207.group.service.GameStoreService;
import com.csc207.group.service.ScreenshotService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.List;


public class RAWGApiClient {
    public static final String GAMES = "games/";
    public static final String REQUEST_ALREADY_IN_CACHE_RETURNING_VALUE = "request already in cache, returning value";
    public static final String REQUEST_NOT_IN_CACHE_CALLING_API = "request not in cache, calling API";
    private RAWGFirebaseAPICache cache;

    private static final String API_KEY = "e2ddd48d917c48e9938b54095923c9ba";
    private static final String BASE_URL = "https://api.rawg.io/api/";
    static HttpClient client = HttpClient.newHttpClient();

    public RAWGApiClient(RAWGFirebaseAPICache cache) {

        this.cache = cache;

    }

    public static final Map<Integer, String> STORES_IDS_MAPPED_TO_NAMES;

    static {
        try {
            STORES_IDS_MAPPED_TO_NAMES = getStoreIDsMappedtoNames();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    public Map<String, Object> getGamesByGenre(String genre) throws IOException,
            URISyntaxException, InterruptedException {
        // cache check
        /*
        if (this.cache.hasRequest(Endpoints.RAWG_GAMES_BY_GENRE, genre)) {
            System.out.println(REQUEST_ALREADY_IN_CACHE_RETURNING_VALUE);
            String cachedJson = this.cache.getResponse(Endpoints.RAWG_GAMES_BY_GENRE, genre);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(cachedJson, Map.class);

        }
        */

        String url = BASE_URL + "games?key=" + API_KEY + "&genres=" + genre + "&page_size=40";

        HttpRequest getRequest = getHttpRequest(url);

        HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());

        // caching
        //kong.unirest.JsonNode node = new kong.unirest.JsonNode(getResponse.body());
        //System.out.println(REQUEST_NOT_IN_CACHE_CALLING_API);
        //this.cache.cacheJsonNode(Endpoints.RAWG_GAMES_BY_GENRE, genre, node);


        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(getResponse.body(), Map.class);
    }

    private static HttpRequest getHttpRequest(String url) throws URISyntaxException {

        return HttpRequest.newBuilder().uri(new URI(url)).GET().build();

    }

    public Integer findGameIdByName(String gameName) throws IOException, URISyntaxException,
            InterruptedException {
        // adding cache check
        if (this.cache.hasRequest(Endpoints.RAWG_GAME_ID_BY_NAME, gameName)) {
            System.out.println(REQUEST_ALREADY_IN_CACHE_RETURNING_VALUE);
            return Integer.parseInt(this.cache.getResponse(Endpoints.RAWG_GAME_ID_BY_NAME, gameName));
        }

        // we have to encode the game name so it works for game names with spaces and other characters like colons
        // this basically formats it as needed for the search request for the api -> so no issue with the request
        String encodedGameName = URLEncoder.encode(gameName, StandardCharsets.UTF_8);

        String url = BASE_URL + "games?key=" + API_KEY + "&search=" + encodedGameName;

        // request
        HttpRequest getRequest = getHttpRequest(url);

        // response
        HttpResponse<String> getResponse = client.send(getRequest, HttpResponse
                .BodyHandlers.ofString());

        // now extracting the data
        ObjectMapper mapper = new ObjectMapper();

        JsonNode rootNode = mapper.readTree(getResponse.body());
        JsonNode results = rootNode.get("results");
        // now we need the id from results
        if (results != null && !results.isEmpty() && results.isArray()) {
            // gets you the id
            int id = results.get(0).get("id").asInt();
            // caching before we return
            System.out.println(REQUEST_NOT_IN_CACHE_CALLING_API);
            this.cache.cacheInt(Endpoints.RAWG_GAME_ID_BY_NAME, gameName, id);

            return id;
        }
        else {
            return null;
        }


    }


    // this is for getting screenshots (not game covers)

    public List<Screenshot> getScreenshotsForGame(String gameID) throws IOException, InterruptedException,
            URISyntaxException {
        // adding cache check
        if (this.cache.hasRequest(Endpoints.RAWG_SCREENSHOTS_FOR_GAMES_BY_ID, gameID)) {
            System.out.println(REQUEST_ALREADY_IN_CACHE_RETURNING_VALUE);
            String cachedJson = this.cache.getResponse(Endpoints.RAWG_SCREENSHOTS_FOR_GAMES_BY_ID, gameID);
            return ScreenshotService.parseScreenshotsFromJson(cachedJson);

        }
        // so basically the api documentation notes -> https://api.rawg.io/api/games/{game_pk}/screenshots
        // this is the url for our http request
        // also note gameID is what is used in the code here in place of gamepk
        // this url variable is the endpoint the get request is sent to
        String url = BASE_URL + GAMES + gameID + "/screenshots" + "?key=" + API_KEY
                + "&page_size=10";
        // takes in the url for the get request as a uri object
        // .GET method tells java it's a get request (for reading data)
        // .build ends the request and builds it
        HttpRequest getRequest = getHttpRequest(url);
        // bodyhandlers -> tells java to treat response as a string
        HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
        // we use the object mapper class to convert json into java objects
        // now we make a map for the results part of the json which has a dict(map) of screenshots

        // caching the response
        System.out.println(REQUEST_NOT_IN_CACHE_CALLING_API);
        this.cache.cacheJsonString(Endpoints.RAWG_SCREENSHOTS_FOR_GAMES_BY_ID, gameID, getResponse.body());

        return ScreenshotService.parseScreenshotsFromJson(getResponse.body());
    }



    // need to add game rating system -> need a request for that as well.

    public List<Achievement> getGameAchievements(String gameID) throws IOException, InterruptedException,
            URISyntaxException {
        // adding in cache check
        if (this.cache.hasRequest(Endpoints.RAWG_ACHIEVEMENTS_FOR_GAMES_BY_ID, gameID)) {

            System.out.println(REQUEST_ALREADY_IN_CACHE_RETURNING_VALUE);
            String cachedJson = this.cache.getResponse(Endpoints.RAWG_ACHIEVEMENTS_FOR_GAMES_BY_ID, gameID);
            return AchievementService.parseAchievementsFromJson(cachedJson);

        }


        // note this get has no query parameters
        String url = BASE_URL + GAMES + gameID + "/achievements" + "?key="
                + API_KEY;

        HttpRequest getRequest = getHttpRequest(url);

        HttpResponse<String> getResponse = client.send(getRequest, HttpResponse
                .BodyHandlers.ofString());

        // decided to create an Achievement class to return the items as a list of achievement objects
        // and for it to be easier to lay them out in the ui

        // caching the response
        System.out.println(REQUEST_NOT_IN_CACHE_CALLING_API);
        this.cache.cacheJsonString(Endpoints.RAWG_ACHIEVEMENTS_FOR_GAMES_BY_ID, gameID, getResponse.body());

        return AchievementService.parseAchievementsFromJson(getResponse.body());
    }

    // since get stores for game doesn't give us store name. decided to make another method that
    // instead gets all store details, it has no arguments so it has a constant result
    // which i will store in a qunique constant
    // returns a map of ids mapped to the name of their corresponding store
    private static Map<Integer, String> getStoreIDsMappedtoNames() throws URISyntaxException, IOException, InterruptedException {

        String url = BASE_URL + "stores" + "?key=" + API_KEY;
            // creating request
        HttpRequest getRequest = getHttpRequest(url);

        // response
            HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            Map mapOfResponse = mapper.readValue(getResponse.body(), Map.class);
            List<Map<String, Object>> resultsFromResponse = (List<Map<String, Object>>) mapOfResponse.get("results");

            // now -> new map to store the data we return
            Map<Integer, String> storeDetails = new HashMap<>();

            // now we iterate through and parse each store's id and name to add to the map
            for(Map<String, Object> result : resultsFromResponse) {
                String storeName = (String) result.get("name");
                Integer id = (Integer) result.get("id");
                storeDetails.put(id, storeName);
            }
            return storeDetails;
    }


    public List<GameStore> getStoresForGame(String gameID) throws IOException, InterruptedException, URISyntaxException
    {
        // adding in cache check

        if (this.cache.hasRequest(Endpoints.RAWG_STORES_FOR_GAMES_BY_ID, gameID)) {
            System.out.println(REQUEST_ALREADY_IN_CACHE_RETURNING_VALUE);

            String cachedJson = this.cache.getResponse(Endpoints.RAWG_STORES_FOR_GAMES_BY_ID, gameID);
            System.out.println("Cached JSON: " + cachedJson);  // <--- DEBUG: see exactly what is stored

            return GameStoreService.parseGameStoresFromJson(cachedJson);

        }

        String url = BASE_URL + GAMES + gameID + "/stores" + "?key=" + API_KEY;

        // create the request
        HttpRequest getRequest = getHttpRequest(url);

        HttpResponse<String> getResponse = client.send(getRequest, HttpResponse
                .BodyHandlers.ofString());

        // caching the response
        System.out.println(REQUEST_NOT_IN_CACHE_CALLING_API);
        this.cache.cacheJsonString(Endpoints.RAWG_STORES_FOR_GAMES_BY_ID, gameID, getResponse.body());
        System.out.println("Raw store JSON: " + getResponse.body());
        return GameStoreService.parseGameStoresFromJson(getResponse.body());
    }



}
