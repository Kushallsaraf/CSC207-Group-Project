package com.csc207.group.usecase.screenshots;

public class ViewScreenshotsRequestModel {
    private final String gameName;

    public ViewScreenshotsRequestModel(String gameName) {
        this.gameName = gameName;
    }

    public String getGameName() {
        return gameName;
    }

}
