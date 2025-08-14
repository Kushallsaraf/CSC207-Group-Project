package com.csc207.group.data_access;

import java.util.List;
import java.util.stream.Collectors;

import com.csc207.group.cache.Endpoints;
import com.csc207.group.cache.IgdbFirebaseApiCache;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

public class IgdbApiClient {
    public static final String LINE_BREAK = "\n";
    public static final String FIELDS_WHERE_ID = "fields *; where id = ";
    public static final String SEMICOLON_STRING = ";";
    public static final String GAMES = "games";
    public static final int LIMIT_20 = 20;
    public static final int SUCCESS_CODE_200 = 200;
    private IgdbFirebaseApiCache cache;

    private static final String CLIENT_ID = "8iht4bl3vlgdgy2j6jtp9l63ky4ee1";
    private static final String ACCESS_TOKEN = "r7scsy47par3y00mi8wha41bayl6fm";
    private static final String BASE_URL = "https://api.igdb.com/v4/";

    public IgdbApiClient(IgdbFirebaseApiCache cache) {
        this.cache = cache;
    }

    public JsonNode searchGamesByName(String gameName) {
        String body = "search \"" + gameName + "\"; fields id; limit 5;";
        return handleCacheAction(Endpoints.IGDB_GAMES_BY_NAME, gameName, body, GAMES);
    }

    public JsonNode getTopPopularGames50() {
        String body =
                "fields id,name,cover.image_id,first_release_date,aggregated_rating,total_rating,total_rating_count;"
                        + LINE_BREAK + "where category = 0 & version_parent = null & first_release_date != null;"
                        + LINE_BREAK + "sort total_rating_count desc;"
                        + LINE_BREAK + "limit 50;";

        String cacheKey = "popular:top50";
        return handleCacheAction(Endpoints.IGDB_POPULAR_GAMES_TOP50, cacheKey, body, GAMES);

    }

    public JsonNode getGameDetailsById(int id) {
        String body = FIELDS_WHERE_ID + id + SEMICOLON_STRING;
        return handleCacheAction(Endpoints.IGDB_GAME_BY_ID, String.valueOf(id), body, GAMES);
    }

    public JsonNode getGamesByIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new IllegalArgumentException("ids must not be null or empty");
        }

        String idCsv = ids.stream().map(String::valueOf).collect(Collectors.joining(","));
        String body =
                "fields id, name, cover.image_id, first_release_date, rating;"
                        + " where id = (" + idCsv + ");"
                        + " limit " + ids.size() + SEMICOLON_STRING;

        String cacheKey = "gamesByIds=" + idCsv;
        return handleCacheAction(Endpoints.IGDB_GAMES_BY_IDS_MIN, cacheKey, body, GAMES);
    }

    /**
     * Get the name of a genre by the genre id.
     * @param id The id of a given genre.
     * @return The JsonNode of a genre.
     */
    public JsonNode getGenresById(int id) {
        String body = FIELDS_WHERE_ID + id + SEMICOLON_STRING;
        return handleCacheAction(Endpoints.IGDB_GENRES_BY_ID, String.valueOf(id), body, "genres");
    }

    public JsonNode getCoverArtById(int id) {
        String body = FIELDS_WHERE_ID + id + SEMICOLON_STRING;
        return handleCacheAction(Endpoints.IGDB_COVERS_BY_ID, String.valueOf(id), body, "covers");
    }

    public JsonNode getPlatformsById(int id) {
        String body = FIELDS_WHERE_ID + id + SEMICOLON_STRING;
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
        if (limit <= 0) {
            limit = LIMIT_20;
        }

        String ids = genreIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        String body = "fields id, name, cover.image_id, first_release_date, rating;"
                + " where genres = (" + ids + ");"
                + " sort rating desc;"
                + " limit " + limit + SEMICOLON_STRING;

        String cacheKey = "genres=" + ids + "&limit=" + limit;
        return handleCacheAction(Endpoints.IGDB_GAMES_BY_GENRES, cacheKey, body, GAMES);
    }

    /**
     * A default version of getGamesByGenreId when
     * the limit is not defined.
     *
     * @param genreIds Ids of a genre.
     * @return the games a part of a genre.
     */
    public JsonNode getGamesByGenreIds(List<Integer> genreIds) {
        return getGamesByGenreIds(genreIds, LIMIT_20);
    }

    public JsonNode getReleaseDateById(int id) {
        String body = FIELDS_WHERE_ID + id + SEMICOLON_STRING;
        return handleCacheAction(Endpoints.IGDB_RELEASE_DATES_BY_ID, String.valueOf(id), body, "release_dates");
    }

    public JsonNode getDevelopersById(int id) {
        String cacheKey1 = "involved_company_" + id;
        String body1 = FIELDS_WHERE_ID + id + SEMICOLON_STRING;
        JsonNode response1 = handleCacheAction(Endpoints.IGDB_INVOLVED_COMPANIES_BY_ID, cacheKey1, body1, "involved_companies");

        int companyId = response1.getArray().getJSONObject(0).getInt("company");
        String cacheKey2 = "company_" + companyId;
        String body2 = FIELDS_WHERE_ID + companyId + SEMICOLON_STRING;
        return handleCacheAction(Endpoints.IGDB_COMPANIES_BY_ID, cacheKey2, body2, "companies");
    }

    public JsonNode getAgeRatingById(int id) {
        String cacheKey1 = "age_rating_" + id;
        String body1 = FIELDS_WHERE_ID + id + SEMICOLON_STRING;
        JsonNode response1 = handleCacheAction(Endpoints.IGDB_AGE_RATINGS_BY_ID, cacheKey1, body1, "age_ratings");

        int categoryId = response1.getArray().getJSONObject(0).getInt("category");
        String cacheKey2 = "age_rating_category_" + categoryId;
        String body2 = FIELDS_WHERE_ID + categoryId + SEMICOLON_STRING;
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

        if (response.getStatus() != SUCCESS_CODE_200) {
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
