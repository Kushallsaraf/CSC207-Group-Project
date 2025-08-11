package com.csc207.group.ui.controller;

import com.csc207.group.model.Game;
import com.csc207.group.service.GameService;

public class GamePageController {

    private final GameService gameService;

    public GamePageController(GameService gameService) {
        this.gameService = gameService;
    }

    public void setGamePageView(int id){
        Game game = gameService.getGameById(id);

    }
}
