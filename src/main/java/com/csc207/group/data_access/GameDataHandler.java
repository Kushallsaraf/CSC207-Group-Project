package com.csc207.group.data_access;

import com.csc207.group.model.Game;

public interface GameDataHandler {

    /**
     * Saves the gameData to the database.
     * @param gameid The id of a given game.
     * @param game a Game object.
     */
    void saveGameData(int gameid, Game game);

    /**
     * Instead of calling the API for a game, the GameService will check the database first.
     * @param gameid The id of a given game
     * @return the cached Game object.
     */
    Game getCachedGame(Integer gameid);

    /**
     * Checks if a game is in the database.
     * @param gameid The id of a given game.
     * @return whether the database has the game.
     */
    boolean hasGame(Integer gameid);


}
