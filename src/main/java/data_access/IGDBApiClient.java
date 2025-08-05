package data_access;

import cache.Endpoints;
import cache.IGDBFirebaseAPICache;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

public class IGDBApiClient {
    private IGDBFirebaseAPICache cache;

    private static final String CLIENT_ID = "8iht4bl3vlgdgy2j6jtp9l63ky4ee1";
    private static final String ACCESS_TOKEN = "r7scsy47par3y00mi8wha41bayl6fm";
    private static final String BASE_URL = "https://api.igdb.com/v4/";

    public IGDBApiClient(IGDBFirebaseAPICache cache) {
        this.cache = cache;
    }

    public JsonNode searchGamesByName(String gameName) {
        String body = "search \"" + gameName + "\"; fields id; limit 5;";
        return handleCacheAction(Endpoints.IGDB_GAMES_BY_NAME, gameName, body, "games");
    }

    public JsonNode getGameDetailsById(int id) {
        String body = "fields *; where id = " + id + ";";
        return handleCacheAction(Endpoints.IGDB_GAME_BY_ID, String.valueOf(id), body, "games");
    }

    public JsonNode getGenresById(int id) {
        String body = "fields *; where id = " + id + ";";
        return handleCacheAction(Endpoints.IGDB_GENRES_BY_ID, String.valueOf(id), body, "genres");
    }

    public JsonNode getCoverArtById(int id) {
        String body = "fields *; where id = " + id + ";";
        return handleCacheAction(Endpoints.IGDB_COVERS_BY_ID, String.valueOf(id), body, "covers");
    }

    public JsonNode getPlatformsById(int id) {
        String body = "fields *; where id = " + id + ";";
        return handleCacheAction(Endpoints.IGDB_PLATFORMS_BY_ID, String.valueOf(id), body, "platforms");
    }

    public JsonNode getReleaseDateById(int id) {
        String body = "fields *; where id = " + id + ";";
        return handleCacheAction(Endpoints.IGDB_RELEASE_DATES_BY_ID, String.valueOf(id), body, "release_dates");
    }

    public JsonNode getDevelopersById(int id) {
        String cacheKey1 = "involved_company_" + id;
        String body1 = "fields *; where id = " + id + ";";
        JsonNode response1 = handleCacheAction(Endpoints.IGDB_INVOLVED_COMPANIES_BY_ID, cacheKey1, body1, "involved_companies");

        int companyId = response1.getArray().getJSONObject(0).getInt("company");
        String cacheKey2 = "company_" + companyId;
        String body2 = "fields *; where id = " + companyId + ";";
        return handleCacheAction(Endpoints.IGDB_COMPANIES_BY_ID, cacheKey2, body2, "companies");
    }

    public JsonNode getAgeRatingById(int id) {
        String cacheKey1 = "age_rating_" + id;
        String body1 = "fields *; where id = " + id + ";";
        JsonNode response1 = handleCacheAction(Endpoints.IGDB_AGE_RATINGS_BY_ID, cacheKey1, body1, "age_ratings");

        int categoryId = response1.getArray().getJSONObject(0).getInt("category");
        String cacheKey2 = "age_rating_category_" + categoryId;
        String body2 = "fields *; where id = " + categoryId + ";";
        return handleCacheAction(Endpoints.IGDB_AGE_RATING_CATEGORIES_BY_ID, cacheKey2, body2, "age_rating_categories");
    }

    private JsonNode handleCacheAction(String endpoint, String cacheKey, String body, String apiPath) {
        if (cache.hasRequest(endpoint, cacheKey)) {
            System.out.println("request found in cache");
            return new JsonNode(cache.getResponse(endpoint, cacheKey));
        }

        HttpResponse<JsonNode> response = Unirest.post(BASE_URL + apiPath)
                .header("Client-ID", CLIENT_ID)
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .header("Accept", "application/json")
                .body(body)
                .asJson();

        if (response.getStatus() != 200) {
            throw new RuntimeException("API call failed with status: " + response.getStatus());
        }

        System.out.println("request not found in cache, calling API...");
        cache.cacheJsonNode(endpoint, cacheKey, response.getBody());
        return response.getBody();
    }

    public void shutdown() {
        Unirest.shutDown();
    }
}

