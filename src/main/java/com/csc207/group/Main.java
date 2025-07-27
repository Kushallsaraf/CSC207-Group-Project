package com.csc207.group;

import com.csc207.group.model.Game;
import com.csc207.group.service.GameService;
import com.csc207.group.data_access.RAWGApiClient;

public class Main {
    public static void main(String[] args) throws Exception {
        String gameName = "witcher 3";
        GameService service = new GameService();
        int id = service.getGameIdByName(gameName);

        Game game = service.getGameById(id);
        System.out.println(game);
        System.out.println("/n/n");

        String response = RAWGApiClient.getGamesByGenre("action");
        System.out.println(response);
    }
}