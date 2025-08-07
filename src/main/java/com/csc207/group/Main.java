package com.csc207.group;

import Cache.RAWGFirebaseAPICache;
import com.csc207.group.data_access.RAWGApiClient;
import com.csc207.group.model.Achievement;
import com.csc207.group.model.GameStore;
import com.csc207.group.model.Screenshot;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public class Main {

    public static final String GTA5_GAME_ID = "3498";

    public static void main(String[] args) {
        try {
            // 3498 -> GTA 5
            String gameID = GTA5_GAME_ID;
            RAWGApiClient client = new RAWGApiClient(new RAWGFirebaseAPICache());
            List<Achievement> achievements = client.getGameAchievements(gameID);

            if (achievements.isEmpty()) {
                System.out.println("No achievements found for game ID: " + gameID);
            } else {
                System.out.println("Achievements for game ID " + gameID + ":");
                for (Achievement ach : achievements) {
                    System.out.println("• " + ach.getAchievementName() + " - " + ach.getAchievementDescription() +
                            " (" + ach.getAchievementID() + ")" + "Image:" + ach.getAchievementImage()
                            + " Completion percentage:" + ach.getAchievementCompletionPercentage()
                            + "Rarity:" + ach.calculateRarity());

                }
            }
        } catch (Exception e) {
            System.out.println("Error occurred while fetching achievements: " + e.getMessage());
            e.printStackTrace();
        }
        // tests the game id by name function
        RAWGApiClient api = new RAWGApiClient(new RAWGFirebaseAPICache());

        try {
            String gameName = "The Witcher 3";  // Change to any game you'd like to test
            Integer gameId = api.findGameIdByName(gameName);

            if (gameId != null) {
                System.out.println("Found game ID for '" + gameName + "': " + gameId);
            } else {
                System.out.println("Game not found: " + gameName);
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // checking if the store ids mapped to names method and its constant worked fine -> does
        for (Map.Entry<Integer, String> entry : RAWGApiClient.STORES_IDS_MAPPED_TO_NAMES.entrySet()) {
            System.out.println("ID: " + entry.getKey() + ", Name: " + entry.getValue());
        }
        try {
            // using 3498 again -> GTA 5
            String testGameID = GTA5_GAME_ID;
            RAWGApiClient client = new RAWGApiClient(new RAWGFirebaseAPICache());
            List<GameStore> storesForGame = client.getStoresForGame(testGameID);

            System.out.println("Stores selling game with ID " + testGameID + ":");
            if (storesForGame.isEmpty()) {
                System.out.println(storesForGame);
                System.out.println("No stores found for this game.");
            } else {
                for (GameStore store : storesForGame) {
                    System.out.println("ID: " + store.getId() + ", Name: " + store.getStoreName() +
                            ", URL: " + store.getStoreURL() + " Game ID: " + store.getGameID() +
                            " Store ID:  " + store.getStoreID());

                }
            }

        } catch (Exception e) {
            System.err.println("Error fetching stores for game: " + e.getMessage());
            e.printStackTrace();
        }
        try {
            // same id as before GTA 5
            String gameID = GTA5_GAME_ID;

            // tests screenshots related code
            RAWGApiClient client = new RAWGApiClient(new RAWGFirebaseAPICache());
            List<Screenshot> screenshots = client.getScreenshotsForGame(gameID);

            System.out.println("=== Screenshots for Game ID " + gameID + " ===");

            for (Screenshot s : screenshots) {
                if (!s.isVisible()) {
                    System.out.println("Image: " + s.getImageURL());
                } else {
                    System.out.println("(Hidden) Skipped image: " + s.getImageURL());
                }
            }

        } catch (Exception e) {
            System.err.println("Error fetching screenshots: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
