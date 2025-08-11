package com.csc207.group.views;

import com.csc207.group.app.GameCentralController;
import com.csc207.group.model.Game;
import com.csc207.group.service.GameService;
import com.csc207.group.service.GenreService;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class GameDetailController {
    private GameCentralController gameCentralController;
    private Game game;
    private GameDetailViewFunc view;
    private GameService gameService;

    public GameDetailController(Game game) {
        this.view = new GameDetailViewFunc();
        this.game = game;
    }

    // This method initializes the view with data from the game and returns the view instance
    public GameDetailViewFunc setView() {
        setTitle();
        setGenre();
        setUserScore();
        setImage();
        setSynopsis();
        setOverview();
        return view;
    }

    public void setTitle() {
        view.setTitle(game.getName());
    }

    public void setGenre() {
//        view.setTags(game.getGenres());
        List<String> genres = game.getGenres();
        List<String> genreNames = new ArrayList<>();
        for (String genre : genres) {
            genreNames.add(new GenreService().getGenresById(genre));
        }
        view.setTags(genreNames);
    }

    public void setUserScore() {
        view.setUserScore(game.getCritic_rating());
    }

    public void setImage() {
        String coverUrl = game.getCover_image();
        if (coverUrl != null && !coverUrl.isEmpty()) {
            view.setImages(new Image(coverUrl), null);
        }
    }

    public void setSynopsis() {
        view.setSynopsis(game.getDescription());
    }

    public void setOverview() {
        String devs = String.join(", ", game.getDeveloper());
        view.setDeveloper(devs);

        view.setReleaseDate(game.getRelease_date());
        view.setAgeRating(game.getAge_rating());

        String plats = String.join(", ", game.getPlatforms());
        view.setPlatforms(plats);
    }
}