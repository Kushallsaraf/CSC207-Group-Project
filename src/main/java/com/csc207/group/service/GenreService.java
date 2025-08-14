package com.csc207.group.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.csc207.group.cache.IgdbFirebaseApiCache;
import com.csc207.group.cache.RawgFirebaseApiCache;
import com.csc207.group.data_access.IgdbApiClient;
import com.csc207.group.data_access.RawgApiClient;
import com.csc207.group.model.Game;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

public final class GenreService {
    public static final String NAME = "name";
    private final RawgApiClient client = new RawgApiClient(new RawgFirebaseApiCache());

    /**
     * Gets games by genre.
     * @param genre the genre.
     * @return List of games.
     * @throws IOException if error with API response.
     * @throws InterruptedException if HTTP request is interrupted.
     */
    public List<Game> getGamesByGenre(String genre) throws IOException, InterruptedException {
        Map<String, Object> response = client.getGamesByGenre(genre);
        List<Game> games = new ArrayList<>();

        List<Map<String, Object>> result = (List<Map<String, Object>>) response.get("results");

        for (Map<String, Object> game : result) {
            List<String> genres = new ArrayList<>();
            if (game.get("genres") != null) {
                List<Map<String, Object>> genreList = (List<Map<String, Object>>) game.get("genres");
                for (Map<String, Object> g : genreList) {
                    genres.add((String) g.get(NAME));
                }
                Game g = new Game();
                g.setGenres(genres);
                g.setName((String) game.get(NAME));
                if (game.containsKey("id")) {
                    g.setGameid((Integer) game.get("id"));
                }
                games.add(g);
            }
        }
        return games;
    }

    /**
     * Gets genres by id.
     * @param id id of genre.
     * @return String name of genre.
     */
    public String getGenresById(String id) {
        int intId = Integer.parseInt(id);
        final IgdbApiClient apiClient = new IgdbApiClient(new IgdbFirebaseApiCache());
        JsonNode response = apiClient.getGenresById(intId);
        JSONArray array = response.getArray();
        JSONObject genreObject = array.getJSONObject(0);
        return genreObject.get(NAME).toString();
    }
}
