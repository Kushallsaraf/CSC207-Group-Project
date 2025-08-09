package com.csc207.group.service;

import com.csc207.group.model.GameStore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class GameStoreService {

    // taking the stores that the api returns for  agiven game
    // and returning them formatted properly
    // will allow for the method in RAWG API Client to return a list of GameStore Objects

    public static List<GameStore> parseGameStoresFromJson(String json) throws JsonProcessingException {

        List<GameStore> gameStoresList = new ArrayList<GameStore>();

        ObjectMapper mapper = new ObjectMapper();
        // similar process to the achievement service class
        JsonNode rootNode = mapper.readTree(json);

        // trying to fix caching issue with how json is cached and
        // how the service class deals with it => worked
        if (rootNode.isArray() && !rootNode.isEmpty()) {
            rootNode = rootNode.get(0);
        }
        JsonNode gameStoresNode = rootNode.get("results");

        if(gameStoresNode != null && gameStoresNode.isArray()) {
            for (JsonNode gameStoreNode : gameStoresNode) {

                int id = gameStoreNode.get("id").asInt();
                String gameID = gameStoreNode.get("game_id").asText();
                int storeID = gameStoreNode.get("store_id").asInt();
                String storeURL = gameStoreNode.get("url").asText();

                // creating the game service object and adding to list
                gameStoresList.add(new GameStore(
                        id,
                        gameID,
                        storeID,
                        storeURL));

            }
        }

        return gameStoresList;

    }

}
