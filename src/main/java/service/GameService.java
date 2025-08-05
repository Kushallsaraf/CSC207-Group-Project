package service;

import cache.IGDBFirebaseAPICache;
import data_access.IGDBApiClient;
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
        System.out.println(array);

        if (array.isEmpty()) return -1;

        JSONObject firstMatch = array.getJSONObject(1);
        return firstMatch.getInt("id");
    }

    public String getGameName(int id) {
        JsonNode json = apiClient.getGameDetailsById(id);
        JSONArray array = json.getArray();

        if (array.isEmpty()) return null;

        JSONObject game = array.getJSONObject(0);
        return game.getString("name");
    }


    public int getGameReleaseYear(int id) {
        JsonNode json = apiClient.getGameDetailsById(id);
        JSONArray array = json.getArray();

        if (array.isEmpty()) return -1;

        JSONObject game = array.getJSONObject(0);
        if (!game.has("first_release_date")) return -1;

        long unixTimestamp = game.getLong("first_release_date");
        java.time.Instant instant = java.time.Instant.ofEpochSecond(unixTimestamp);
        java.time.ZonedDateTime dateTime = instant.atZone(java.time.ZoneId.systemDefault());

        return dateTime.getYear();

    }

    public List<Integer> getSimilarGameIds(int gameId) {
        JsonNode json = apiClient.getGameDetailsById(gameId);
        JSONArray array = json.getArray();

        List<Integer> similarGameIds = new ArrayList<>();

        if (array.isEmpty()) return similarGameIds;

        JSONObject game = array.getJSONObject(0);

        if (!game.has("similar_games")) return similarGameIds;

        JSONArray similarArray = game.getJSONArray("similar_games");
        for (int i = 0; i < similarArray.length(); i++) {
            similarGameIds.add(similarArray.getInt(i));
        }

        return similarGameIds;
    }



    public static void main(String[] args) {
        GameService g = new GameService();
        System.out.println(g.getGameName(73));
        System.out.println(g.getGameReleaseYear(73));
        GameService gameService = new GameService();

        int gameId = 73; // Mass Effect ID
        List<Integer> similarGameNames = gameService.getSimilarGameIds(gameId);

        System.out.println("Similar games to " + gameService.getGameName(gameId) + ":");
        for (Integer name : similarGameNames) {
            System.out.println("- " + g.getGameName(name));
        }
    }
    }
