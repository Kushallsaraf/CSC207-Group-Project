package com.csc207.group.usecase.screenshots;

import java.util.List;

import com.csc207.group.model.Screenshot;

public class ViewScreenshotsResponseModel {

    private final List<Screenshot> screenshots;

    public ViewScreenshotsResponseModel(List<Screenshot> screenshots) {
        this.screenshots = screenshots;
    }

    public List<Screenshot> getScreenshots() {
        return screenshots;
    }

}
