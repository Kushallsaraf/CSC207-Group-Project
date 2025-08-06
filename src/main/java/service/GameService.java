package service;

import cache.IGDBFirebaseAPICache;
import data_access.IGDBApiClient;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import model.GamePreview;

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
    public List<Integer> searchGame(String name) {
        List<Integer> gameIds = new ArrayList<>();

        JsonNode json = apiClient.searchGamesByName(name);
        JSONArray array = json.getArray();

        for (int i = 0; i < array.length(); i++) {
            JSONObject gameObject = array.getJSONObject(i);
            if (gameObject.has("id")) {
                gameIds.add(gameObject.getInt("id"));
            }
        }

        return gameIds;
    }


    public String getGameName(int id) {
        JsonNode json = apiClient.getGameDetailsById(id);
        if (json == null || json.getArray().isEmpty()) {
            return "<name not found>";
        }

        JSONObject gameObject = json.getArray().getJSONObject(0);

        if (!gameObject.has("name")) {
            return "<name not found>";
        }

        return gameObject.getString("name");
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

    public Image getCoverImageByGameId(int gameId) {
        JsonNode gameJson = apiClient.getGameDetailsById(gameId);
        JSONArray gameArray = gameJson.getArray();

        if (gameArray.isEmpty()) {
            return getPlaceholderImage();
        }

        JSONObject gameObj = gameArray.getJSONObject(0);

        if (!gameObj.has("cover")) {
            return getPlaceholderImage();
        }

        int coverId = gameObj.getInt("cover");

        JsonNode coverJson = apiClient.getCoverArtById(coverId);
        JSONArray coverArray = coverJson.getArray();

        if (coverArray.isEmpty()) {
            return getPlaceholderImage();
        }

        JSONObject coverObj = coverArray.getJSONObject(0);
        if (!coverObj.has("url")) {
            return getPlaceholderImage();
        }

        String url = coverObj.getString("url");

        // Normalize to full URL
        if (url.startsWith("//")) {
            url = "https:" + url;
        } else if (!url.startsWith("http")) {
            url = "https://" + url;
        }

        return new Image(url, true);


    }

    private Image getPlaceholderImage() {
        Canvas canvas = new Canvas(75, 100);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK); // Or any fallback color
        gc.fillRect(0, 0, 75, 100);

        WritableImage image = new WritableImage(75, 100);
        canvas.snapshot(null, image);
        return image;
    }
    public GamePreview getGamePreviewById(int gameId) {
        String title = getGameName(gameId);
        int releaseYear = getGameReleaseYear(gameId);
        Image coverImage = getCoverImageByGameId(gameId);

        return new GamePreview(title, releaseYear, coverImage, gameId);
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
