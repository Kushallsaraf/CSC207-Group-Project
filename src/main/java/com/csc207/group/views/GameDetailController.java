package com.csc207.group.views;

import com.csc207.group.app.GameCentralController;
import com.csc207.group.model.Game;
import com.csc207.group.service.GameService;
import javafx.scene.image.Image;

public class GameDetailController {
    private GameCentralController gameCentralController;
    private Game game;
    private GameDetailViewFunc view;
    private GameService gameService;

    public GameDetailController(Game game) {
        this.view = new GameDetailViewFunc();
        this.game = game;
    }

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
        view.title.setText(game.getName());
    }
    public void setGenre(){
        for (String genre : game.getGenres()) {
            view.tags.add(view.createTag(genre));
        }
    }
    public void setUserScore(){
        view.scoreLabel.setText(String.valueOf(game.getCritic_rating())+"/5");
    }
    public void setImage(){view.imageView.setImage(new Image(game.getCover_image()));
    }
    public void setSynopsis(){
        view.synopsisStr = game.getDescription();
    }
    public void setOverview(){
        view.developer = String.join(",", game.getDeveloper());
        view.releaseDate = game.getRelease_date();
        view.ageRating = game.getAge_rating();
        view.platforms = String.join(",", game.getPlatforms());
    }



}
