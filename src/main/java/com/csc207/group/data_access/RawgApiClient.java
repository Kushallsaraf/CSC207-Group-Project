package com.csc207.group.data_access;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.csc207.group.cache.Endpoints;
import com.csc207.group.cache.RawgFirebaseApiCache;
import com.csc207.group.model.Achievement;
import com.csc207.group.model.GameStore;
import com.csc207.group.model.Screenshot;
import com.csc207.group.service.AchievementService;
import com.csc207.group.service.GameStoreService;
import com.csc207.group.service.ScreenshotService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class RawgApiClient {
    public static final Map<Integer, String> STORES_IDS_MAPPED_TO_NAMES;
    public static final String GAMES = "games/";
    public static final String REQUEST_ALREADY_IN_CACHE_RETURNING_VALUE = "request already in cache, returning value";
    public static final String REQUEST_NOT_IN_CACHE_CALLING_API = "request not in cache, calling API";
    public static final String API_CALL_FAILED_WITH_STATUS = "API call failed with status: ";
    public static final int SUCCESS_CODE_200 = 200;
    public static final String RESULTS = "results";
    public static final String KEY_EQUALS = "?key=";
    public static final int CODE_404 = 404;
    private static final String API_KEY = "e2ddd48d917c48e9938b54095923c9ba";
    private static final String BASE_URL = "https://api.rawg.io/api/";
    private static HttpClient client = HttpClient.newHttpClient();
    private RawgFirebaseApiCache cache;

    static {
        try {
            STORES_IDS_MAPPED_TO_NAMES = getStoreIdsMappedToNames();
        }
        catch (URISyntaxException | InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    public RawgApiClient(RawgFirebaseApiCache cache) {
        this.cache = cache;
    }

    /**
     * Returns games that are a part of a genre.
     * @param genre A given genre.
     * @return the games of a specific genre.
     * @throws RuntimeException If response takes too long.
     * @throws IOException If there's an error communicating with API or the response it gives.
     * @throws InterruptedException if the HTTP request is interrupted.
     */
    public Map<String, Object> getGamesByGenre(String genre) throws IOException,
            InterruptedException {
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
        HttpRequest getRequest = null;
        try {
            getRequest = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();
        }
        catch (URISyntaxException ex) {
            throw new RuntimeException(ex);
        }

        HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());

        if (getResponse.statusCode() != SUCCESS_CODE_200) {
            throw new RuntimeException(API_CALL_FAILED_WITH_STATUS + getResponse.statusCode());
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

    /**
     * Returns a gameid using its name.
     * @param gameName The name of the game.
     * @return The RAWG API id of the game.
     * @throws IOException If there's an error communicating with API or the response it gives.
     * @throws URISyntaxException if the URI for the endpoint is invalid.
     * @throws InterruptedException if the HTTP request is interrupted.
     * @throws RuntimeException if the HTTP request takes too long.
     */
    public Integer findGameIdByName(String gameName) throws URISyntaxException,
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
        HttpResponse<String> getResponse = null;
        try {
            getResponse = client.send(getRequest, HttpResponse
                    .BodyHandlers.ofString());
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        // checking if call was successful
        if (getResponse.statusCode() != SUCCESS_CODE_200) {
            throw new RuntimeException(API_CALL_FAILED_WITH_STATUS + getResponse.statusCode());
        }

        // now extracting the data
        ObjectMapper mapper = new ObjectMapper();

        JsonNode rootNode = null;
        try {
            rootNode = mapper.readTree(getResponse.body());
        }
        catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
        JsonNode results = rootNode.get(RESULTS);
        // now we need the id from results
        Integer id = null;
        if (results != null && !results.isEmpty() && results.isArray()) {
            // gets you the id
            id = results.get(0).get("id").asInt();
            // caching before we return
            System.out.println(REQUEST_NOT_IN_CACHE_CALLING_API);
            this.cache.cacheInt(Endpoints.RAWG_GAME_ID_BY_NAME, gameName, id);
        }
        return id;
    }

    // this is for getting screenshots (not game covers)

    /**
     * Returns screenshots for a given game id.
     * @param gameID Game id of a game.
     * @return List of screenshots.
     * @throws InterruptedException if HTTP request is interrupted.
     * @throws URISyntaxException if URI for endpoint is invalid.
     * @throws JsonProcessingException if object can't be converted to Json.
     * @throws RuntimeException if HTTP request takes too long.
     */
    public List<Screenshot> getScreenshotsForGame(String gameID) throws InterruptedException,
            URISyntaxException {
        // adding cache check
        if (this.cache.hasRequest(Endpoints.RAWG_SCREENSHOTS_FOR_GAMES_BY_ID, gameID)) {
            System.out.println(REQUEST_ALREADY_IN_CACHE_RETURNING_VALUE);
            String cachedJson = this.cache.getResponse(Endpoints.RAWG_SCREENSHOTS_FOR_GAMES_BY_ID, gameID);
            try {
                return ScreenshotService.parseScreenshotsFromJson(cachedJson);
            }
            catch (JsonProcessingException ex) {
                throw new RuntimeException(ex);
            }

        }
        // so basically the api documentation notes -> https://api.rawg.io/api/games/{game_pk}/screenshots
        // this is the url for our http request
        // also note gameID is what is used in the code here in place of gamepk
        // this url variable is the endpoint the get request is sent to
        String url = BASE_URL + GAMES + gameID + "/screenshots" + KEY_EQUALS + API_KEY
                + "&page_size=10";
        // takes in the url for the get request as a uri object
        // .GET method tells java it's a get request (for reading data)
        // .build ends the request and builds it
        HttpRequest getRequest = getHttpRequest(url);
        // bodyhandlers -> tells java to treat response as a string
        HttpResponse<String> getResponse = null;
        try {
            getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        // checking if call was successful
        if (getResponse.statusCode() != SUCCESS_CODE_200) {
            throw new RuntimeException(API_CALL_FAILED_WITH_STATUS + getResponse.statusCode());
        }

        // caching the response
        System.out.println(REQUEST_NOT_IN_CACHE_CALLING_API);
        this.cache.cacheJsonString(Endpoints.RAWG_SCREENSHOTS_FOR_GAMES_BY_ID, gameID, getResponse.body());

        try {
            return ScreenshotService.parseScreenshotsFromJson(getResponse.body());
        }
        catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Returns games achievements for a given game.
     * @param gameID game id for a given game.
     * @return list of achievements for a given game.
     * @throws InterruptedException if HTTP request is interrupted.
     * @throws URISyntaxException if URI for endpoint is invalid.
     * @throws RuntimeException if HTTP request takes too long.
     * @throws JsonProcessingException if object can't be converted to Json.
     */
    public List<Achievement> getGameAchievements(String gameID)
            throws InterruptedException, URISyntaxException {

        List<Achievement> allAchievements = new ArrayList<>();

        // Check cache first
        String cachedJson = getCachedAchievements(gameID);
        if (cachedJson != null) {
            try {
                allAchievements = AchievementService.parseAchievementsFromJson(cachedJson);
            }
            catch (JsonProcessingException ex) {
                throw new RuntimeException(ex);
            }
        }
        else {
            allAchievements = fetchAllAchievementPagesFromApi(gameID);
            cacheAchievements(gameID, allAchievements);
        }
        // changed url so that the response gives us 20 achievements at once
        // the next url -> next page which will have the next achievements
        // so the while loop keeps going till there are no achievements left.

        return allAchievements;

    }
    // since get stores for game doesn't give us store name. decided to make another method that
    // instead gets all store details, it has no arguments so it has a constant result
    // which i will store in a qunique constant
    // returns a map of ids mapped to the name of their corresponding store

    private String getCachedAchievements(String gameID) {
        if (this.cache.hasRequest(Endpoints.RAWG_ACHIEVEMENTS_FOR_GAMES_BY_ID, gameID)) {
            System.out.println(REQUEST_ALREADY_IN_CACHE_RETURNING_VALUE);
            return this.cache.getResponse(Endpoints.RAWG_ACHIEVEMENTS_FOR_GAMES_BY_ID, gameID);
        }
        else {
            return null;
        }
    }

    private List<Achievement> fetchAllAchievementPagesFromApi(String gameID) throws URISyntaxException {

        List<Achievement> allAchievements = new ArrayList<>();

        String url = BASE_URL + GAMES + gameID + "/achievements"
                + KEY_EQUALS + API_KEY
                + "&page_size=20";

        ObjectMapper mapper = new ObjectMapper();

        // keep going till we find no next page => reach the end of the achievements
        while (url != null) {
            HttpRequest request = getHttpRequest(url);
            HttpResponse<String> response;
            try {
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
            }
            catch (IOException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }

            if (response.statusCode() == CODE_404) {
                // No achievements for this game
                System.out.println("No achievements found for game: " + gameID);
                // in the case of no achievements we return an empty list -> fixed issue
                return Collections.emptyList();
            }

            if (response.statusCode() != SUCCESS_CODE_200) {
                throw new RuntimeException(API_CALL_FAILED_WITH_STATUS + response.statusCode());
            }
            parseAndAddAchievement(response.body(), allAchievements);
            try {
                JsonNode rootNode = mapper.readTree(response.body());
                JsonNode results = rootNode.get(RESULTS);
                if (rootNode.hasNonNull("next")) {
                    url = rootNode.get("next").asText();
                }
                else {
                    url = null;
                }

            }
            catch (JsonProcessingException ex) {
                throw new RuntimeException(ex);
            }
        }
        return allAchievements;
    }

    private void parseAndAddAchievement(String responseBody, List<Achievement> allAchievements) {
        try {
            allAchievements.addAll(AchievementService.parseAchievementsFromJson(responseBody));
        }
        catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void cacheAchievements(String gameID, List<Achievement> allAchievements) {
        ObjectMapper mapper = new ObjectMapper();
        String combinedJson = null;
        try {
            combinedJson = mapper.writeValueAsString(Map.of(RESULTS, allAchievements));
        }
        catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
        this.cache.cacheJsonString(Endpoints.RAWG_ACHIEVEMENTS_FOR_GAMES_BY_ID, gameID, combinedJson);

    }

    private static Map<Integer, String> getStoreIdsMappedToNames() throws URISyntaxException,
            InterruptedException {

        String url = BASE_URL + "stores" + KEY_EQUALS + API_KEY;
        // creating request
        HttpRequest getRequest = getHttpRequest(url);

        // response
        HttpResponse<String> getResponse = null;
        try {
            getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        // checking if call was successful
        if (getResponse.statusCode() != SUCCESS_CODE_200) {
            throw new RuntimeException(API_CALL_FAILED_WITH_STATUS + getResponse.statusCode());
        }

        ObjectMapper mapper = new ObjectMapper();
        Map mapOfResponse = null;
        try {
            mapOfResponse = mapper.readValue(getResponse.body(), Map.class);
        }
        catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
        List<Map<String, Object>> resultsFromResponse = (List<Map<String, Object>>) mapOfResponse.get(RESULTS);

        // now -> new map to store the data we return
        Map<Integer, String> storeDetails = new HashMap<>();

        // now we iterate through and parse each store's id and name to add to the map
        for (Map<String, Object> result : resultsFromResponse) {
            String storeName = (String) result.get("name");
            Integer id = (Integer) result.get("id");
            storeDetails.put(id, storeName);
        }
        return storeDetails;
    }

    /**
     *  Gets stores for a given game id.
     * @param gameID a given game id.
     * @return a list of stores for a game.
     * @throws InterruptedException if HTTP request is interrupted.
     * @throws URISyntaxException if URI for endpoint is invalid.
     * @throws JsonProcessingException if object can't be converted to Json.
     * @throws IOException If there's an error communicating with API or the response it gives.
     * @throws RuntimeException if HTTP Request takes too long.
     */
    public List<GameStore> getStoresForGame(String gameID) throws InterruptedException, URISyntaxException {
        // adding in cache check
        if (this.cache.hasRequest(Endpoints.RAWG_STORES_FOR_GAMES_BY_ID, gameID)) {
            System.out.println(REQUEST_ALREADY_IN_CACHE_RETURNING_VALUE);

            String cachedJson = this.cache.getResponse(Endpoints.RAWG_STORES_FOR_GAMES_BY_ID, gameID);

            try {
                return GameStoreService.parseGameStoresFromJson(cachedJson);
            }
            catch (JsonProcessingException ex) {
                throw new RuntimeException(ex);
            }
        }

        String url = BASE_URL + GAMES + gameID + "/stores" + KEY_EQUALS + API_KEY;

        // create the request
        HttpRequest getRequest = getHttpRequest(url);

        HttpResponse<String> getResponse = null;
        try {
            getResponse = client.send(getRequest, HttpResponse
                    .BodyHandlers.ofString());
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        // checking if call was successful
        if (getResponse.statusCode() != SUCCESS_CODE_200) {
            throw new RuntimeException(API_CALL_FAILED_WITH_STATUS + getResponse.statusCode());
        }

        // caching the response
        System.out.println(REQUEST_NOT_IN_CACHE_CALLING_API);
        this.cache.cacheJsonString(Endpoints.RAWG_STORES_FOR_GAMES_BY_ID, gameID, getResponse.body());
        System.out.println("Raw store JSON: " + getResponse.body());
        try {
            return GameStoreService.parseGameStoresFromJson(getResponse.body());
        }
        catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }

}
