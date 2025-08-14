package com.csc207.group.service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.csc207.group.cache.FirebaseRestClient;
import com.csc207.group.cache.IgdbFirebaseApiCache;
import com.csc207.group.data_access.FirebaseGameDataHandler;
import com.csc207.group.data_access.IgdbApiClient;
import com.csc207.group.model.Game;
import com.csc207.group.model.GamePreview;
import com.csc207.group.model.LibraryEntry;
import com.csc207.group.model.Review;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

public final class GameService {
    private final FirebaseGameDataHandler firebaseGameDataHandler =
            new FirebaseGameDataHandler(new FirebaseRestClient());

    private final IgdbApiClient apiClient = new IgdbApiClient(new IgdbFirebaseApiCache());
    private final Map<Integer, GamePreview> previewCache = new HashMap<>();

    public int getGameIdByName(String name) {
        JsonNode json = apiClient.searchGamesByName(name);
        JSONArray array = json.getArray();
        if (array.isEmpty()) return -1;

        JSONObject firstMatch = array.getJSONObject(0);
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


    /**
     * Will save a game to the database.
     *
     */

    public Game getGameById(int id) {
        if (this.firebaseGameDataHandler.hasGame(id)){
            return firebaseGameDataHandler.getCachedGame(id);
        }

        JsonNode json = apiClient.getGameDetailsById(id);
        JSONArray array = json.getArray();
        if (array.isEmpty()) return null;

        JSONObject gameJson = array.getJSONObject(0);
        Game gameobj = new Game(id);

        gameobj.setName(gameJson.optString("name", "Unknown"));

        // Genres
        List<String> genres = new ArrayList<>();
        if (gameJson.has("genres")) {
            JSONArray genreArray = gameJson.getJSONArray("genres");
            for (int i = 0; i < genreArray.length(); i++) {
                genres.add(String.valueOf(genreArray.getInt(i)));
            }
        }
        gameobj.setGenres(genres);

        // Developers
        List<String> developers = new ArrayList<>();
        if (gameJson.has("involved_companies")) {
            JSONArray devArray = gameJson.getJSONArray("involved_companies");
            for (int i = 0; i < devArray.length(); i++) {
                developers.add(getDeveloperById(devArray.getInt(i)));
            }
        } else {
            developers.add("Unknown");
        }
        gameobj.setDeveloper(developers);

        // Ratings
        gameobj.setCriticRating(gameJson.optDouble("total_rating", 0));
        gameobj.setRatingCount(gameJson.optDouble("total_rating_count", 0));

        // Platforms
        List<String> platforms = new ArrayList<>();
        if (gameJson.has("platforms")) {
            JSONArray platformArray = gameJson.getJSONArray("platforms");
            for (int i = 0; i < platformArray.length(); i++) {
                platforms.add(getPlatformById(platformArray.getInt(i)));
            }
        } else {
            platforms.add("Unknown");
        }
        gameobj.setPlatforms(platforms);

        // Cover Image
        String cover_page = gameJson.has("cover") ? getCoverPageById(gameJson.optInt("cover", 0)) : "No cover page";
        gameobj.setCoverImage(cover_page);

        // Age Rating
        String rating = gameJson.has("age_ratings")
                ? getAgeRatingById(gameJson.getJSONArray("age_ratings").getInt(0))
                : "age rating not found";
        gameobj.setAgeRating(rating);

        // Release Date
        String release_date = gameJson.has("release_dates")
                ? getReleaseDate(gameJson.getJSONArray("release_dates").getInt(0))
                : "Release date not found";
        gameobj.setReleaseDate(release_date);

        // DLCs
        List<Integer> DLCs = new ArrayList<>();
        if (gameJson.has("dlcs")) {
            JSONArray dlcArray = gameJson.getJSONArray("dlcs");
            for (int i = 0; i < dlcArray.length(); i++) {
                DLCs.add(dlcArray.getInt(i));
            }
        }
        gameobj.setDownloadableContent(DLCs);

        // Description
        String summary = gameJson.optString("summary", "No description available.");
        gameobj.setDescription(summary);

        return gameobj;
    }

    public GamePreview getGamePreviewById(int gameId) {
        if (previewCache.containsKey(gameId)) {
            return previewCache.get(gameId);
        }

        JSONObject gameData = fetchGameData(gameId);
        if (gameData == null) return null;

        String title = gameData.optString("name", "<name not found>");
        int releaseYear = extractReleaseYear(gameData);
        Image coverImage = extractCoverImage(gameData);
        // Fetch the summary to use as the description
        String description = gameData.optString("summary", "No description available.");

        // Pass the description as the 5th argument
        GamePreview preview = new GamePreview(title, releaseYear, coverImage, gameId, description);
        previewCache.put(gameId, preview);
        return preview;
    }

    public LibraryEntry getLibraryEntryById(int gameId) {
        JSONObject gameData = fetchGameData(gameId);
        if (gameData == null) return null;

        String title = gameData.optString("name", "<name not found>");
        int releaseYear = extractReleaseYear(gameData);
        Image coverImage = extractCoverImage(gameData);
        // Fetch the summary to use as the description
        String description = gameData.optString("summary", "No description available.");

        // Pass the description as the 5th argument
        return new LibraryEntry(title, releaseYear, coverImage, gameId, description);
    }



    private JSONObject fetchGameData(int gameId) {
        JsonNode json = apiClient.getGameDetailsById(gameId);
        JSONArray array = json.getArray();
        if (array.isEmpty()) return null;
        return array.getJSONObject(0);
    }

    private int extractReleaseYear(JSONObject game) {
        if (!game.has("first_release_date")) return -1;
        long timestamp = game.getLong("first_release_date");
        ZonedDateTime dateTime = Instant.ofEpochSecond(timestamp).atZone(ZoneId.systemDefault());
        return dateTime.getYear();
    }

    private Image extractCoverImage(JSONObject gameData) {
        if (!gameData.has("cover")) return getPlaceholderImage();

        int coverId = gameData.getInt("cover");
        JsonNode coverJson = apiClient.getCoverArtById(coverId);
        JSONArray coverArray = coverJson.getArray();

        if (coverArray.isEmpty()) return getPlaceholderImage();

        JSONObject coverObj = coverArray.getJSONObject(0);
        if (!coverObj.has("url")) return getPlaceholderImage();

        String url = coverObj.getString("url");
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
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 75, 100);
        WritableImage image = new WritableImage(75, 100);
        canvas.snapshot(null, image);
        return image;
    }

    public void preloadGamePreviews(List<Integer> gameIds) {
        for (int id : gameIds) {
            getGamePreviewById(id); // populate cache
        }
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
        if (response.getArray().isEmpty()) {
            return null; // preferably return a placeholder image path
        }
        String url = response.getArray().getJSONObject(0).getString("url");
        if (url.startsWith("//")) {
            url = "https:" + url;
        } else if (!url.startsWith("http")) {
            url = "https://" + url;
        }
        return url;
    }

    public String getAgeRatingById(int id) {
        JsonNode response = apiClient.getAgeRatingById(id);
        return response.getArray().getJSONObject(0).getString("rating");
    }

    public String getReleaseDate(int id) {
        JsonNode response = apiClient.getReleaseDateById(id);
        return response.getArray().getJSONObject(0).getString("human");
    }

    public static void main(String[] args) {
        GameService g = new GameService();
        Game game = g.getGameById(75);
        if (game.getReviews() == null || game.getReviews().isEmpty()) {
            System.out.println("No reviews found for game: " + game.getName());
        } else {
            System.out.println("Reviews for game: " + game.getName());
            for (Review r : game.getReviews()) {
                if (r != null) {
                    System.out.println("User: " + r.getUserId());
                    System.out.println("Rating: " + r.getRating());
                    System.out.println("Content: " + r.getContent());
                    System.out.println("---------------------------");
                }
            }
        }

    }
}
