package com.csc207.group.model;

public class GameStore {

    private int id;
    private String gameID;
    private int storeID;
    private String storeURL;

    // consructor
    public GameStore(int id, String gameID, int storeID, String storeURL) {
        this.id = id;
        this.gameID = gameID;
        this.storeID = storeID;
        this.storeURL = storeURL;

    }
    public int getId() { return id; }

    public String getGameID() { return gameID; }

    public int getStoreID() { return storeID; }

    public String getStoreURL() { return storeURL; }


}
