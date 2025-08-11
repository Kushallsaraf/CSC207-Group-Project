package com.csc207.group.ui.controller;

import com.csc207.group.app.GameCentralController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**Upon clicking a game preview or library entry object, open the game page view
 *
 */
public class GamePreviewClickHandler implements EventHandler<MouseEvent> {

    private final GameCentralController gameCentralController;

    public GamePreviewClickHandler(GameCentralController gameCentralController){
        this.gameCentralController = gameCentralController;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        Object src = mouseEvent.getSource();
        if (src instanceof Node) {
            Node node = (Node) src;
            Object data = node.getUserData();
            if (data instanceof Integer) {
                int gameId = (Integer) data;
                gameCentralController.showGamePage(gameId);


            }

    }
}}
