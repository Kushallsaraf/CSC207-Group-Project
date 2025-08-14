package com.csc207.group.presenter;

import com.csc207.group.model.Screenshot;
import com.csc207.group.usecase.screenshots.ViewScreenshotsOutputBoundary;
import com.csc207.group.usecase.screenshots.ViewScreenshotsResponseModel;
import com.csc207.group.views.GameDetailViewFunc;
import javafx.application.Platform;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class ScreenshotPresenter implements ViewScreenshotsOutputBoundary {

    private final GameDetailViewFunc view;

    public ScreenshotPresenter(GameDetailViewFunc view) {
        this.view = view;
    }

    @Override
    public void present(ViewScreenshotsResponseModel model) {
        List<Image> images = new ArrayList<>();

        for (Screenshot screenshot : model.getScreenshots()) {

            if (screenshot != null && screenshot.isVisible()) {
                String url = screenshot.getImageUrl();
                if (url != null) {
                    Image image = new Image(url, true);
                    images.add(image);
                }
            }
        }

        Platform.runLater(() -> view.displayScreenshots(images));

    }

    @Override
    public void presentFailure(String message) {
        List<Image> empty = new ArrayList<>();
        Platform.runLater(() -> view.displayScreenshots(empty));
    }

}


