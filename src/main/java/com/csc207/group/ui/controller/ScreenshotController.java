package com.csc207.group.ui.controller;

import com.csc207.group.usecase.screenshots.ViewScreenshotsInputBoundary;
import com.csc207.group.usecase.screenshots.ViewScreenshotsRequestModel;

public class ScreenshotController {

    private final ViewScreenshotsInputBoundary interactor;

    public ScreenshotController(ViewScreenshotsInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void onViewScreenshots(String gameName) {
        ViewScreenshotsRequestModel request = new ViewScreenshotsRequestModel(gameName);
        interactor.execute(request);
    }

}
