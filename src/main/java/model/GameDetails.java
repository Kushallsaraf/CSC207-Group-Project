package model;

import service.GameService;
import service.GenreService;

public class GameDetails {
    private GameService gameService;
    private GenreService genreService;


    public GameDetails(GameService gameService, GenreService genreService ){
        this.gameService =gameService;
        this.genreService =genreService;
    }
    public String getGameName(int id){
        return this.gameService.getGameName(id);
    }

    public int getReleaseYear(int id){
        return this.gameService.getGameReleaseYear(id);
    }
}
