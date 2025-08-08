package com.csc207.group.service.recommendation;

import com.csc207.group.cache.IGDBFirebaseAPICache;
import com.csc207.group.data_access.IGDBApiClient;
import com.csc207.group.model.GameRecommendation;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecommendationService {

    private final IGDBApiClient apiClient;

    public RecommendationService() {
        this.apiClient = new IGDBApiClient(new IGDBFirebaseAPICache());
    }

    public RecommendationService(IGDBApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public List<Integer> getSimilarGameIds(int gameId) {
        JSONObject game = fetchGameData(gameId);
        if (game == null || !game.has("similar_games")) return Collections.emptyList();

        List<Integer> similarIds = new ArrayList<>();
        JSONArray similarArray = game.getJSONArray("similar_games");
        for (int i = 0; i < similarArray.length(); i++) {
            similarIds.add(similarArray.getInt(i));
        }
        return similarIds;
    }

    /**Fetches games by gameIds.
     *
     * @param gameIds
     * @return
     */
    public List<GameRecommendation> getRecommendationsByIds(List<Integer> gameIds) {
        JsonNode resp = apiClient.getGamesByIds(gameIds);
        return parseRecommendations(resp);
    }

    public List<Integer> getGameGenreIds(Integer gameid){
        JsonNode node = apiClient.getGameDetailsById(gameid);
        List<Integer> genreIds = new ArrayList<>();

        if (node != null && node.getArray() != null && node.getArray().length() > 0) {
            JSONObject gameObj = node.getArray().getJSONObject(0);

            if (gameObj.has("genres")) {
                for (int i = 0; i < gameObj.getJSONArray("genres").length(); i++) {
                    genreIds.add(gameObj.getJSONArray("genres").getInt(i));
                }
            }
        }

        return genreIds;

    }

    /**returns the top 50 most popular games
     *
     * @param
     * @return
     */
    public List<GameRecommendation> parsePopularGames() {
        JsonNode response = apiClient.getTopPopularGames50();
        List<GameRecommendation> recommendations = new ArrayList<>();

        if (response == null || response.getArray() == null) {
            return recommendations;
        }

        for (int i = 0; i < response.getArray().length(); i++) {
            JSONObject obj = response.getArray().getJSONObject(i);

            int gameId = obj.optInt("id", -1);
            String name = obj.optString("name", "");

            // Cover URL
            String coverUrl = "";
            if (obj.has("cover")) {
                JSONObject coverObj = obj.getJSONObject("cover");
                String imageId = coverObj.optString("image_id", "");
                if (!imageId.isEmpty()) {
                    coverUrl = "https://images.igdb.com/igdb/image/upload/t_cover_big/" + imageId + ".jpg";
                }
            }

            // Release year
            int releaseYear = 0;
            if (obj.has("first_release_date")) {
                long timestamp = obj.getLong("first_release_date");
                releaseYear = Instant.ofEpochSecond(timestamp)
                        .atZone(ZoneId.systemDefault())
                        .getYear();
            }

            double rating = obj.optDouble("rating", 0);

            recommendations.add(new GameRecommendation(gameId,name ,coverUrl , releaseYear, rating));
        }

        return recommendations;
    }

    /**
     * Returns a list of game recommendations
     */
    private List<GameRecommendation> parseRecommendations(JsonNode response) {
        List<GameRecommendation> recommendations = new ArrayList<>();
        if (response == null || response.getArray() == null) return recommendations;

        for (int i = 0; i < response.getArray().length(); i++) {
            JSONObject obj = response.getArray().getJSONObject(i);

            int id = obj.optInt("id", 0);
            String title = obj.optString("name", "");

            // Cover URL
            String coverUrl = "";
            if (obj.has("cover")) {
                JSONObject coverObj = obj.optJSONObject("cover");
                if (coverObj != null) {
                    String imageId = coverObj.optString("image_id", "");
                    if (!imageId.isEmpty()) {
                        coverUrl = buildCoverUrl(imageId);
                    }
                }
            }

            // Release year from UNIX timestamp (seconds)
            int year = 0;
            if (obj.has("first_release_date")) {
                long ts = obj.optLong("first_release_date", 0L);
                if (ts > 0) {
                    year = Instant.ofEpochSecond(ts)
                            .atZone(ZoneId.systemDefault())
                            .getYear();
                }
            }

            double rating = obj.optDouble("rating", 0.0);

            recommendations.add(new GameRecommendation(id, title, coverUrl, year, rating));
        }
        return recommendations;
    }

    /**
     * Convenience: fetch by genre IDs (OR match) and return ready-to-use recommendations.
     * Expects your IGDBApiClient.getGamesByGenreIds to request:
     * fields id, name, cover.image_id, first_release_date, rating;
     */
    public List<GameRecommendation> getRecommendationsByGenres(List<Integer> genreIds, int limit) {
        JsonNode resp = apiClient.getGamesByGenreIds(genreIds, limit);
        return parseRecommendations(resp);
    }



    private static String buildCoverUrl(String imageId) {
        return "https://images.igdb.com/igdb/image/upload/t_cover_big/" + imageId + ".jpg";
    }

    private JSONObject fetchGameData(int gameId) {
        JsonNode json = apiClient.getGameDetailsById(gameId);
        JSONArray array = json.getArray();
        if (array.isEmpty()) return null;
        return array.getJSONObject(0);
    }

}

