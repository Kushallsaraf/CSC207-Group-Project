package com.csc207.group.data_access;

import com.csc207.group.cache.Endpoints;
import com.csc207.group.cache.IGDBFirebaseAPICache;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

import java.util.List;
import java.util.stream.Collectors;

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

    public JsonNode getTopPopularGames50() {
        String body =
                "fields id, name, cover.image_id, first_release_date, rating, popularity;" +
                        " sort popularity desc;" +
                        " limit 50;";

        String cacheKey = "popular:top50";
        return handleCacheAction(Endpoints.IGDB_POPULAR_GAMES_TOP50, cacheKey, body, "games");
    }

    public JsonNode getGameDetailsById(int id) {
        String body = "fields *; where id = " + id + ";";
        return handleCacheAction(Endpoints.IGDB_GAME_BY_ID, String.valueOf(id), body, "games");
    }

    public JsonNode getGamesByIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new IllegalArgumentException("ids must not be null or empty");
        }

        String idCsv = ids.stream().map(String::valueOf).collect(Collectors.joining(","));
        String body =
                "fields id, name, cover.image_id, first_release_date, rating;" +
                        " where id = (" + idCsv + ");" +
                        " limit " + ids.size() + ";";

        String cacheKey = "gamesByIds=" + idCsv;
        return handleCacheAction(Endpoints.IGDB_GAMES_BY_IDS_MIN, cacheKey, body, "games");
    }



    /**Get the name of a genre by the genre id
     *
     * @param id
     * @return
     */
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

    /** Fetches game details for games that result from searching games by genre
     *
     * @param genreIds
     * @param limit
     * @return
     */

    public JsonNode getGamesByGenreIds(List<Integer> genreIds, int limit) {
        if (genreIds == null || genreIds.isEmpty()) {
            throw new IllegalArgumentException("genreIds must not be null or empty");
        }
        if (limit <= 0) limit = 20;

        String ids = genreIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        String body = "fields id, name, cover.image_id, first_release_date, rating;" +
                " where genres = (" + ids + ");" +
                " sort rating desc;" +
                " limit " + limit + ";";

        String cacheKey = "genres=" + ids + "&limit=" + limit;
        return handleCacheAction(Endpoints.IGDB_GAMES_BY_GENRES, cacheKey, body, "games");
    }

    /**A default version of getGamesByGenreId when
     * the limit is not defined
     *
     * @param genreIds
     * @return
     */
    public JsonNode getGamesByGenreIds(List<Integer> genreIds) {
        return getGamesByGenreIds(genreIds, 20);
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
    }}



