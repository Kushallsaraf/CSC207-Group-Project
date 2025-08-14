package com.csc207.group.ui.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ReviewSubmissionHandler implements EventHandler<ActionEvent> {
    private GamePageController gamePageController;

    public ReviewSubmissionHandler(GamePageController gamePageController) {
        this.gamePageController = gamePageController;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        gamePageController.handleReviewSubmission();

    }
}
