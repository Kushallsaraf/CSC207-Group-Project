package com.csc207.group.data_access;
import com.csc207.group.cache.Endpoints;
import com.csc207.group.cache.RAWGFirebaseAPICache;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.util.Map;
import com.csc207.group.model.Achievement;
import com.csc207.group.model.GameStore;
import com.csc207.group.model.Screenshot;
import com.csc207.group.service.AchievementService;
import com.csc207.group.service.GameStoreService;
import com.csc207.group.service.ScreenshotService;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

public class RAWGApiClient {
    private RAWGFirebaseAPICache cache;
    public static final String GAMES = "games/";
    public static final String REQUEST_ALREADY_IN_CACHE_RETURNING_VALUE = "request already in cache, returning value";
    public static final String REQUEST_NOT_IN_CACHE_CALLING_API = "request not in cache, calling API";
    private static final String API_KEY = "e2ddd48d917c48e9938b54095923c9ba";
    private static final String BASE_URL = "https://api.rawg.io/api/";

    static HttpClient client = HttpClient.newHttpClient();

    public static final Map<Integer, String> STORES_IDS_MAPPED_TO_NAMES;

    static {
        try {
            STORES_IDS_MAPPED_TO_NAMES = getStoreIDsMappedtoNames();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


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

        // checking if call was successful
        if (getResponse.statusCode() != 200) {
            throw new RuntimeException("API call failed with status: " + getResponse.statusCode());
        }


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

        // checking if call was successful
        if (getResponse.statusCode() != 200) {
            throw new RuntimeException("API call failed with status: " + getResponse.statusCode());
        }

        // caching the response
        System.out.println(REQUEST_NOT_IN_CACHE_CALLING_API);
        this.cache.cacheJsonString(Endpoints.RAWG_SCREENSHOTS_FOR_GAMES_BY_ID, gameID, getResponse.body());

        return ScreenshotService.parseScreenshotsFromJson(getResponse.body());
    }

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

        // checking if call was successful
        if (getResponse.statusCode() != 200) {
            throw new RuntimeException("API call failed with status: " + getResponse.statusCode());
        }


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

        // checking if call was successful
        if (getResponse.statusCode() != 200) {
            throw new RuntimeException("API call failed with status: " + getResponse.statusCode());
        }


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

        // checking if call was successful
        if (getResponse.statusCode() != 200) {
            throw new RuntimeException("API call failed with status: " + getResponse.statusCode());
        }


        // caching the response
        System.out.println(REQUEST_NOT_IN_CACHE_CALLING_API);
        this.cache.cacheJsonString(Endpoints.RAWG_STORES_FOR_GAMES_BY_ID, gameID, getResponse.body());
        System.out.println("Raw store JSON: " + getResponse.body());
        return GameStoreService.parseGameStoresFromJson(getResponse.body());
    }



}
