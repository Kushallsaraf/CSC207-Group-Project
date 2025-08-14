package com.csc207.group.usecase.screenshots;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.csc207.group.model.Screenshot;
import com.csc207.group.usecase.screenshots.gateway.ScreenshotGateway;

public class ViewScreenshotsInteractor implements ViewScreenshotsInputBoundary {

    private final ScreenshotGateway gateway;
    private final ViewScreenshotsOutputBoundary presenter;

    public ViewScreenshotsInteractor(ScreenshotGateway gateway, ViewScreenshotsOutputBoundary presenter) {
        this.gateway = gateway;
        this.presenter = presenter;
    }

    @Override
    public void execute(ViewScreenshotsRequestModel requestModel) {
        try {
            List<Screenshot> screenshots = null;
            screenshots = gateway.getScreenshotsForGame(requestModel.getGameName());

            if (screenshots == null) {
                screenshots = new ArrayList<>();
            }

            ViewScreenshotsResponseModel response = new ViewScreenshotsResponseModel(screenshots);
            presenter.present(response);

        }
        catch (IOException ex) {
            presenter.presentFailure("Could not fetch screenshots.");
        }
        catch (InterruptedException ex) {
            presenter.presentFailure("Could not fetch screenshots.");
        }

    }

}
