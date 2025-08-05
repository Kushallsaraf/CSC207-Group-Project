package com.csc207.group.service;

import Cache.IGDBFirebaseAPICache;
import com.csc207.group.model.Game;
import com.csc207.group.data_access.IGDBApiClient;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GameService {

    private final IGDBApiClient apiClient = new IGDBApiClient(new IGDBFirebaseAPICache());

    public int getGameIdByName(String name) {
        JsonNode json = apiClient.searchGamesByName(name);
        JSONArray array = json.getArray();

        if (array.isEmpty()) return -1;

        JSONObject firstMatch = array.getJSONObject(0);
        return firstMatch.getInt("id");
    }

    public Game getGameById(int id) {
        JsonNode json = apiClient.getGameDetailsById(id);
        JSONArray array = json.getArray();
        // Instantiating game object
        Game gameobj = new Game();

        if (array.isEmpty()) return null;

        //Setting Name
        JSONObject gameJson = array.getJSONObject(0);
        System.out.println(gameJson);
        String name = gameJson.optString("name", "Unknown");
        gameobj.setName(name);

        // Setting Genre ids
        List<String> genres = new ArrayList<>();
        if (gameJson.has("genres")) {
            JSONArray genreArray = gameJson.getJSONArray("genres");
            for (int i = 0; i < genreArray.length(); i++) {
                genres.add(String.valueOf(genreArray.getInt(i)));
            }
        }
        gameobj.setGenres(genres);


        // Setting Developers
        List<String> developers = new ArrayList<>();
        if (gameJson.has("involved_companies")) {
            JSONArray devArray = gameJson.getJSONArray("involved_companies");
            for (int i = 0; i < devArray.length(); i++) {
                developers.add(getDeveloperById(devArray.getInt(i)));
            }
        }
        else {
            developers.add("Unknown");
        }
        gameobj.setDeveloper(developers);

        // Setting Critic Ratings
        double aggregate_rating = gameJson.optDouble("total_rating", 0);
        gameobj.setCritic_rating(aggregate_rating);

        // Setting Rating Count
        double rating_count = gameJson.optDouble("total_rating_count", 0);
        gameobj.setRating_count(rating_count);

        // Setting Platforms the game is available on
        List<String> platforms = new ArrayList<>();
        if (gameJson.has("platforms")) {
            JSONArray platformArray = gameJson.getJSONArray("platforms");
            for (int i = 0; i < platformArray.length(); i++) {
                platforms.add(getPlatformById(platformArray.getInt(i)));
            }
        }
        else {
            platforms.add("Unknown");
        }
        gameobj.setPlatforms(platforms);


        // Setting Cover Image (for wishlist)
        String cover_page = "";
        if (gameJson.has("cover")) {
            cover_page = getCoverPageById(gameJson.optInt("cover", 0));
        }
        else {
            cover_page = "No cover page";
        }
        gameobj.setCover_image(cover_page);


        // Setting age ratings
        String rating = "";
        if (gameJson.has("age_ratings")) {
            JSONArray ageRatingArray = gameJson.getJSONArray("age_ratings");
            rating = getAgeRatingById(ageRatingArray.getInt(0));
        }
        else {
            rating = "age rating not found";
        }
        gameobj.setAge_rating(rating);


        // Setting release date
        String release_date = "";
        if (gameJson.has("release_dates")) {
            JSONArray releaseDateArray = gameJson.getJSONArray("release_dates");
            release_date = getRelease_date(releaseDateArray.getInt(0));
        }
        else {
            release_date = "Release date not found";
        }
        gameobj.setRelease_date(release_date);


        // Setting list of DLCs of a game
        List<Integer> DLCs = new ArrayList<>();
        if (gameJson.has("dlcs")) {
            JSONArray dlcArray = gameJson.getJSONArray("dlcs");
            for (int i = 0; i < dlcArray.length(); i++) {
                DLCs.add(dlcArray.getInt(i));
            }
        }
        gameobj.setDLCs(DLCs);

        return gameobj;
    }

    public String getDeveloperById(int id) {
        JsonNode response = apiClient.getDevelopersById(id);
        return response.getArray().getJSONObject(0).getString("name");
    }

    public String getPlatformById(int id) {
        JsonNode response = apiClient.getPlatformsById(id);
        return response.getArray().getJSONObject(0).getString("name");
    }

    public String getCoverPageById(int id) {
        JsonNode response = apiClient.getCoverArtById(id);
        return response.getArray().getJSONObject(0).getString("url");
    }

    public String getAgeRatingById(int id) {
        JsonNode response = apiClient.getAgeRatingById(id);
        return response.getArray().getJSONObject(0).getString("rating");
    }

    public String getRelease_date(int id) {
        JsonNode response = apiClient.getReleaseDateById(id);
        return response.getArray().getJSONObject(0).getString("human");
    }

}