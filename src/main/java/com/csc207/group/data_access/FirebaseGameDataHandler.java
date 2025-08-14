package com.csc207.group.data_access;

import com.csc207.group.cache.FirebaseRestClient;
import com.csc207.group.model.Game;
import com.csc207.group.model.Review;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Game objects are saved in firebase database.
 * This class is responsible for the storing and retrieving of these objects.
 */
public class FirebaseGameDataHandler implements GameDataHandler {
    public static final String STRING_REVIEWS = "reviews";
    public static final String STRING_GAMES_BACKSLASH = "Games/";
    private final FirebaseRestClient client;

    public FirebaseGameDataHandler(FirebaseRestClient client) {
        this.client = client;
    }

    @Override
    public void saveGameData(int gameID, Game game) {
        int gameid = game.getGameid();
        if (gameid == 0) {
            gameid = gameID;
        }

        JSONObject gameJson = new JSONObject();

        gameJson.put("name", game.getName());
        gameJson.put("genres", game.getGenres());
        gameJson.put("developer", game.getDeveloper());
        gameJson.put("critic_rating", game.getCriticRating());
        gameJson.put("rating_count", game.getRatingCount());
        gameJson.put("platforms", game.getPlatforms());
        gameJson.put("cover_image", game.getCoverImage());
        gameJson.put("age_rating", game.getAgeRating());
        gameJson.put("release_date", game.getReleaseDate());
        gameJson.put("DLCs", game.getDownloadableContent());
        gameJson.put("description", game.getDescription());

        JSONArray reviewsArray = new JSONArray();
        for (Review review : game.getReviews()) {
            JSONObject reviewJson = new JSONObject();
            reviewJson.put("userid", review.getUserId());
            reviewJson.put("content", review.getContent());
            reviewJson.put("gameid", review.getGameId());
            reviewJson.put("rating", review.getRating());
            reviewsArray.put(reviewJson);
        }
        gameJson.put(STRING_REVIEWS, reviewsArray);

        client.putData(STRING_GAMES_BACKSLASH + gameid, gameJson.toString());

    }

    @Override
    public Game getCachedGame(Integer gameid) {
        String json = client.getData(STRING_GAMES_BACKSLASH + gameid);
        if (json == null || "null".equals(json)) {
            return null;
        }

        JSONObject gameJson = new JSONObject(json);
        Game game = new Game();
        // your Game() now initializes safe defaults
        game.setGameid(gameid);

        // Scalars & lists
        game.setName(gameJson.optString("name", ""));
        game.setGenres(jsonArrayToStringList(gameJson.optJSONArray("genres")));
        game.setDeveloper(jsonArrayToStringList(gameJson.optJSONArray("developer")));
        game.setCriticRating(gameJson.optDouble("critic_rating", 0.0));
        game.setRatingCount(gameJson.optDouble("rating_count", 0.0));
        game.setPlatforms(jsonArrayToStringList(gameJson.optJSONArray("platforms")));
        String cover = gameJson.optString("cover_image", "");
        cover = normalizeURL(cover);
        game.setCoverImage(cover);
        game.setCoverImage(gameJson.optString("cover_image", ""));
        game.setAgeRating(gameJson.optString("age_rating", ""));
        game.setReleaseDate(gameJson.optString("release_date", ""));
        game.setDownloadableContent(jsonArrayToIntList(gameJson.optJSONArray("DLCs")));

        List<Review> reviews = new ArrayList<Review>();
        if (gameJson.has(STRING_REVIEWS) && !gameJson.isNull(STRING_REVIEWS)) {
            Object reviewsNode = gameJson.get(STRING_REVIEWS);

            if (reviewsNode instanceof JSONArray) {
                JSONArray arr = (JSONArray) reviewsNode;
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject r = arr.getJSONObject(i);
                    String userid = r.optString("userid", "unknown");
                    String content = r.optString("content", "");
                    int gid = r.optInt("gameid", gameid);
                    double rating = r.optDouble("rating", 0.0);
                    reviews.add(new Review(userid, content, gid, rating));
                }
            }
            else if (reviewsNode instanceof JSONObject) {
                JSONObject obj = (JSONObject) reviewsNode;
                for (String key : obj.keySet()) {
                    JSONObject r = obj.getJSONObject(key);
                    String userid = r.optString("userid", "unknown");
                    String content = r.optString("content", "");
                    int gid = r.optInt("gameid", gameid);
                    double rating = r.optDouble("rating", 0.0);
                    reviews.add(new Review(userid, content, gid, rating));
                }
            }
        }
        game.setReviews(reviews);

        return game;

    }

    private String normalizeURL(String cover) {
        if (cover == null) {
            return null;

        }

        cover = cover.trim();
        if (cover.startsWith("//")) {
            cover = "https:" + cover;
        }
        return cover;

    }

    @Override
    public boolean hasGame(Integer gameid) {


        // Check if the game exists in Firebase
        return client.hasPath(STRING_GAMES_BACKSLASH + gameid);
    }


    private List<String> jsonArrayToStringList(JSONArray array) {
        List<String> list = new ArrayList<>();
        if (array != null) {
            for (Object obj : array) {
                list.add((String) obj);
            }
        }
        return list;
    }

    private List<Integer> jsonArrayToIntList(JSONArray array) {
        List<Integer> list = new ArrayList<>();
        if (array != null) {
            for (Object obj : array) {
                list.add(((Number) obj).intValue());
            }
        }
        return list;
    }
}
