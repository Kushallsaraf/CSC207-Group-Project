package com.csc207.group.data_access;

import com.csc207.group.model.Game;

public interface GameDataHandler {



    /**Saves the gameData to the database
     *
     */
    public void saveGameData(int gameid, Game game);

    /** Instead of calling the API for a game, the GameService will check the database first.
     *
     * @return
     */
    public Game getCachedGame(Integer gameid);

    /**Checks if a game is in the database
     *
     * @param gameid
     * @return
     */
    public boolean hasGame(Integer gameid);


}
