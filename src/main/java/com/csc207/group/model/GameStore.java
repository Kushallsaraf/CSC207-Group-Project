package com.csc207.group.model;

import com.csc207.group.data_access.RawgApiClient;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GameStore {
    @JsonProperty("id")
    private int id;

    @JsonProperty("game_id")
    private String gameID;

    @JsonProperty("store_id")
    private int storeID;

    @JsonProperty("url")
    private String storeURL;

    // consructor
    public GameStore(int id, String gameID, int storeID, String storeURL) {
        this.id = id;
        this.gameID = gameID;
        this.storeID = storeID;
        this.storeURL = storeURL;

    }

    public GameStore() {}

    public int getId() { return id; }

    public String getGameID() { return gameID; }

    public int getStoreID() { return storeID; }

    public String getStoreURL() { return storeURL; }

    public String getStoreName() {

        return RawgApiClient.STORES_IDS_MAPPED_TO_NAMES.get(storeID);

    }

    
}
