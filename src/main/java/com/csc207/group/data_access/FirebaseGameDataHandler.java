package com.csc207.group.data_access;

import com.csc207.group.cache.FirebaseRestClient;
import com.csc207.group.model.Game;
import com.csc207.group.model.Review;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**Game objects are saved in firebase database. This class is responsible
 * for the storing and retrieving of these objects.
 */
public class FirebaseGameDataHandler implements GameDataHandler{
    private final FirebaseRestClient client;

    public FirebaseGameDataHandler(FirebaseRestClient client) {
        this.client = client;
    }


    @Override
    public void saveGameData(int gameID, Game game) {
        int gameid = game.getGameid();
        if (gameid == 0){
            gameid = gameID;
        }

        JSONObject gameJson = new JSONObject();

        gameJson.put("name", game.getName());
        gameJson.put("genres", game.getGenres());
        gameJson.put("developer", game.getDeveloper());
        gameJson.put("critic_rating", game.getCritic_rating());
        gameJson.put("rating_count", game.getRating_count());
        gameJson.put("platforms", game.getPlatforms());
        gameJson.put("cover_image", game.getCover_image());
        gameJson.put("age_rating", game.getAge_rating());
        gameJson.put("release_date", game.getRelease_date());
        gameJson.put("DLCs", game.getDLCs());
        gameJson.put("description", game.getDescription());

        JSONArray reviewsArray = new JSONArray();
        for (Review review : game.getReviews()) {
            JSONObject reviewJson = new JSONObject();
            reviewJson.put("userid", review.getUserid());
            reviewJson.put("content", review.getContent());
            reviewJson.put("gameid", review.getGameid());
            reviewJson.put("rating", review.getRating());
            reviewsArray.put(reviewJson);
        }
        gameJson.put("reviews", reviewsArray);

        client.putData("Games/" + gameid, gameJson.toString());


    }

    @Override
    public Game getCachedGame(Integer gameid) {
        String json = client.getData("Games/" + gameid);
        if (json == null || "null".equals(json)) {
            return null;
        }

        JSONObject gameJson = new JSONObject(json);
        Game game = new Game(); // your Game() now initializes safe defaults
        game.setGameid(gameid);

        // Scalars & lists
        game.setName(gameJson.optString("name", ""));
        game.setGenres(jsonArrayToStringList(gameJson.optJSONArray("genres")));
        game.setDeveloper(jsonArrayToStringList(gameJson.optJSONArray("developer")));
        game.setCritic_rating(gameJson.optDouble("critic_rating", 0.0));
        game.setRating_count(gameJson.optDouble("rating_count", 0.0));
        game.setPlatforms(jsonArrayToStringList(gameJson.optJSONArray("platforms")));
        String cover = gameJson.optString("cover_image", "");
        cover = normalizeURL(cover);
        game.setCover_image(cover);
        game.setCover_image(gameJson.optString("cover_image", ""));
        game.setAge_rating(gameJson.optString("age_rating", ""));
        game.setRelease_date(gameJson.optString("release_date", ""));
        game.setDLCs(jsonArrayToIntList(gameJson.optJSONArray("DLCs")));


        // Reviews: accept either an array [...] or an object {pushId:{...}, ...}
        List<Review> reviews = new ArrayList<Review>();
        if (gameJson.has("reviews") && !gameJson.isNull("reviews")) {
            Object reviewsNode = gameJson.get("reviews");

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
            } else if (reviewsNode instanceof JSONObject) {
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
        if (cover == null){
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
        return client.hasPath("Games/" + gameid);
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
