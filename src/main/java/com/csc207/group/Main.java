package com.csc207.group;

import com.csc207.group.model.Game;
import com.csc207.group.service.GameService;

public class Main {
    public static void main(String[] args) {
        String gameName = "witcher 3";
        GameService service = new GameService();
        int id = service.getGameIdByName(gameName);

        Game game = service.getGameById(id);
        System.out.println(game);
    }
}